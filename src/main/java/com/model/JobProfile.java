package com.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Collects a job, client, and list of equipment.
 */
public class JobProfile {

    private final Job job;
    private final Client client;
    private List<Equipment> equipment;
    private LocalDate date;

    public JobProfile(Job job, Client client) {
        this.job = job;
        this.client = client;
        this.equipment = new ArrayList<>();
    }

    public void schedule(LocalDate date) {
        this.date = date;
    }

    public Job getJob() {
        return job;
    }

    public Client getClient() {
        return client;
    }


}
