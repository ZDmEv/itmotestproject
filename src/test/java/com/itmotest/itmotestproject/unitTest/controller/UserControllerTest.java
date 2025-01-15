package com.itmotest.itmotestproject.unitTest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itmotest.itmotestproject.model.User;
import com.itmotest.itmotestproject.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();
    @MockitoBean
    private UserService userService;

    @Test
    void correctTest() throws Exception {
        doReturn(0L).when(userService).add(any());
        this.mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new User("biba", "builder", 10))))
                .andExpect(status().isOk());
    }

    @Test
    void nameWithSymbolsTest() throws Exception {
        doReturn(0L).when(userService).add(any());
        var result = this.mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new User("biba%", "builder", 10))))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("В имени присутствуют символы или цифры"));
    }

    @Test
    void nameWithNumbersTest() throws Exception {
        doReturn(0L).when(userService).add(any());
        var result = this.mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new User("biba2007", "builder", 10))))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("В имени присутствуют символы или цифры"));
    }

    @Test
    void withoutNameTest() throws Exception {
        doReturn(0L).when(userService).add(any());
        var result = this.mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new User(null, "builder", 10))))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Поле name обязательно к заполнению"));
    }
}
