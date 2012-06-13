package com.stormpath.tooter.model.dao;

import com.stormpath.tooter.model.Customer;

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/12/12
 * Time: 9:35 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CustomerDao {

    Customer getCustomerByUserName(String userName) throws Exception;

    Customer saveOrUpdateCustomer(Customer customer) throws Exception;
}
