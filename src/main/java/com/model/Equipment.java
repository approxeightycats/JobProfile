package com.model;

import java.time.LocalDate;

public class Equipment {

    private String department;
    private String name;
    private Float replacementCost;
    private LocalDate dateOfService;

    public Equipment(String dept, String name, Float replacementCost, LocalDate dateOfService) {
        department = dept;
        this.name = name;
        this.replacementCost = replacementCost;
        this.dateOfService = dateOfService;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReplacementCost() {
        return Math.round(replacementCost * 100);
    }

    public void setReplacementCost(Float replacementCost) {
        this.replacementCost = replacementCost;
    }

    public String getDateOfService() {
        return dateOfService.toString();
    }

    public void setDateOfService(LocalDate dateOfService) {
        this.dateOfService = dateOfService;
    }
}
