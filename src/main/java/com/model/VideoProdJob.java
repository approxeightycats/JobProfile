package com.model;

public class VideoProdJob extends Job {

    private String deliveryDestination;
    private String outputFormat;
    private String aspectRatio;
    private String frequency;
    private String resolution;
    private float fps;
    private String location;
    private boolean live;
    private String sound;

    public VideoProdJob(String clientID) {
        super(clientID);
    }



}
