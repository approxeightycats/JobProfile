package com.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Collects a job, client, and list of equipment.
 */
public class JobProfile {

    private Job job;
    private Client client;
    private List<Equipment> equipment;
    private Float price;
    private LocalDate date;

    public JobProfile(Job job, Client client) {
        this.job = job;
        this.client = client;
        this.equipment = new ArrayList<>();
        this.price = 0.00f;
    }

    public void schedule(LocalDate date) {
        this.date = date;
    }

    public Job getJob() {
        return this.job;
    }


}
