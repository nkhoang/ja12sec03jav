package com.googleappengine.service;

import com.googleappengine.model.WordEntity;

/**
 * @author hnguyen.
 */
public interface WordService {
    WordEntity lookup(String word);
}
