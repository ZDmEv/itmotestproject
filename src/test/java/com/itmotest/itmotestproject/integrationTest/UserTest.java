package com.itmotest.itmotestproject.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itmotest.itmotestproject.dao.UserDao;
import com.itmotest.itmotestproject.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDao userDao;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        userDao.allUsers().forEach(
                user -> userDao.delete(user)
        );
    }

    @Test
    void createUserTest() throws Exception {
        var result = this.mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new User("biba", "builder", 10))))
                .andExpect(status().isOk())
                .andReturn();
        var id = Long.parseLong(result.getResponse().getContentAsString());
        var user = userDao.getById(id);
        assertNotNull(user);
        assertAll(
                () -> assertEquals("biba", user.getName()),
                () -> assertEquals("builder", user.getJob()),
                () -> assertEquals(10, user.getAge())
        );
    }

    @Test
    void printUsersTest() throws Exception {
        List.of(
                new User("biba", "builder", 70),
                new User("boba", "driver", 80),
                new User("sanya", "unemployed", 21)
        ).forEach(
                user ->
                {
                    try {
                        this.mockMvc.perform(post("/add")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(mapper.writeValueAsString(user)))
                                .andExpect(status().isOk());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        var result = this.mockMvc.perform(get("/")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var users = mapper.readValue(result, User[].class);
        assertEquals(3, users.length);
    }

    @Test
    void deleteUserTest() throws Exception {
        var createMvcResult = this.mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new User("biba", "builder", 10))))
                .andExpect(status().isOk())
                .andReturn();
        var id = Long.parseLong(createMvcResult.getResponse().getContentAsString());
        assertEquals(1, userDao.allUsers().size());
        this.mockMvc.perform(
                        post("/delete/{id}", String.valueOf(id))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();
        assertEquals(0, userDao.allUsers().size());
    }

    @Test
    void deleteNotExistingUserTest() throws Exception {
        assertEquals(0, userDao.allUsers().size());
        this.mockMvc.perform(
                        post("/delete/99")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();
        assertEquals(0, userDao.allUsers().size());
    }

    @Test
    void deleteWithNegativeIdTest() throws Exception {
        assertEquals(0, userDao.allUsers().size());
        this.mockMvc.perform(
                        post("/delete/99")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();
        assertEquals(0, userDao.allUsers().size());
    }
}
