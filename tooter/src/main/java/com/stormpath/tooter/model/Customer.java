package com.stormpath.tooter.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_ACCOUNT")
public class Customer {


    public static String BASIC_ACCOUNT = "Basic";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    Integer id;

    @Column(name = "userName")
    String userName;

    @Column(name = "password")
    String password;

    @Transient
    String confirmPassword;

    @Column(name = "firstName")
    String firstName;

    @Column(name = "lastName")
    String lastName;

    @Column(name = "accountType")
    String accountType;

    @Column(name = "email")
    String email;

    @Transient
    List<Toot> tootList;

    @Transient
    String tootMessage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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