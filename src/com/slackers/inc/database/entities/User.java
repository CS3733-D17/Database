package com.slackers.inc.database.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matt on 3/28/2017.
 */
public class User implements IEntity {

    private static final String TABLE_NAME = "USERLOGINS";

    private Integer id;
    private String firstName;
    private String lastName;
    private String physicalAddress;
    private String phoneNumber;
    private String emailAddress;
    private Boolean isManufacturer;
    private String password; // very safe
    private String brandName;

    public User(){

    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Map<String, Object> getEntityValues() {
        Map<String,Object> out = new HashMap<>();
        out.put("id", id);
        out.put("FirstName", firstName);
        out.put("LastName", lastName);
        out.put("PhysicalAddress", physicalAddress);
        out.put("PhoneNumber", phoneNumber);
        out.put("EmailAddress", emailAddress);
        out.put("IsManufacturer", isManufacturer);
        out.put("Password", password);
        out.put("BrandName", brandName);
        return out;
    }

    @Override
    public void setEntityValues(Map<String, Object> values) {
        if(values.get("id") != null) {

        }
        if(false){

        }
    }

    @Override
    public Map<String, Class> getEntityNameTypePairs() {
        Map<String,Class> out = new HashMap<>();
        out.put("id", Integer.class);
        out.put("FirstName", String.class);
        out.put("LastName", String.class);
        out.put("PhysicalAddress", String.class);
        out.put("PhoneNumber", String.class);
        out.put("EmailAddress", String.class);
        out.put("IsManufacturer", Boolean.class);
        out.put("Password", String.class);
        out.put("BrandName", String.class);
        return out;
    }

    @Override
    public List<String> tableColumnCreationSettings() {
        return null; // not sure how to do this
    }

    public int getId(){
        return id;
    }
}
