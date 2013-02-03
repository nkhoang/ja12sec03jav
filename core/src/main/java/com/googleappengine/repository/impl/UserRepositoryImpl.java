package com.googleappengine.repository.impl;

import com.googleappengine.dao.UserDao;
import com.googleappengine.dao.impl.BaseDaoImpl;
import com.googleappengine.model.QUser;
import com.googleappengine.model.User;
import org.jasypt.spring.security3.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository(value = "userDao")
public class UserRepositoryImpl extends BaseDaoImpl implements UserDao {
    private static final Logger LOG = LoggerFactory.getLogger(UserRepositoryImpl.class);
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * load user by username.
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QUser user = QUser.user;
        User foundUser = getQuery().from(user).where(user.username.eq(username)).uniqueResult(user);
        if (foundUser == null) {
            throw new UsernameNotFoundException(String.format("Could not find username: %s", username));
        }
        return foundUser;
    }
}
