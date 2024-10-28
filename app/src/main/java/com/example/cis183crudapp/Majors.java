package com.example.cis183crudapp;

public class Majors {
    private int majorId;
    private String majorName;
    private String majorPrefix;

    public Majors(int majorId, String majorName, String majorPrefix) {
        this.majorId = majorId;
        this.majorName = majorName;
        this.majorPrefix = majorPrefix;
    }

    public int getMajorId() {
        return majorId;
    }

    public String getMajorName() {
        return majorName;
    }

    public String getMajorPrefix() {
        return majorPrefix;
    }
}
