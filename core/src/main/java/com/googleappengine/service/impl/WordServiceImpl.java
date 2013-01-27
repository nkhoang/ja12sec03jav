package com.googleappengine.service.impl;

import com.googleappengine.model.WordEntity;
import com.googleappengine.repository.WordRepository;
import com.googleappengine.service.LookupService;
import com.googleappengine.service.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author hnguyen.
 */
@Service
public class WordServiceImpl implements WordService {
    private static final Logger LOG = LoggerFactory.getLogger(WordServiceImpl.class.getCanonicalName());
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private LookupService vdictLookupService;
    @Autowired
    private LookupService oxfordLookupService;

    public WordEntity lookup(String word) {
        return wordRepository.save(new WordEntity(WordEntity.WordDict.VDICT, WordEntity.WordType.ADJECTIVE, word, word));
    }

    public List<WordEntity> search(String word) {
        return wordRepository.findByWordAndDictType(word, WordEntity.WordDict.VDICT);
    }
}
