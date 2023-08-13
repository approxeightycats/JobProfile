package com.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * contains a client's info
 */
public class Client {

    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty clientID;
    private StringProperty org;
    private StringProperty title;
    private StringProperty email;
    private StringProperty phone;
    private StringProperty addressLine1;
    private StringProperty addressLine2;
    private StringProperty addressCity;
    private StringProperty addressState;
    private StringProperty addressZip;
    private StringProperty contactPrimary;
    private StringProperty contactSecondary;
    private StringProperty contactTertiary;

    public StringProperty addressLine1Property() {
        if (addressLine1 == null) {
            addressLine1 = new SimpleStringProperty(this, "addressLine1");
        }
        return addressLine1;
    }

    public StringProperty addressLine2Property() {
        if (addressLine2 == null) {
            addressLine2 = new SimpleStringProperty(this, "addressLine2");
        }
        return addressLine2;
    }

    public StringProperty addressCityProperty() {
        if (addressCity == null) {
            addressCity = new SimpleStringProperty(this, "addressCity");
        }
        return addressCity;
    }

    public StringProperty addressStateProperty() {
        if (addressState == null) {
            addressState = new SimpleStringProperty(this, "addressState");
        }
        return addressState;
    }

    public StringProperty addressZipProperty() {
        if (addressZip == null) {
            addressZip = new SimpleStringProperty(this, "addressZip");
        }
        return addressZip;
    }

    public StringProperty contactPrimaryProperty() {
        if (contactPrimary == null) {
            contactPrimary = new SimpleStringProperty(this, "contactPrimary");
        }
        return contactPrimary;
    }

    public StringProperty contactSecondaryProperty() {
        if (contactSecondary == null) {
            contactSecondary = new SimpleStringProperty(this, "contactSecondary");
        }
        return contactSecondary;
    }

    public StringProperty contactTertiaryProperty() {
        if (contactTertiary == null) {
            contactTertiary = new SimpleStringProperty(this, "contactTertiary");
        }
        return contactTertiary;
    }

    public void setAddressLine1(String val) {
        addressLine1Property().set(val);
    }

    public void setAddressLine2(String val) {
        addressLine2Property().set(val);
    }

    public void setAddressCity(String val) {
        addressCityProperty().set(val);
    }

    public void setAddressState(String val) {
        addressStateProperty().set(val);
    }

    public void setAddressZip(String val) {
        addressZipProperty().set(val);
    }

    public void setContactPrimary(String val) {
        contactPrimaryProperty().set(val);
    }

    public void setContactSecondary(String val) {
        contactSecondaryProperty().set(val);
    }

    public void setContactTertiary(String val) {
        contactTertiaryProperty().set(val);
    }

    public String getAddressLine1() {
        return addressLine1Property().get();
    }

    public String getAddressLine2() {
        return addressLine2Property().get();
    }

    public String getAddressCity() {
        return addressCityProperty().get();
    }

    public String getAddressState() {
        return addressStateProperty().get();
    }

    public String getAddressZip() {
        return addressZipProperty().get();
    }

    public String getContactPrimary() {
        return contactPrimaryProperty().get();
    }

    public String getContactSecondary() {
        return contactSecondaryProperty().get();
    }

    public String getContactTertiary() {
        return contactTertiaryProperty().get();
    }

    public StringProperty clientIDProperty() {
        if (clientID == null) {
            clientID = new SimpleStringProperty(this, "clientID");
        }
        return clientID;
    }

    public void setClientID(String val) {
        clientIDProperty().set(val);
    }

    public String getClientID() {
        return clientIDProperty().get();
    }

    public StringProperty firstNameProperty() {
        if (firstName == null) {
            firstName = new SimpleStringProperty(this, "firstName");
        }
        return firstName;
    }

    public void setFirstName(String val) {
        firstNameProperty().set(val);
    }

    public String getFirstName() {
        return firstNameProperty().get();
    }

    public StringProperty lastNameProperty() {
        if (lastName == null) {
            lastName = new SimpleStringProperty(this, "lastName");
        }
        return lastName;
    }

    public String getLastName() {
        return lastNameProperty().get();
    }

    public void setLastName(String val) {
        lastNameProperty().set(val);
    }

    public StringProperty orgProperty() {
        if (org == null) {
            org = new SimpleStringProperty(this, "org");
        }
        return org;
    }

    public String getOrg() {
        return orgProperty().get();
    }

    public void setOrg(String val) {
        orgProperty().set(val);
    }

    public StringProperty titleProperty() {
        if (title == null) {
            title = new SimpleStringProperty(this, "title");
        }
        return title;
    }

    public void setTitle(String val) {
        titleProperty().set(val);
    }

    public String getTitle() {
        return titleProperty().get();
    }

    public StringProperty emailProperty() {
        if (email == null) {
            email = new SimpleStringProperty(this, "email");
        }
        return email;
    }

    public void setEmail(String val) {
        emailProperty().set(val);
    }

    public String getEmail() {
        return emailProperty().get();
    }

    public StringProperty phoneProperty() {
        if (phone == null) {
            phone = new SimpleStringProperty(this, "phone");
        }
        return phone;
    }

    public void setPhone(String val) {
        phoneProperty().set(val);
    }

    public String getPhone() {
        return phoneProperty().get();
    }



}
