/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slackers.inc.database.entities;

import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author John Stegeman <j.stegeman@labyrinth-tech.com>
 */
public class LabelApplication implements IEntity{

    private Date submissionDate;
    private String beverageName;
    private String submitter;
    private int id;
    
    public LabelApplication(String beverageName, String submitter, Date submissionDate)
    {
        this.beverageName = beverageName;
        this.submitter = submitter;
        this.submissionDate = submissionDate;
        this.id=-1;
    }
    public LabelApplication(String beverageName, String submitter)
    {
        this(beverageName, submitter, new java.sql.Date(java.sql.Date.from(Instant.now()).getTime()));
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public String getBeverageName() {
        return beverageName;
    }

    public String getSubmitter() {
        return submitter;
    }

    public int getId() {
        return id;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public void setBeverageName(String beverageName) {
        this.beverageName = beverageName;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }
    
    
    
    @Override
    public String getTableName() {
        return "LabelApplications";
    }

    @Override
    public Map<String, Object> getEntityValues() {
        Map<String,Object> out = new HashMap<>();
        out.put("ApplicationId", this.id);
        out.put("BeverageName", this.beverageName);
        out.put("SubmittedDate", this.submissionDate);
        out.put("SubmitterName", this.submitter);
        return out;
    }

    @Override
    public Map<String, Object> getUpdatableEntityValues() {
        Map<String,Object> out = new HashMap<>();
        out.put("BeverageName", this.beverageName);
        out.put("SubmittedDate", this.submissionDate);
        out.put("SubmitterName", this.submitter);
        return out;
    }

    @Override
    public void setEntityValues(Map<String, Object> values) {
        if (values.get("ApplicationId")!=null)
        {
            this.id = (Integer)values.get("ApplicationId");
        }
        if (values.get("BeverageName")!=null)
        {
            this.beverageName = (String)values.get("BeverageName");
        }
        if (values.get("SubmittedDate")!=null)
        {
            this.submissionDate = (Date)values.get("SubmittedDate");
        }
        if (values.get("SubmitterName")!=null)
        {
            this.submitter = (String)values.get("SubmitterName");
        }
    }

    @Override
    public Map<String, Class> getEntityNameTypePairs() {
        Map<String,Class> out = new HashMap<>();
        out.put("ApplicationId", Integer.class);
        out.put("BeverageName", String.class);
        out.put("SubmittedDate", java.sql.Date.class);
        out.put("SubmitterName", String.class);
        return out;
    }

    @Override
    public List<String> tableColumnCreationSettings() {
        List<String> cols = new LinkedList<>();
        cols.add("ApplicationId int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)");
        cols.add("BeverageName varchar(256)");
        cols.add("SubmittedDate Date");
        cols.add("SubmitterName varchar(128)");
        return cols;
    }

    @Override
    public String toString() {
        return "LabelApplication{" + "\n\tsubmissionDate=" + submissionDate + "\n\tbeverageName=" + beverageName + "\n\tsubmitter=" + submitter + "\n\tid=" + id + '}';
    }
    
    
    
}
