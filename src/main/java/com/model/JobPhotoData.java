package com.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class JobPhotoData {

    private StringProperty deliveryDestination;
    private StringProperty outputFormat;
    private StringProperty aspectRatio;
    private StringProperty otherSizingInfo;
    private IntegerProperty PPI;
    private IntegerProperty DPI;
    private StringProperty locations;
    private StringProperty otherInfo;

    public String getDeliveryDestination() {
        return deliveryDestination.get();
    }

    public StringProperty deliveryDestinationProperty() {
        return deliveryDestination;
    }

    public void setDeliveryDestination(String deliveryDestination) {
        this.deliveryDestination.set(deliveryDestination);
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

    public String getOtherSizingInfo() {
        return otherSizingInfo.get();
    }

    public StringProperty otherSizingInfoProperty() {
        return otherSizingInfo;
    }

    public void setOtherSizingInfo(String otherSizingInfo) {
        this.otherSizingInfo.set(otherSizingInfo);
    }

    public int getPPI() {
        return PPI.get();
    }

    public IntegerProperty PPIProperty() {
        return PPI;
    }

    public void setPPI(int PPI) {
        this.PPI.set(PPI);
    }

    public int getDPI() {
        return DPI.get();
    }

    public IntegerProperty DPIProperty() {
        return DPI;
    }

    public void setDPI(int DPI) {
        this.DPI.set(DPI);
    }

    public String getLocations() {
        return locations.get();
    }

    public StringProperty locationsProperty() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations.set(locations);
    }

    public String getOtherInfo() {
        return otherInfo.get();
    }

    public StringProperty otherInfoProperty() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo.set(otherInfo);
    }
}
