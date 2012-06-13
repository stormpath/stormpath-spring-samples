package com.stormpath.tooter.model.dao;

import com.stormpath.tooter.model.Toot;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/12/12
 * Time: 9:51 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class DefaultTootDao extends HibernateDaoSupport implements TootDao {

    @Override
    public List<Toot> getTootsByUserId(int custId) throws Exception {


        //return getHibernateTemplate().find("from Toot t where t.customer.id=:tootId", custId);
        return getHibernateTemplate().find("from Toot");
    }

    @Override
    public Toot saveToot(Toot toot) throws Exception {

        getHibernateTemplate().save(toot);

        return toot;
    }

    @Override
    public void removeToot(Toot toot) throws Exception {
        getHibernateTemplate().delete(toot);
    }

    @Autowired
    public void init(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }
}
