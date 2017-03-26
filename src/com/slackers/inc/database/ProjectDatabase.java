/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slackers.inc.database;

import com.slackers.inc.database.entites.LabelApplication;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author John Stegeman <j.stegeman@labyrinth-tech.com>
 */
public class ProjectDatabase{

    
    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        
        LabelApplication app1 = new LabelApplication("Generic Wine 7", "John Doe");
        
        DerbyConnection db = DerbyConnection.getInstance();
        
        if (!db.tableExists(app1.getTableName()))
        {
            db.createTable(app1.getTableName(), app1.tableColumnCreationSettings());
        }
        System.out.println(app1);
        
        db.writeEntity(app1, "BeverageName");
        db.getEntity(app1, "BeverageName");
        System.out.println(app1);
        
        app1.setSubmitter("Rob Smith");
        db.writeEntity(app1, "BeverageName");
        db.getEntity(app1, "BeverageName");
        System.out.println(app1);
        
        db.closeConnection();
        db.shutdownDb();
    }
    
    
    
}
