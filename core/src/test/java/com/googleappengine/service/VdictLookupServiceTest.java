package com.googleappengine.service;

import com.googleappengine.LocalDatastoreTestCase;
import com.googleappengine.model.json.Word;
import junit.framework.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class VdictLookupServiceTest extends LocalDatastoreTestCase {
    private static final Logger LOG = LoggerFactory.getLogger(VdictLookupServiceTest.class.getCanonicalName());
    @Autowired
    private LookupService vdictLookupService;

    @Test
    public void testLookup() {
        Word w = vdictLookupService.lookup("help");
        Assert.assertNotNull(w);
    }
}
