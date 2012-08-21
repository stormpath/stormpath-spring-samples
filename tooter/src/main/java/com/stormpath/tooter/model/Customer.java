/*
 * Copyright 2012 Stormpath, Inc. and contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stormpath.tooter.model;

import com.stormpath.sdk.account.Account;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Elder Crisostomo
 */
@Entity
@Table(name = "T_ACCOUNT")
public class Customer implements Serializable {

    public final static String BASIC_ACCOUNT_TYPE = "Basic";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    Integer id;

    @Column(name = "userName")
    String userName;

    @Transient
    String password; //used during password set/reset only.  Do NOT store users' passwords - let Stormpath do that safely for you.

    @Transient
    String confirmPassword; //used during password set/reset only.

    @Transient
    String sptoken; //used during password reset only.

    @Transient
    String firstName;

    @Transient
    String lastName;

    @Transient
    String accountType = BASIC_ACCOUNT_TYPE;

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
            if (account.getGroupMemberships().iterator().hasNext()) {
                setAccountType(account.getGroupMemberships().iterator().next().getGroup().getHref());
            }
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

    public String getSptoken() {
        return sptoken;
    }

    public void setSptoken(String sptoken) {
        this.sptoken = sptoken;
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