package com.googleappengine.repository;

import com.google.appengine.api.datastore.Key;
import com.googleappengine.dao.UserDao;
import com.googleappengine.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Key>, UserDao {
}
