package com.view;

import com.db.DBController;
import com.model.Job;

public class JobProfileCLI {

    private static DBController db;

    public static void main(String[] args) {
        db = new DBController();

        Job job = new Job("C6");
        System.out.println(job.getJobID());
        job.setName("dog");

        db.addJob(job);
        db.viewTable();

        System.out.println(DBController.getOpenClientID());
    }

}
