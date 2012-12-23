package com.googleappengine.search;


import com.googleappengine.model.json.Word;
import com.googleappengine.service.LookupService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.lucene.document.Document;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext-dao.xml"})
public class LuceneUtilsTest {
    private static final Logger LOG = LoggerFactory.getLogger(LuceneUtilsTest.class.getCanonicalName());

    @Autowired
    private LookupService vdictLookupService;


    @Test
    public void testSearchById() throws Exception {
        Assert.assertTrue("Failed to search existing word", CollectionUtils.isNotEmpty(LuceneSearchUtils.performSearchById("cons")));
    }

    @Test
    public void testSearchByContent() throws Exception {
        List<Document> foundDocs = LuceneSearchUtils.performSearchByContent("");
        Assert.assertTrue("Failed to search existing word.",
                CollectionUtils.isNotEmpty(foundDocs));

        StringBuilder foundWords = new StringBuilder();
        int index = 0;
        for (Document doc : foundDocs) {
            index++;
            foundWords.append(doc.get(LuceneSearchFields.ID) + " ");
            if (index % 10 == 0) {
                foundWords.append("\n");
                index = 0;
            }
        }
        LOG.info(foundWords.toString());
    }


    @Test
    /**
     * Test write word to Lucene. Perform looking up using the main site service.
     */
    public void testWriteAWord() throws Exception {
        // LOG.info("Total Docs: " + LuceneUtils.getTotalDocs());
        Word w = vdictLookupService.lookup("home");
        LuceneUtils.writeWordToIndex(w);
    }


    @Test
    public void testGetMaxDoc() throws Exception {
        int maxDoc = LuceneUtils.getTotalDocs();
        LOG.info(maxDoc + "");
    }

    @Test
    public void testGetNumDocs() throws Exception {
        int numDocs = LuceneUtils.getIndexReader().numDocs();
        LOG.info("NumDocs : " + numDocs);
    }


    @After
    /**
     * After test close all Lucene writer and index searcher.
     */
    public void closeLuceneUtils() throws Exception {
        LuceneUtils.closeLuceneWriter();
        LuceneUtils.closeSearcher();
    }
}