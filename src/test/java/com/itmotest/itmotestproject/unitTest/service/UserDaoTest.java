package com.itmotest.itmotestproject.unitTest.service;

import com.itmotest.itmotestproject.dao.UserDaoImpl;
import com.itmotest.itmotestproject.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @Test
    void correctAddTest() {
        var userDao = new UserDaoImpl();
        assertDoesNotThrow(() -> userDao.add(new User("Biba", "driver", 60)));
        var users = userDao.allUsers();
        assertEquals(1, users.size());
        var user = users.get(0);
        assertAll(
                () -> assertEquals("Biba", user.getName()),
                () -> assertEquals("driver", user.getJob()),
                () -> assertEquals(60, user.getAge())
        );
    }

    @Test
    void correctDeleteTest() {
        var userDao = new UserDaoImpl();
        assertDoesNotThrow(() -> userDao.add(new User("Biba", "driver", 60)));
        var users = userDao.allUsers();
        assertEquals(1, users.size());
        assertDoesNotThrow(() -> userDao.delete(users.get(0)));
        assertEquals(0, userDao.allUsers().size());
    }

    @Test
    void correctEditTest() {
        var userDao = new UserDaoImpl();
        assertDoesNotThrow(() -> userDao.add(new User("Biba", "driver", 60)));
        var users = userDao.allUsers();
        assertEquals(1, users.size());
        var user = users.get(0);
        assertAll(
                () -> assertEquals("Biba", user.getName()),
                () -> assertEquals("driver", user.getJob()),
                () -> assertEquals(60, user.getAge())
        );
        user.setJob("builder");
        assertDoesNotThrow(() -> userDao.edit(user));
        users = userDao.allUsers();
        assertEquals(1, users.size());
        assertEquals("builder", users.get(0).getJob());
    }
}
