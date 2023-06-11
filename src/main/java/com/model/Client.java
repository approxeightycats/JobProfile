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

    public void setClientID(String val) {
        clientIDProperty().set(val);
    }

    public void setFirstName(String val) {
        firstNameProperty().set(val);
    }

    public void setLastName(String val) {
        lastNameProperty().set(val);
    }

    public String getClientID() {
        return clientIDProperty().get();
    }

    public String getFirstName() {
        return firstNameProperty().get();
    }

    public String getLastName() {
        return lastNameProperty().get();
    }

    private StringProperty clientIDProperty() {
        if (clientID == null) {
            clientID = new SimpleStringProperty(this, "clientID");
        }
        return clientID;
    }

    public StringProperty firstNameProperty() {
        if (firstName == null) {
            firstName = new SimpleStringProperty(this, "firstName");
        }
        return firstName;
    }

    public StringProperty lastNameProperty() {
        if (lastName == null) {
            lastName = new SimpleStringProperty(this, "lastName");
        }
        return lastName;
    }
}
