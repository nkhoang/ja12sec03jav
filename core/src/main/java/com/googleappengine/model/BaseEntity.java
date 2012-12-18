package com.googleappengine.model;

import com.google.appengine.api.datastore.Key;

/**
 * @author hnguyen
 */
public class BaseEntity {
    private Key id;

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }
}
