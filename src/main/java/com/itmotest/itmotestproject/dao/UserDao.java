package com.itmotest.itmotestproject.dao;

import com.itmotest.itmotestproject.model.User;

import java.util.List;

public interface UserDao {
    List<User> allUsers();
    long add(User user);
    void delete(User user);
    User edit(User user);
    User getById(long id);
}
