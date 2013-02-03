package com.googleappengine.util;

import junit.framework.Assert;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.spring.security3.PasswordEncoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath*:applicationContext-dao.xml", "classpath*:applicationContext-service.xml"})
public class PassWordUtilsTest {
    private static final Logger LOG = LoggerFactory.getLogger(PassWordUtilsTest.class.getCanonicalName());
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StandardPBEStringEncryptor propertyEncryptor;

    @Test
    public void testEncodePassword() {
        String output = passwordEncoder.encodePassword("babyonemoretime", null);
        LOG.info(String.format("Password = %s", output));
        Assert.assertNotNull(output);
    }

    @Test
    public void testPropertyEncoder() {
        String digestedPassword = propertyEncryptor.encrypt("a password to be digested");
        Assert.assertNotNull(digestedPassword);
        String decodedPassword = propertyEncryptor.decrypt(digestedPassword);
        Assert.assertEquals("a password to be digested", decodedPassword);
    }
}
