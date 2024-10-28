package com.example.cis183crudapp;

public class Student {
    private String fname;
    private String lname;
    private String uname;
    private String email;
    private int age;
    private float gpa;
    private String major;

    public Student(String fname, String lname, String uname, String email, int age, float gpa, String major) {
        this.fname = fname;
        this.lname = lname;
        this.uname = uname;
        this.email = email;
        this.age = age;
        this.gpa = gpa;
        this.major = major;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
