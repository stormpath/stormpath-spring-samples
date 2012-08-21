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

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Toot is Tooter's concept of a Tweet.
 */
@Entity
@Table(name = "T_TOOT")
public class Toot implements Serializable, Comparable<Toot> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    int tootId;

    @Column(name = "tootMessage")
    String tootMessage;

    @ManyToOne
    Customer customer;


    public int getTootId() {
        return tootId;
    }

    public void setTootId(int tootId) {
        this.tootId = tootId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getTootMessage() {

        return tootMessage;
    }

    public void setTootMessage(String tootMessage) {
        this.tootMessage = tootMessage;
    }

    @Override
    public int compareTo(Toot toot) {

        int result = 1;

        if (toot != null) {

            result = (this.getTootId() - toot.getTootId()) * -1;
        }

        return result;
    }
}
