package com.stormpath.tooter.model;

import java.util.List;

public class Customer {


    String userName;

    String password;

    String confirmPassword;

    String firstName;

    String lastName;

    String accountType;

    String email;

    String confirmEmail;

    List<Toot> tootList;

    String tootMessage;

    public String getTootMessage() {
        return tootMessage;
    }

    public void setTootMessage(String tootMessage) {
        this.tootMessage = tootMessage;
    }

    public List<Toot> getTootList() {
        return tootList;
    }

    public void setTootList(List<Toot> tootList) {
        this.tootList = tootList;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public static String BASIC_ACCOUNT = "Basic";


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}