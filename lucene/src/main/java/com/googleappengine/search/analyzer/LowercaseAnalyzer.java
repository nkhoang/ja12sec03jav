package com.googleappengine.search.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;
import org.apache.lucene.util.Version;

import java.io.Reader;

public final class LowercaseAnalyzer extends Analyzer {

    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
        LowerCaseTokenizer tokenizer = new LowerCaseTokenizer(Version.LUCENE_40, reader);
        // use LowerCaseFilter.
        LowerCaseFilter filter = new LowerCaseFilter(Version.LUCENE_40, tokenizer);

        return new TokenStreamComponents(tokenizer, filter);
    }

}
