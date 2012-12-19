package com.googleappengine.service;

import com.googleappengine.LocalDatastoreTestCase;
import com.googleappengine.model.json.Word;
import com.googleappengine.util.WordUtil;
import junit.framework.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class OxfordLookupServiceTest extends LocalDatastoreTestCase {
    public static final Logger LOG = LoggerFactory.getLogger(OxfordLookupServiceTest.class.getCanonicalName());
    @Autowired
    private LookupService oxfordLookupService;

    @Test
    public void testLookup() throws Exception {
        LOG.info("Testing OxfordLookupService...");
        Word w = oxfordLookupService.lookup("come");
        w.getDescription();
        Assert.assertNotNull(w.getPhraseList());
        LOG.info("JSON: " + WordUtil.toJson(w));
    }
}
