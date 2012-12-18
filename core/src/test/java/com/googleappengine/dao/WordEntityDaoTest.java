package com.googleappengine.dao;

import com.googleappengine.LocalDatastoreTestCase;
import com.googleappengine.model.WordEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hnguyen
 */
public class WordEntityDaoTest extends LocalDatastoreTestCase {
    @Autowired
    private WordEntityDao dao;

    @Test
    public void testSaveAWord() {
        WordEntity entity = new WordEntity();
        entity.setWord("hello");

        dao.save(entity);
    }
}
