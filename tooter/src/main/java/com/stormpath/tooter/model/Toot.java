package com.stormpath.tooter.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/8/12
 * Time: 6:17 PM
 * To change this template use File | Settings | File Templates.
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
