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
    private StringProperty address;

    private StringProperty clientIDProperty() {
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

    public StringProperty addressProperty() {
        if (address == null) {
            address = new SimpleStringProperty(this, "address");
        }
        return address;
    }

    public void setAddress(String val) {
        addressProperty().set(val);
    }

    public String getAddress() {
        return addressProperty().get();
    }


}
