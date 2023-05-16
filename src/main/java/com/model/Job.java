package com.model;

/**
 * Contains technical data for a given job.
 */
public class Job {

    private final int jobID;
    private String deliveryDestination;
    private String outputFormat;
    private String aspectRatio;
    private String frequency;
    private String resolution;
    private float fps;
    private String location;
    private int cameras;
    private boolean live;
    private String sound;
    private int length;

    public Job() {
        jobID = DBInterface.getOpenJobID();
    }

    public int getJobID() {
        return jobID;
    }

    public String getDeliveryDestination() {
        return deliveryDestination;
    }

    public void setDeliveryDestination(String deliveryDestination) {
        this.deliveryDestination = deliveryDestination;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public float getFps() {
        return fps;
    }

    public void setFps(float fps) {
        this.fps = fps;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCameras() {
        return cameras;
    }

    public void setCameras(int cameras) {
        this.cameras = cameras;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
