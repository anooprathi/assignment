package com.anoop.assignment.model;

public enum ErrorCode {
    Not_Found("Customer not found");


    private final String description;
    private final String details;

    ErrorCode(String description, String details) {
        this.description = description;
        this.details = details;
    }

    ErrorCode(String description) {
       this(description, "");
    }

    public String getDescription() {
        return description;
    }

    public String getDetails() {
        return details;
    }
}
