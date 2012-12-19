package com.googleappengine.service;


import com.googleappengine.model.json.Word;

public interface LookupService {
    /**
     * Common method of Lookup Interface.
     *
     * @param word word to be searched.
     * @return the general {@link com.googleappengine.model.WordEntity} entity.
     */
    Word lookup(String word);


    /**
     * Each service has to define their service name.
     *
     * @return the service name.
     */
    String getServiceName();
}
