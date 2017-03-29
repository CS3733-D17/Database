/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slackers.inc.database;

import com.slackers.inc.database.entities.IEntity;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author John Stegeman <j.stegeman@labyrinth-tech.com>
 */
public class DerbyConnection {
    private final static String DRIVER_CLASS = "org.apache.derby.jdbc.EmbeddedDriver";
    private final static String DB_PROTOCOL_BASE = "jdbc:derby:";
    private final static String DB_RELATIVE_LOCATION = "database/DB";
    
    private static DerbyConnection INSTANCE;
    
    static
    {
        INSTANCE = null;
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
            INSTANCE = new DerbyConnection();
        } catch (Exception ex) {
            Logger.getLogger(DerbyConnection.class.getName()).log(Level.SEVERE, "Could not load database. Exiting");
            Logger.getLogger(DerbyConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }       
    }
    
    private Connection con;
    
    private DerbyConnection() throws SQLException
    {
        Map<String,String> properties = new HashMap<>();
        properties.put("create", "true");
        con = DriverManager.getConnection(makeConnectionString(properties));
    }
    
    public boolean tableExists(String tableName) throws SQLException
    {
        DatabaseMetaData md = con.getMetaData();
        ResultSet rs = md.getTables(null, null, "%", null);
        while (rs.next()) {
            if (rs.getString(3).toLowerCase().equals(tableName.toLowerCase()))
                return true;
        }
        return false;
    }
    
    public boolean createTable(String tableName, List<String> columns) throws SQLException
    {        
        String stmt = String.format("CREATE TABLE %s (%s)", tableName, String.join(", ", columns));
        CallableStatement call = con.prepareCall(stmt);
        return call.execute();
    }
    
    
    public boolean createEntity(IEntity entity) throws SQLException
    {
        StringBuilder cols = new StringBuilder();
        StringBuilder vPlace = new StringBuilder();
        List<Object> vals = new LinkedList<>();
        boolean first = true;
        for (Entry<String, Object> e : entity.getEntityValues().entrySet())
        {
            if (first)
            {
                first = false;
            }
            else
            {
                cols.append(",");
                vPlace.append(",");
            }
            cols.append(e.getKey());
            vPlace.append('?');
            vals.add(e.getValue());
        }
        String stmt = String.format("INSERT INTO %s (%s) VALUES (%s)", entity.getTableName(), cols.toString(), vPlace.toString());
        CallableStatement call = con.prepareCall(stmt);
        int i = 1;
        for (Object o : vals)
        {
            DerbyConnection.setStatementValue(call, i, o);
            i++;
        }        
        return call.execute();
    }
    
    public boolean deleteEntity(IEntity entity, String... searchColumns) throws SQLException
    {
        if (searchColumns.length<=0)
            return false; // avoid table deletion
        Set<String> cols = new HashSet<>(Arrays.asList(searchColumns));
        StringBuilder conds = new StringBuilder();
        List<Object> vals = new LinkedList<>();
        boolean first = true;
        for (Entry<String, Object> e : entity.getEntityValues().entrySet())
        {
            if (cols.contains(e.getKey()))
            {
                if (first)
                {
                    first = false;
                }
                else
                {
                    conds.append(" AND ");
                }
                conds.append(e.getKey());
                conds.append("=(?)");
                vals.add(e.getValue());
            }
        }
        String stmt = String.format("DELETE * FROM %s WHERE %s", entity.getTableName(), conds.toString());
        CallableStatement call = con.prepareCall(stmt);
        int i = 1;
        for (Object o : vals)
        {
            DerbyConnection.setStatementValue(call, i, o);
            i++;
        }        
        return call.execute();
    }
    
    public boolean updateEntity(IEntity entity, String... searchColumns) throws SQLException
    {
        if (searchColumns.length<=0)
            return false; // avoid table deletion
        Set<String> cols = new HashSet<>(Arrays.asList(searchColumns));
        StringBuilder conds = new StringBuilder();
        
        StringBuilder vPlace = new StringBuilder();
        List<Object> setvals = new LinkedList<>();
        List<Object> condvals = new LinkedList<>();
        boolean first = true;
        boolean firstSet = true;
        for (Entry<String, Object> e : entity.getEntityValues().entrySet())
        {
            if (cols.contains(e.getKey()))
            {
                if (first)
                {
                    first = false;
                }
                else
                {
                    conds.append(" AND ");
                }
                conds.append(e.getKey());
                conds.append("=(?)");
                condvals.add(e.getValue());
            }
            if (firstSet)
            {
                firstSet=false;
            }
            else
            {
                vPlace.append(',');
            }
            vPlace.append(e.getKey());
            vPlace.append("=(?)");
            setvals.add(e.getValue());
        }
        String stmt = String.format("UPDATE %s SET %s WHERE %s", entity.getTableName(), vPlace.toString(), conds.toString());
        CallableStatement call = con.prepareCall(stmt);
        int i = 1;
        for (Object o : setvals)
        {
            DerbyConnection.setStatementValue(call, i, o);
            i++;
        }
        for (Object o : condvals)
        {
            DerbyConnection.setStatementValue(call, i, o);
            i++;
        }        
        return call.execute();
    }
    
