package com.stormpath.tooter.model.dao;

import com.stormpath.tooter.model.Customer;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/12/12
 * Time: 9:38 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class DefaultCustomerDao extends HibernateDaoSupport implements CustomerDao {


    @Override
    public Customer getCustomerByUserName(String userName) throws Exception {

        Criteria criteria = getSession().createCriteria(Customer.class);
        criteria.add(Restrictions.eq("userName", userName));
        List<?> list = new ArrayList<Object>();
        list.addAll(criteria.list());

        Customer customer = new Customer();

        if (!list.isEmpty()) {
            for (Object obj : list) {
                if (obj != null) {
                    customer = (Customer) obj;
                    break;
                }
            }
        }
        return customer;
    }

    @Override
    public Customer saveOrUpdateCustomer(Customer customer) throws Exception {

        getHibernateTemplate().saveOrUpdate(customer);

        return customer;
    }

    @Autowired
    public void init(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }
}
