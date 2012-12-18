package com.googleappengine.dao.impl;

import com.googleappengine.dao.BaseDao;
import com.googleappengine.model.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;

/**
 * The implementation of {@link BaseDao}.
 *
 * @author hoangnk
 */
@Repository
@Transactional
public abstract class BaseDaoImpl<T extends BaseEntity, PK extends Serializable> implements BaseDao<T, PK> {
    private static final Logger LOG = LoggerFactory.getLogger(BaseDaoImpl.class.getCanonicalName());
    @PersistenceContext
    protected EntityManager entityManager;


    // Extender must implement this method to provide the class name.
    public abstract String getClassName();

    /**
     * {@inheritDoc}
     */
    public boolean delete(PK id) {
        // no need to do anything further.
        if (id == null) {
            return false;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Delete an object type of %s with id: [%s]", getClassName(), id));
        }
        boolean result = false;
        try {
            String queryString = String.format("Delete from %s i where i.id=%s", getClassName(), id);
            Query query = entityManager.createQuery(queryString);
            query.executeUpdate();
            entityManager.flush();
            result = true;
        } catch (Exception e) {
            LOG.error(String.format("Could not delete an object type of %s with id: [%s].", getClassName(), id), e);
        }
        return result;
    }


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T get(PK id) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Getting object type of %s with id: [%s]", getClassName(), id));
        }
        if (id == null) {
            return null;
        }
        try {
            String queryString = String.format("Select from %s t where t.id=:valueId", getClassName());
            Query query = entityManager.createQuery(queryString);
            query.setParameter("valueId", id);

            Object o = query.getSingleResult();
            if (o != null) {
                return (T) o;
            }
        } catch (Exception e) {
            LOG.error(String.format("Could not get an object type of %s with id: [%s].", getClassName(), id), e);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public T save(T e) {
        if (e == null) {
            return null;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Save an object type of %s.", getClassName()));
        }

        entityManager.persist(e);
        entityManager.flush();
        return e;
    }

    /**
     * {@inheritDoc}
     */
    public T update(T e) {
        T result = null;
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Update an object type of %s with id: %s.", getClassName(), e.getId()));
        }
        try {
            entityManager.merge(e);
            entityManager.flush();
            result = e;
        } catch (Exception ex) {
            LOG.info(String.format("Could not update entity type of %s with id: [%s]", getClassName(),
                    e.getId()), e.toString());
            LOG.error("Error", ex);
        }
        return result;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
