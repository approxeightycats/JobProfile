package com.model;

import com.db.DBInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains technical data for a given job.
 */
public class Job {

    private final int jobID;
    private final String clientID;
    private String name;
    private int estProjectLength;
    private int estCost;
    private int estProductionTime;
    private String status;

    public Job(String clientID) {
        jobID = DBInterface.getOpenJobID();
        this.clientID = clientID;
    }

    public int getJobID() {
        return jobID;
    }

    public String getClientID() {
        return clientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEstProjectLength() {
        return estProjectLength;
    }

    public void setEstProjectLength(int estProjectLength) {
        this.estProjectLength = estProjectLength;
    }

    public int getEstCost() {
        return estCost;
    }

    public void setEstCost(int estCost) {
        this.estCost = estCost;
    }

    public int getEstProductionTime() {
        return estProductionTime;
    }

    public void setEstProductionTime(int estProductionTime) {
        this.estProductionTime = estProductionTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status ) {
        this.status = status;
    }

}
