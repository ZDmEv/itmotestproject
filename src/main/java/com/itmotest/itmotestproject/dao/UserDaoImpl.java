package com.itmotest.itmotestproject.dao;

import com.itmotest.itmotestproject.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserDaoImpl implements UserDao {
    private final AtomicInteger autoId = new AtomicInteger(0);
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public List<User> allUsers() {
        return new ArrayList<User>(users.values());
    }

    @Override
    public long add(User user) {
        user.setId(autoId.getAndIncrement());
        users.put(user.getId(), user);
        return user.getId();
    }

    @Override
    public void delete(User user) {
        users.remove(user.getId());
    }

    @Override
    public User edit(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User getById(long id) {
        return users.get(id);
    }
}