    public boolean entityExists(IEntity entity, String... searchColumns) throws SQLException
    {
        if (searchColumns.length<=0)
            return true; // avoid table deletion
        Set<String> cols = new HashSet<>(Arrays.asList(searchColumns));
        StringBuilder conds = new StringBuilder();
        List<Object> vals = new LinkedList<>();
        boolean first = true;
        for (Entry<String, Object> e : entity.getEntityValues().entrySet())
        {
            if (cols.contains(e.getKey()))
            {
                if (first)
                {
                    first = false;
                }
                else
                {
                    conds.append(" AND ");
                }
                conds.append(e.getKey());
                conds.append("=(?)");
                vals.add(e.getValue());
            }
        }
        String stmt = String.format("SELECT * FROM %s WHERE %s", entity.getTableName(), conds.toString());
        CallableStatement call = con.prepareCall(stmt);
        int i = 1;
        for (Object o : vals)
        {
            DerbyConnection.setStatementValue(call, i, o);
            i++;
        }        
        ResultSet results = call.executeQuery();
        while (results.next())
        {
            return true;
        }
        return false;
    }
    
    public void getEntity(IEntity entity, String... searchColumns) throws SQLException
    {
        if (searchColumns.length<=0)
            return; // avoid table deletion
        Set<String> cols = new HashSet<>(Arrays.asList(searchColumns));
        StringBuilder conds = new StringBuilder();
        List<Object> vals = new LinkedList<>();
        boolean first = true;
        for (Entry<String, Object> e : entity.getEntityValues().entrySet())
        {
            if (cols.contains(e.getKey()))
            {
                if (first)
                {
                    first = false;
                }
                else
                {
                    conds.append(" AND ");
                }
                conds.append(e.getKey());
                conds.append("=(?)");
                vals.add(e.getValue());
            }
        }
        String stmt = String.format("SELECT * FROM %s WHERE %s", entity.getTableName(), conds.toString());
        CallableStatement call = con.prepareCall(stmt);
        int i = 1;
        for (Object o : vals)
        {
            DerbyConnection.setStatementValue(call, i, o);
            i++;
        }
        ResultSet results = call.executeQuery();
        
        int c=0;
        Map<String,Object> valMap = new HashMap<>();
        while (results.next())
        {
            valMap.clear();
            c++;
            if (c>1) // only get first
                return;
            for (String s : entity.getEntityNameTypePairs().keySet())
            {
                DerbyConnection.getStatementValue(results, s, entity, valMap);
            }
            entity.setEntityValues(valMap);
        }
    }

    public boolean writeEntity(IEntity entity, String... searchColumns) throws SQLException
    {
        if (this.entityExists(entity, searchColumns))
            return this.updateEntity(entity, searchColumns);
        else
            return this.createEntity(entity);
    }
    
    private static void setStatementValue(CallableStatement stmt, int index, Object obj) throws SQLException
    {
        if (obj == null)
        {
            stmt.setNull(index, java.sql.Types.NULL);
        }
        else if (obj instanceof Integer)
        {
            stmt.setInt(index, (Integer)obj);
        }
        else if (obj instanceof Short)
        {
            stmt.setShort(index, (Short)obj);
        }
        else if (obj instanceof String)
        {
            stmt.setString(index, (String)obj);
        }
        else if (obj instanceof Date)
        {
            stmt.setDate(index, (Date)obj);
        }
        else if (obj instanceof Long)
        {
            stmt.setLong(index, (Long)obj);
        }
        else if (obj instanceof Serializable)
        {
            try {
                stmt.setString(index, objectToString((Serializable)obj));
            } catch (IOException ex) {
                Logger.getLogger(DerbyConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private static void getStatementValue(ResultSet result, String colTitle, IEntity entity, Map<String,Object> valueCollection) throws SQLException
    {
        Class target = entity.getEntityNameTypePairs().get(colTitle);
        if (target == null)
            return;
        if (target.isAssignableFrom(Integer.class))
        {
            valueCollection.put(colTitle, result.getInt(colTitle));
        }
        else if (target.isAssignableFrom(Short.class))
        {
            valueCollection.put(colTitle, result.getShort(colTitle));
        }
        else if (target.isAssignableFrom(String.class))
        {
            valueCollection.put(colTitle, result.getString(colTitle));
        }
        else if (target.isAssignableFrom(Date.class))
        {
            valueCollection.put(colTitle, result.getDate(colTitle));
        }
        else if (target.isAssignableFrom(Long.class))
        {
            valueCollection.put(colTitle, result.getLong(colTitle));
        }
        else if (target.isAssignableFrom(Serializable.class))
        {
            try {
                valueCollection.put(colTitle, objectFromString(result.getString(colTitle)));
            } catch (IOException ex) {
                Logger.getLogger(DerbyConnection.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DerbyConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private static Object objectFromString( String s ) throws IOException ,
                                                       ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode( s );
        Object o;
        try (ObjectInputStream ois = new ObjectInputStream( 
                new ByteArrayInputStream(  data ) )) {
            o = ois.readObject();
        }
        return o;
   }
    
    private static String objectToString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream( baos )) {
            oos.writeObject( o );
        }
        return Base64.getEncoder().encodeToString(baos.toByteArray()); 
    }
    
    public void closeConnection() throws SQLException
    {
        this.con.close();
    }    
    
    public static DerbyConnection getInstance()
    {
        return DerbyConnection.INSTANCE;
    }
    
    
    private static String makeConnectionString(Map<String,String> properties)
    {
        StringBuilder propString = new StringBuilder();
        boolean first = true;
        for (Entry<String,String> e : properties.entrySet())
        {
            if (first)
            {
                first = false;
            }
            else
            {
                propString.append(";");
            }
            propString.append(e.getKey()).append("=").append(e.getValue());
        }
        
        String base = DB_PROTOCOL_BASE + DB_RELATIVE_LOCATION + ";" + propString.toString();
        return base;
    }
    
    public boolean shutdownDb()
    {
        try {
            Map<String,String> properties = new HashMap<>();
            properties.put("shutdown", "true");
            DriverManager.getConnection(makeConnectionString(properties));
        } catch (SQLException ex) {
            return true;
        }
        return false;
    }
}
