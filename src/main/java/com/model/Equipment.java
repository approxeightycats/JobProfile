package com.model;

import java.time.LocalDate;

public class Equipment {

    private String name;
    private LocalDate dateOfService;
    private Float replacementCost;
    private Float rentalRate;

    public Equipment(String name) {
        this.name = name;
    }

    public void setPurchaseDate(LocalDate date) {
        dateOfService = date;
    }

    public void setRentalRate(Float rate) {
        rentalRate = rate;
    }

}
