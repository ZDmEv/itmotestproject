package com.itmotest.itmotestproject.service;

import com.itmotest.itmotestproject.dao.UserDao;
import com.itmotest.itmotestproject.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public List<User> allUsers() {
        return userDao.allUsers();
    }

    @Override
    public long add(User user) {
        return userDao.add(user);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public User edit(User user) {
        return userDao.edit(user);
    }

    @Override
    public User getById(long id) {
        return userDao.getById(id);
    }
}
