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
package com.stormpath.tooter.model.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;

/**
 * @author Elder Crisostomo
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
