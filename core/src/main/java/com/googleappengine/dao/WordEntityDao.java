package com.googleappengine.dao;

import com.googleappengine.model.WordEntity;

/**
 * Word Entity DAO interface.
 *
 * @author hnguyen.
 */
public interface WordEntityDao extends BaseDao<WordEntity, Long> {

    /**
     * Get word by word description.
     *
     * @param word the word description.
     * @return the word entity.
     */
    public WordEntity getWordByName(String word);
}
