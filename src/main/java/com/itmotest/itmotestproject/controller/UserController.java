package com.itmotest.itmotestproject.controller;

import com.itmotest.itmotestproject.model.User;
import com.itmotest.itmotestproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.nonNull;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/add")
    public long add(@RequestBody @Valid User user) {
        return userService.add(user);
    }

    @GetMapping(value = "/")
    public List<User> printUsers() {
        return userService.allUsers();
    }

    @PostMapping(value = "/delete/{id}")
    public void delete(@PathVariable("id") long id) {
        if (id < 0) {
            return;
        }
        var user = userService.getById(id);
        if (nonNull(user)) {
            userService.delete(user);
        }
    }
}
