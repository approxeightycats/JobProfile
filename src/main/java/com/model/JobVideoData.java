package com.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class JobVideoData {

    private StringProperty deliveryDestination;
    private StringProperty outputFormat;
    private StringProperty aspectRatio;
    private StringProperty systemFreq;
    private StringProperty resolution;
    private StringProperty fps;
    private StringProperty location;
    private IntegerProperty cameras;
    private StringProperty estLength;

    public String getDeliveryDestination() {
        return deliveryDestination.get();
    }

    public StringProperty deliveryDestinationProperty() {
        if (deliveryDestination == null) {
            deliveryDestination = new SimpleStringProperty(this, "deliveryDestination");
        }
        return deliveryDestination;
    }

    public void setDeliveryDestination(String deliveryDestination) {
        deliveryDestinationProperty().set(deliveryDestination);
    }

    public String getOutputFormat() {
        return outputFormat.get();
    }

    public StringProperty outputFormatProperty() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat.set(outputFormat);
    }

    public String getAspectRatio() {
        return aspectRatio.get();
    }

    public StringProperty aspectRatioProperty() {
        return aspectRatio;
    }

    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio.set(aspectRatio);
    }

    public String getSystemFreq() {
        return systemFreq.get();
    }

    public StringProperty systemFreqProperty() {
        return systemFreq;
    }

    public void setSystemFreq(String systemFreq) {
        this.systemFreq.set(systemFreq);
    }

    public String getResolution() {
        return resolution.get();
    }

    public StringProperty resolutionProperty() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution.set(resolution);
    }

    public String getFps() {
        return fps.get();
    }

    public StringProperty fpsProperty() {
        return fps;
    }

    public void setFps(String fps) {
        this.fps.set(fps);
    }

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public int getCameras() {
        return cameras.get();
    }

    public IntegerProperty camerasProperty() {
        return cameras;
    }

    public void setCameras(int cameras) {
        this.cameras.set(cameras);
    }

    public String getEstLength() {
        return estLength.get();
    }

    public StringProperty estLengthProperty() {
        return estLength;
    }

    public void setEstLength(String estLength) {
        this.estLength.set(estLength);
    }
}
