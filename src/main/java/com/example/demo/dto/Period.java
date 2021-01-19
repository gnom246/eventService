package com.example.demo.dto;

public enum Period {
    ALL("All"),
    FUTURE("Future"),
    PRESENT_AND_FUTURE("Present or Future");

    private String userValue;

    Period(String userValue){
        this.userValue = userValue;
    }
}
