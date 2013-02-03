package com.googleappengine.dao;

import com.googleappengine.LocalDatastoreTestCase;
import com.googleappengine.model.User;
import com.googleappengine.repository.UserRepository;
import junit.framework.Assert;
import org.jasypt.spring.security3.PasswordEncoder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class UserDaoTest extends LocalDatastoreTestCase {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testSaveUser() {
        User u = new User();
        u.setBirthDate(new Date());
        u.setUsername("nkhoang.it");
        u.setPassword(passwordEncoder.encodePassword("password", null));
        User savedUser = userRepository.save(u);
        Assert.assertNotNull(savedUser);
    }

    @Test
    public void testLoadUserByName() {
        User u = (User) userRepository.loadUserByUsername("user-01");
        Assert.assertNotNull(u);
        Assert.assertEquals("user01@user.com", u.getEmail());
    }

    @Before
    public void init() {
        User u1 = new User();
        u1.setUsername("user-01");
        u1.setPassword(passwordEncoder.encodePassword("password" + u1.getUsername(), null));
        u1.setEmail("user01@user.com");

        User u2 = new User();
        u2.setUsername("user-02");
        u2.setPassword(passwordEncoder.encodePassword("password" + u1.getUsername(), null));
        u2.setEmail("user02@user.com");

        userRepository.save(u1);
    }
}
