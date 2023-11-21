package com.example.bus_tracking_system.Model;

public class UsersModel {

    String userType;
    String collegeId;
    String emailId;

    public UsersModel() {
    }

    public UsersModel(String emailId, String userType, String collegeId) {
        this.emailId = emailId;
        this.userType = userType;
        this.collegeId = collegeId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String schoolId) {
        this.collegeId = collegeId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
