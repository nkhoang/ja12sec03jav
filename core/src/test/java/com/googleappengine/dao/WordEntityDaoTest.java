package com.googleappengine.dao;

import com.googleappengine.LocalDatastoreTestCase;
import com.googleappengine.model.WordEntity;
import com.googleappengine.repository.WordRepository;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author hnguyen
 */
public class WordEntityDaoTest extends LocalDatastoreTestCase {
    private static final Logger LOG = LoggerFactory.getLogger(WordEntityDaoTest.class.getCanonicalName());
    @Autowired
    private WordRepository wordRepository;


    @Test
    public void testFindByWordDict() {
        List<WordEntity> entities = wordRepository.findByDictType(WordEntity.WordDict.VDICT);
        Assert.assertNotNull(entities);
        Assert.assertEquals("Incorrect total word count.", 2, entities.size());
        Assert.assertEquals("house", entities.get(0).getWord());
    }

    @Test
    public void testFindByWordAndDictType() {
        List<WordEntity> entities = wordRepository.findByWordAndDictType("house", WordEntity.WordDict.VDICT);
        Assert.assertNotNull(entities);
        Assert.assertEquals("Incorrect total word count.", 1, entities.size());
        Assert.assertEquals("house", entities.get(0).getWord());
    }


    @Test
    public void testFindByDescription() {
        List<WordEntity> entities = wordRepository.findByWord("house");
        Assert.assertNotNull(entities);
        Assert.assertEquals("Incorrect total word count.", 2, entities.size());
        Assert.assertEquals("house", entities.get(0).getWord());
    }


    @Before
    public void saveSampleWords() {
        WordEntity word = new WordEntity(WordEntity.WordDict.VDICT, "house");
        wordRepository.save(word);
        word = new WordEntity(WordEntity.WordDict.OXFORD, "house");
        wordRepository.save(word);
        word = new WordEntity(WordEntity.WordDict.VDICT, "crew");
        wordRepository.save(word);
    }
}
