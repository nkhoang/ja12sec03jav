package com.googleappengine.dao.impl;

import com.googleappengine.dao.UserDao;
import com.googleappengine.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    @PersistenceContext
    protected EntityManager entityManager;

    public String getClassName() {
        return "User";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getUserPassword(String username) {
        String password = null;
        try {
            Query query = entityManager.createQuery("Select u.password from " + User.class.getName()
                    + " u where u.username = :username");
            query.setParameter("username", username.trim());

            List<String> result = query.getResultList();
            if (result != null && result.size() > 0) {
                password = result.get(0);
            }
        } catch (Exception ex) {
            LOGGER.error("Error", ex);
        }

        return password;
    }


    public boolean checkUsername(String username) {
        Query query = entityManager
                .createQuery("select from " + User.class.getName() + " u where u.username=:username");
        query.setParameter("username", username.trim());

        List<User> result = query.getResultList();
        if (result != null && result.size() > 0) {
            return true;
        }
        return false;
    }


    public boolean checkEmail(String email) {
        Query query = entityManager
                .createQuery("select from " + User.class.getName() + " u where u.email=:email");
        query.setParameter("email", email.trim());

        List<User> result = query.getResultList();
        if (result != null && result.size() > 0) {
            return true;
        }

        return false;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Query query = entityManager
                .createQuery("select from " + User.class.getName() + " u where u.username=:username");
        query.setParameter("username", username.trim());

        List<User> result = query.getResultList();
        if (result != null && result.size() > 0) {
            return result.get(0);
        } else {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        }
    }
}
