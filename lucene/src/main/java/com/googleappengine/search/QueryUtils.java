package com.googleappengine.search;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;

/**
 * Query Utils.
 */
public class QueryUtils {
    /**
     * Search word by word content.
     *
     * @param searchString the search string which will be split by spaces to compose a {@link org.apache.lucene.search.PhraseQuery}.
     * @return the built {@link org.apache.lucene.search.PhraseQuery}.
     */
    public static Query buildPhraseQuery(String searchString) {
        PhraseQuery query = new PhraseQuery();
        String[] searchTerms = searchString.split(" ");
        for (int i = 0; i < searchTerms.length; i++) {
            query.add(new Term(LuceneSearchFields.WORD_CONTENT, searchTerms[i]));
        }
        query.setSlop(3);
        return query;
    }


    /**
     * Build fuzzy search query.
     *
     * @param searchString the search string.
     * @return the built query.
     */
    public static Query buildFuzzyQuery(String searchString) {
        return new FuzzyQuery(new Term(searchString), 2, 2);
    }
}
