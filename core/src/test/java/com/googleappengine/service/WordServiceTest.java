package com.googleappengine.service;

import com.googleappengine.LocalDatastoreTestCase;
import com.googleappengine.model.WordEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WordServiceTest extends LocalDatastoreTestCase {
    @Autowired
    private WordService wordService;

    @Test
    public void testWordService() {
        WordEntity word = wordService.lookup("hello");
    }
}
