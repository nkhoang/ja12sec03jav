package com.googleappengine.service;

import com.googleappengine.model.WordEntity;

import java.util.List;

/**
 * @author hnguyen.
 */
public interface WordService {
    WordEntity lookup(String word);

    List<WordEntity> search(String word);
}
