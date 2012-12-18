package com.googleappengine.dao;

import com.googleappengine.LocalDatastoreTestCase;
import com.googleappengine.model.WordEntity;
import junit.framework.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hnguyen
 */
public class WordEntityDaoTest extends LocalDatastoreTestCase {
    private static final Logger LOG = LoggerFactory.getLogger(WordEntityDaoTest.class.getCanonicalName());
    @Autowired
    private WordEntityDao dao;

    @Test
    public void testSaveAWord() {
        LOG.info("Testing save a word.");
        WordEntity entity = new WordEntity("hello", WordEntity.WordType.NOUN);

        WordEntity word = dao.save(entity);
        // check entity
        Assert.assertNotNull(word.getId());
        WordEntity dbWord = dao.get(entity.getId());
        LOG.debug("Found word: " + dbWord);
        Assert.assertNotNull(dbWord);
        Assert.assertNotNull(dbWord.getTimeStamp());
    }

    @Test
    public void testGetWordByName() {
        LOG.info("Testing get word by name");

        saveSampleWords();

        WordEntity word = dao.getWordByName("house");
        Assert.assertEquals("house", word.getWord());
    }

    private void saveSampleWords() {
        WordEntity word = new WordEntity("house", WordEntity.WordType.NOUN);
        dao.save(word);
        word = new WordEntity("crew", WordEntity.WordType.NOUN);
        dao.save(word);
    }
}
