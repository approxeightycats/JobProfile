package com.model;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.SplittableRandom;

public class Equipment {

    private StringProperty item;
    private StringProperty department;
    private StringProperty status;
    private StringProperty serial;
    private IntegerProperty replacementCost;
    private IntegerProperty rentalCost;
    private StringProperty dateOfService;
    private BooleanProperty toggle;

    public String getItem() {
        return itemProperty().get();
    }

    public StringProperty itemProperty() {
        if (item == null) {
            item = new SimpleStringProperty(this, "item");
        }
        return item;
    }

    public void setItem(String item) {
        itemProperty().set(item);
    }

    public String getDepartment() {
        return departmentProperty().get();
    }

    public StringProperty departmentProperty() {
        if (department == null) {
            department = new SimpleStringProperty(this, "department");
        }
        return department;
    }

    public void setDepartment(String department) {
        departmentProperty().set(department);
    }

    public String getStatus() {
        return statusProperty().get();
    }

    public StringProperty statusProperty() {
        if (status == null) {
            status = new SimpleStringProperty(this, "status");
        }
        return status;
    }

    public void setStatus(String status) {
        statusProperty().set(status);
    }

    public String getSerial() {
        return serialProperty().get();
    }

    public StringProperty serialProperty() {
        if (serial == null) {
            serial = new SimpleStringProperty(this, "serial");
        }
        return serial;
    }

    public void setSerial(String serial) {
        serialProperty().set(serial);
    }

    public Integer getReplacementCost() {
        return replacementCostProperty().get();
    }

    public IntegerProperty replacementCostProperty() {
        if (replacementCost == null) {
            replacementCost = new SimpleIntegerProperty(this, "replacementCost");
        }
        return replacementCost;
    }

    public void setReplacementCost(Integer replacementCost) {
        replacementCostProperty().set(replacementCost);
    }

    public Integer getRentalCost() {
        return rentalCostProperty().get();
    }

    public IntegerProperty rentalCostProperty() {
        if (rentalCost == null) {
            rentalCost = new SimpleIntegerProperty(this, "rentalCost");
        }
        return rentalCost;
    }

    public void setRentalCost(Integer rentalCost) {
        rentalCostProperty().set(rentalCost);
    }

    public String getDateOfService() {
        return dateOfServiceProperty().get();
    }

    public StringProperty dateOfServiceProperty() {
        if (dateOfService == null) {
            dateOfService = new SimpleStringProperty(this, "dateOfService");
        }
        return dateOfService;
    }

    public BooleanProperty toggleProperty() {
        if (toggle == null) {
            toggle = new SimpleBooleanProperty(this, "on");
        }
        return toggle;
    }

    public void setDateOfService(String dateOfService) {
        dateOfServiceProperty().set(dateOfService);
    }
}
