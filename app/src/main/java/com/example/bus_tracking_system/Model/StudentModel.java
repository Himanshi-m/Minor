package com.example.bus_tracking_system.Model;

public class StudentModel {

    String studentName;
    String rollNo;
    String address;
    String city;
    String state;
    String country;
    String picDropLocation;
    String ContactNumber;
    String studentEmail;
    String routeNumber;
    String busNumber;

    public StudentModel() {
    }

    public StudentModel(String studentName,
                        String rollNo,
                        String address,
                        String city,
                        String state,
                        String country,
                        String picDropLocation,
                        String ContactNumber,
                        String studentEmail,
                        String routeNumber,
                        String busNumber) {
        this.studentName = studentName;
        this.rollNo = rollNo;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.picDropLocation = picDropLocation;
        this.ContactNumber = ContactNumber;
        this.studentEmail = studentEmail;
        this.routeNumber = routeNumber;
        this.busNumber = busNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPicDropLocation() {
        return picDropLocation;
    }

    public void setPicDropLocation(String picDropLocation) {
        this.picDropLocation = picDropLocation;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String ContactNumber) {
        this.ContactNumber = ContactNumber;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setBusRoute(String busRoute) {
        this.routeNumber = routeNumber;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }
}
