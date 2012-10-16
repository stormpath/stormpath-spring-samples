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
public class User implements Serializable {

    public final static String NO_GROUP = "Basic";

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
    String email;

    @Transient
    String groupUrl;

    @Transient
    List<Toot> tootList;

    @Transient
    Account account;

    public User() {
    }

    public User(User user) {
        if (user != null) {
            setAccount(user.getAccount());
            setConfirmPassword(user.getConfirmPassword());
            setEmail(user.getEmail());
            setFirstName(user.getFirstName());
            setId(user.getId());
            setLastName(user.getLastName());
            setPassword(user.getPassword());
            setTootList(user.getTootList());
            setUserName(user.getUserName());
        }
    }

    public User(Account account) {
        if (account != null) {
            setEmail(account.getEmail());
            setFirstName(account.getGivenName());
            setLastName(account.getSurname());
            setUserName(account.getUsername());
            setAccount(account);
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

    /**
     * Return the email associated with this user. If available, use the Stormpath SDK Account value, otherwise return the
     * local property. We do this because the user class is a form backing bean for authentication, profile updates, etc.
     *
     * @return the email from the Stormpath SDK Account object if the account is not null, otherwise return the underlying property
     */
    public String getEmail() {
        return account != null ? account.getEmail() : email;
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

    /**
     * Return the username associated with this user. If available, use the Stormpath SDK Account value, otherwise return the
     * local property. We do this because the user class is a form backing bean for authentication, profile updates, etc.
     *
     * @return the username from the Stormpath SDK Account object if the account is not null, otherwise return the underlying property
     */
    public String getUserName() {
        return account != null ? account.getUsername() : userName;
    }

    public void setUserName(String userName) {
        if (account != null) {
            this.account.setUsername(userName);
        } else {
            this.userName = userName;
        }
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

    /**
     * Return the first name associated with this user. If available, use the Stormpath SDK Account value, otherwise return the
     * local property. We do this because the user class is a form backing bean for authentication, profile updates, etc.
     *
     * @return the first name from the Stormpath SDK Account object if the account is not null, otherwise return the underlying property
     */
    public String getFirstName() {
        return account != null ? account.getGivenName() : firstName;
    }

    public void setFirstName(String firstName) {
        if (account != null) {
            account.setGivenName(firstName);
        } else {
            this.firstName = firstName;
        }
    }

    /**
     * Return the last name associated with this user. If available, use the Stormpath SDK Account value, otherwise return the
     * local property. We do this because the user class is a form backing bean for authentication, profile updates, etc.
     *
     * @return the last name from the Stormpath SDK Account object if the account is not null, otherwise return the underlying property
     */
    public String getLastName() {
        return account != null ? account.getSurname() : lastName;
    }

    public void setLastName(String lastName) {
        if (account != null) {
            this.account.setSurname(lastName);

        } else {
            this.lastName = lastName;
        }
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Return the name of the group for this user.
     *
     * @return the name of the group associated with this user, or "Basic" if the user is not assigned to any groups in Stormpath
     */
    public String getGroupName() {
        if (this.account.getGroupMemberships().iterator().hasNext()) {
            return this.account.getGroupMemberships().iterator().next().getGroup().getName();
        } else {
            return NO_GROUP;
        }
    }

    public String getGroupUrl() {
        if (this.account != null && this.account.getGroupMemberships().iterator().hasNext()) {
            return this.account.getGroupMemberships().iterator().next().getGroup().getHref();
        } else {
            return groupUrl;
        }
    }

    public void setGroupUrl(String groupUrl) {
        this.groupUrl = groupUrl;
    }
}