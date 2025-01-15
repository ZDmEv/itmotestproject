package com.itmotest.itmotestproject.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class User {

    public User(String name, String job, int age) {
        this.name = name;
        this.job = job;
        this.age = age;
    }

    public User(long id, String name, String job, int age) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.age = age;
    }

    private long id;
    @NotNull(message = "Поле name обязательно к заполнению")
    @Size(min = 2, max = 50, message = "Имя должно содержать от 2 до 50 символов")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "В имени присутствуют символы или цифры")
    private String name;
    @Min(value = 0, message = "Возраст не должен быть отрицательным")
    @Max(value = 120,  message = "Возраст не должен быть больше чем 120")
    private int age;
    @Size(max = 50, message = "Наименование профессии должно содержать от до 50 символов")
    private String job;
}
