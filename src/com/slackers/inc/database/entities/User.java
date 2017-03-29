package com.slackers.inc.database.entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class User implements IEntity {

    private static final String TABLE_NAME = "USERLOGINS";

    private Integer userId;
    private String firstName;
    private String lastName;
    private String physicalAddress;
    private String phoneNumber;
    private String emailAddress;
    private Boolean isManufacturer;
    private String password;
    private String brandName;

    public User(){

    }

    public Integer getUserId() {
        return userId;
    }

    public User setUserId(Integer newVal) {
        userId = newVal;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String newVal) {
        firstName = newVal;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String newVal) {
        lastName = newVal;
        return this;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public User setPhysicalAddress(String newVal) {
        physicalAddress = newVal;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String newVal) {
        phoneNumber = newVal;
        return this;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public User setEmailAddress(String newVal) {
        emailAddress = newVal;
        return this;
    }

    public Boolean getIsManufacturer() {
        return isManufacturer;
    }

    public User setIsManufacturer(Boolean newVal) {
        isManufacturer = newVal;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String newVal) {
        password = newVal;
        return this;
    }

    public String getBrandName() {
        return brandName;
    }

    public User setBrandName(String newVal) {
        brandName = newVal;
        return this;
    }


    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    @Override
    public Map<String, Object> getEntityValues() {
        Map<String,Object> out = new HashMap<>();
        out.put("UserId", userId);
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
    public Map<String, Object> getUpdatableEntityValues() {
        Map<String,Object> out = new HashMap<>();
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
        if(values.get("UserId") != null){
            userId = (Integer) values.get("UserId");
        }
        if(values.get("FirstName") != null){
            firstName = (String) values.get("FirstName");
        }
        if(values.get("LastName") != null){
            lastName = (String) values.get("LastName");
        }
        if(values.get("PhysicalAddress") != null){
            physicalAddress = (String) values.get("PhysicalAddress");
        }
        if(values.get("PhoneNumber") != null){
            phoneNumber = (String) values.get("PhoneNumber");
        }
        if(values.get("EmailAddress") != null){
            emailAddress = (String) values.get("EmailAddress");
        }
        if(values.get("IsManufacturer") != null){
            isManufacturer = (Boolean) values.get("IsManufacturer");
        }
        if(values.get("Password") != null){
            password = (String) values.get("Password");
        }
        if(values.get("BrandName") != null){
            brandName = (String) values.get("BrandName");
        }
    }

    @Override
    public Map<String, Class> getEntityNameTypePairs() {
        Map<String,Class> out = new HashMap<>();
        out.put("UserId", Integer.class);
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
        List<String> cols = new LinkedList<>();
        cols.add("UserId int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)");
        cols.add("FirstName varchar(128)");
        cols.add("LastName varchar(128)");
        cols.add("PhysicalAddress varchar(128)");
        cols.add("PhoneNumber varchar(128)");
        cols.add("EmailAddress varchar(128)");
        cols.add("Password varchar(128)");
        cols.add("BrandName varchar(128)");
        cols.add("IsManufacturer int");
        return cols;
    }

    @Override
    public int getId() {
        return userId;
    }

}