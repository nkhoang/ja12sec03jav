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
    public void testWordRepository() {
        saveSampleWords();

        Iterable<WordEntity> words = wordRepository.findAll();
        Assert.assertTrue(words.iterator().hasNext());
    }

    @Test
    public void tetFindByWordType() {
        List<WordEntity> entities = wordRepository.findByWordType(WordEntity.WordType.ADJECTIVE.name());
        Assert.assertNotNull(entities);
        Assert.assertEquals("Incorrect total word count.", 1, entities.size());
    }

    @Test
    public void testFindByDescription() {
        List<WordEntity> entities = wordRepository.findByWord("house");
        Assert.assertNotNull(entities);
        Assert.assertEquals("Incorrect total word count.", 2, entities.size());
        Assert.assertEquals("house", entities.get(0).getWord());
    }

    @Test
    public void testFindWordByDescriptionAndWordType() {
        List<WordEntity> entities = wordRepository.findByWordAndWordType("house",
                WordEntity.WordType.NOUN.name());
        Assert.assertNotNull(entities);
        Assert.assertEquals("Incorrect total word count.", 1, entities.size());
        Assert.assertEquals("house", entities.get(0).getWord());
        Assert.assertEquals(WordEntity.WordType.NOUN.name(), entities.get(0).getWordType());
    }

    @Before
    public void saveSampleWords() {
        WordEntity word = new WordEntity("house", WordEntity.WordType.NOUN.name());
        wordRepository.save(word);
        word = new WordEntity("house", WordEntity.WordType.ADJECTIVE.name());
        wordRepository.save(word);
        word = new WordEntity("crew", WordEntity.WordType.NOUN.name());
        wordRepository.save(word);
    }
}
