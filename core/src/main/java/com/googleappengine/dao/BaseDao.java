package com.googleappengine.dao;

import com.googleappengine.model.BaseEntity;

import java.io.Serializable;

/**
 * Define basic CRUD.
 *
 * @param <T>  the model class.
 * @param <PK> the id type.
 * @author hnguyen
 */
public interface BaseDao<T extends BaseEntity, PK extends Serializable> {
    /**
     * Get method.
     *
     * @param id the entity id.
     * @return the entity.
     */
    T get(PK id);

    /**
     * Save method.
     *
     * @param e the entity to be saved.
     * @return the saved entity.
     */
    T save(T e);

    /**
     * Update method.
     *
     * @param e the entity to be updated.
     * @return the updated entity.
     */
    T update(T e);

    /**
     * Delete method.
     *
     * @param id the entity id to be deleted.
     * @return the deleted entity.
     */
    boolean delete(PK id);
}
