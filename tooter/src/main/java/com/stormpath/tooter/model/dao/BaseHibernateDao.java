package com.stormpath.tooter.model.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/13/12
 * Time: 12:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseHibernateDao extends HibernateDaoSupport {

    public <T> void deleteById(Class<T> entityType, Serializable id) {
        delete(load(entityType, id));
    }

    public void delete(Object entity) throws DataAccessException {
        getHibernateTemplate().delete(entity);
    }

    public <T> T load(Class<T> entityClass, Serializable id) throws DataAccessException {
        return getHibernateTemplate().load(entityClass, id);
    }

    public void update(Object entity) throws DataAccessException {
        getHibernateTemplate().update(entity);
    }

    public Serializable save(Object entity) throws DataAccessException {
        return getHibernateTemplate().save(entity);
    }
}
