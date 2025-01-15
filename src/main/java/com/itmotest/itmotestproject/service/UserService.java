package com.itmotest.itmotestproject.service;

import com.itmotest.itmotestproject.model.User;

import java.util.List;

public interface UserService {
    List<User> allUsers();
    long add(User user);
    void delete(User user);
    User edit(User updatedUser);
    User getById(long id);
}
