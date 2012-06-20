package com.stormpath.tooter.model;

import com.stormpath.sdk.account.Account;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "T_ACCOUNT")
public class Customer implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    Integer id;

    @Column(name = "userName")
    String userName;

    @Transient
    String password;

    @Transient
    String confirmPassword;

    @Transient
    String firstName;

    @Transient
    String lastName;

    @Transient
    String accountType;

    @Transient
    String email;

    @Transient
    List<Toot> tootList;

    public Customer() {
    }

    public Customer(Customer customer) {
        if (customer != null) {
            setAccountType(customer.getAccountType());
            setConfirmPassword(customer.getConfirmPassword());
            setEmail(customer.getEmail());
            setFirstName(customer.getFirstName());
            setId(customer.getId());
            setLastName(customer.getLastName());
            setPassword(customer.getPassword());
            setTootList(customer.getTootList());
            setUserName(customer.getUserName());
        }
    }

    public Customer(Account account) {
        if (account != null) {
            setEmail(account.getEmail());
            setFirstName(account.getGivenName());
            setLastName(account.getSurname());
            setUserName(account.getUsername());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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