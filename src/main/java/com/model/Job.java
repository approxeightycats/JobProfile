package com.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Contains technical data for a given job.
 */
public class Job {

    private StringProperty jobId;
    private StringProperty jobName;
    private StringProperty clientName;
    private StringProperty clientId;
    private StringProperty contact;
    private StringProperty jobTypes;
    private StringProperty description;
    private StringProperty dates; //placeholder
    private StringProperty estimate;
    private StringProperty notes;

    private final JobVideoData jobVideoData;
    private final JobPhotoData jobPhotoData;
    private final JobAudioData jobAudioData;

    public Job() {
        jobVideoData = new JobVideoData();
        jobPhotoData = new JobPhotoData();
        jobAudioData = new JobAudioData();
    }

    private StringProperty jobIdProperty() {
        if (jobId == null) {
            jobId = new SimpleStringProperty(this, "jobId");
        }
        return jobId;
    }

    private StringProperty jobNameProperty() {
        if (jobName == null) {
            jobName =  new SimpleStringProperty(this, "jobName");
        }
        return jobName;
    }

    private StringProperty clientNameProperty() {
        if (clientName == null) {
            clientName = new SimpleStringProperty(this, "clientName");
        }
        return clientName;
    }

    private StringProperty clientIdProperty() {
        if (clientId == null) {
            clientId =  new SimpleStringProperty(this, "clientId");
        }
        return clientId;
    }

    private StringProperty contactProperty() {
        if (contact == null) {
            contact =  new SimpleStringProperty(this, "contact");
        }
        return contact;
    }

    private StringProperty jobTypesProperty() {
        if (jobTypes == null) {
            jobTypes =  new SimpleStringProperty(this, "jobTypes");
        }
        return jobTypes;
    }

    private StringProperty descriptionProperty() {
        if (description == null) {
            description =  new SimpleStringProperty(this, "description");
        }
        return description;
    }

    private StringProperty datesProperty() {
        if (dates == null) {
            dates =  new SimpleStringProperty(this, "dates");
        }
        return dates;
    }

    private StringProperty estimateProperty() {
        if (estimate == null) {
            estimate =  new SimpleStringProperty(this, "estimate");
        }
        return estimate;
    }

    private StringProperty notesProperty() {
        if (notes == null) {
            notes =  new SimpleStringProperty(this, "notes");
        }
        return notes;
    }

    public void setJobId(String val) {
        jobIdProperty().set(val);
    }

    public void setJobName(String val) {
        jobNameProperty().set(val);
    }

    public void setClientName(String val) {
        clientNameProperty().set(val);
    }

    public void setClientId(String val) {
        clientIdProperty().set(val);
    }

    public void setContact(String val) {
        contactProperty().set(val);
    }

    public void setJobTypes(String val) {
        jobTypesProperty().set(val);
    }

    public void setDescription(String val) {
        descriptionProperty().set(val);
    }

    public void setDates(String val) {
        datesProperty().set(val);
    }

    public void setEstimate(String val) {
        estimateProperty().set(val);
    }

    public void setNotes(String val) {
        notesProperty().set(val);
    }

    public String getJobId() { return jobId.get(); }
    public String getJobName() { return jobName.get(); }
    public String getClientName() { return clientName.get(); }
    public String getClientId() { return clientId.get(); }
    public String getContact() { return contact.get(); }
    public String getJobTypes() { return jobTypes.get(); }
    public String getDesc() { return description.get(); }
    public String getDates() { return dates.get(); }
    public String getEstimatedPrice() { return estimate.get(); }
    public String getNotes() { return notes.get(); }

    public JobVideoData getJobVideoData() {
        return jobVideoData;
    }

    public JobAudioData getJobAudioData() {
        return jobAudioData;
    }

    public JobPhotoData getJobPhotoData() {
        return jobPhotoData;
    }

}
