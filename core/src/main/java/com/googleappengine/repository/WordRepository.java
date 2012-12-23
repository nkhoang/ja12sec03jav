package com.googleappengine.repository;

import com.google.appengine.api.datastore.Key;
import com.googleappengine.model.WordEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Extended interface for handling Word.
 */
public interface WordRepository extends CrudRepository<WordEntity, Key> {
    List<WordEntity> findByWord(String word);

    List<WordEntity> findByDictType(WordEntity.WordDict type);

    List<WordEntity> findByWordAndDictType(String word, WordEntity.WordDict type);
}
