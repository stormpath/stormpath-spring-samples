package com.stormpath.tooter.model;

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/8/12
 * Time: 6:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Toot {


    String tootMessage;

    Customer customer;

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

}
