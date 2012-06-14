package com.stormpath.tooter.model.dao;

import com.stormpath.tooter.model.Toot;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/12/12
 * Time: 9:51 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class DefaultTootDao extends BaseHibernateDao implements TootDao {

    @Override
    public List<Toot> getTootsByUserId(int custId) throws Exception {


        Criteria criteria = getSession().createCriteria(Toot.class);
        criteria.add(Restrictions.eq("customer.id", custId));
        List<?> list = new ArrayList<Object>();
        list.addAll(criteria.list());

        List<Toot> tootList = new ArrayList<Toot>(list.size());

        for (Object obj : list) {
            if (obj != null) {
                tootList.add((Toot) obj);
            }
        }

        return tootList;
    }

    @Override
    public Toot saveToot(Toot toot) throws Exception {

        getHibernateTemplate().save(toot);

        return toot;
    }

    @Override
    public void removeTootById(Integer tootId) throws Exception {
        deleteById(Toot.class, Integer.valueOf(tootId));
    }

    @Autowired
    public void init(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }
}
