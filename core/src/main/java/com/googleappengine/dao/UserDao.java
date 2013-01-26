package com.googleappengine.dao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * User dao.
 *
 */

public interface UserDao extends UserDetailsService {
    /**
     * Retrieves the password in DB for a user
     *
     * @param username the user's username
     * @return the password in DB, if the user's already persisted
     */
    String getUserPassword(String username);

    boolean checkEmail(String email);

    boolean checkUsername(String username);
}
