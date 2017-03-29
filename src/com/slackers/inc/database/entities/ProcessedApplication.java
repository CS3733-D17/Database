package com.slackers.inc.database.entities;

import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessedApplication implements IEntity {

    private static final String TABLE_NAME = "PROCESSEDAPPS";

    private Integer id;
    private Integer applicationId;
    private Boolean isAccepted;
    private Date dateProcessed;

    public ProcessedApplication(){

    }

    public int getId() {
        return id;
    }

    public ProcessedApplication setId(Integer newVal) {
        id = newVal;
        return this;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public ProcessedApplication setApplicationId(Integer newVal) {
        applicationId = newVal;
        return this;
    }

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public ProcessedApplication setIsAccepted(Boolean newVal) {
        isAccepted = newVal;
        return this;
    }

    public Date getDateProcessed() {
        return dateProcessed;
    }

    public ProcessedApplication setDateProcessed(Date newVal) {
        dateProcessed = newVal;
        return this;
    }

    public ProcessedApplication setDateProcessedToToday(){
        dateProcessed = new java.sql.Date(java.sql.Date.from(Instant.now()).getTime());
        return this;
    }


    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    @Override
    public Map<String, Object> getEntityValues() {
        Map<String,Object> out = new HashMap<>();
        out.put("id", id);
        out.put("ApplicationId", applicationId);
        out.put("IsAccepted", isAccepted);
        out.put("DateProcessed", dateProcessed);
        return out;
    }

    @Override
    public void setEntityValues(Map<String, Object> values) {
        if(values.get("id") != null){
            id = (Integer) values.get("id");
        }
        if(values.get("ApplicationId") != null){
            applicationId = (Integer) values.get("ApplicationId");
        }
        if(values.get("IsAccepted") != null){
            isAccepted = (Boolean) values.get("IsAccepted");
        }
        if(values.get("DateProcessed") != null){
            dateProcessed = (Date) values.get("DateProcessed");
        }
    }

    @Override
    public Map<String, Class> getEntityNameTypePairs() {
        Map<String,Class> out = new HashMap<>();
        out.put("id", Integer.class);
        out.put("ApplicationId", Integer.class);
        out.put("IsAccepted", Boolean.class);
        out.put("DateProcessed", Date.class);
        return out;
    }

    @Override
    public List<String> tableColumnCreationSettings() {
        return null; // TODO: this method
    }

}
