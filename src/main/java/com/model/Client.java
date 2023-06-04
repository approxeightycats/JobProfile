package com.model;

import com.db.DBController;

/**
 * contains a client's info
 */
public class Client {

    private final String clientID;
    private String name;
    private String title;
    private String email;
    private String phone;
    private String address;

    public Client() {
        clientID = DBController.getOpenClientID();
    }



    public String getClientID() {
        return clientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        if (address == null) {
            return "No address.";
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
