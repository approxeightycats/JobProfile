package com.view;

import com.db.DBInterface;
import com.model.Job;

public class JobProfileCLI {

    private static DBInterface db;

    public static void main(String[] args) {
        db = new DBInterface();

        Job job = new Job("C6");
        System.out.println(job.getJobID());
        job.setName("dog");

        db.addJob(job);
        db.viewTable();

        System.out.println(DBInterface.getOpenClientID());
    }

}
