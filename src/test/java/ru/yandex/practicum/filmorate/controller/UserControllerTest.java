package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserControllerTest {

    @Autowired
    UserController userController;

    @Test
    void postUser() {
        User user=new User();
        user.setName("user");
        user.setBirthday(LocalDate.parse("1975-01-01"));
        user.setEmail("user@email.com");
        user.setLogin("corUser");
        User noCorrect = new User();
        userController.postUser(user);
        userController.postUser(noCorrect);
    }
    @Test
    void getUsers() {
        List<User> userList=new ArrayList<>();
        User user=new User();
        user.setName("user");
        user.setBirthday(LocalDate.parse("1975-01-01"));
        user.setEmail("user@email.com");
        user.setLogin("corUser");
        userList.add(user);
        userController.postUser(user);
        Assertions.assertEquals(userList, userController.getUsers());

    }
    @Test
    void putUser() {
        List<User> userList=new ArrayList<>();
        User user=new User();
        user.setName("user");
        user.setBirthday(LocalDate.parse("1975-01-01"));
        user.setEmail("user@email.com");
        user.setLogin("corUser");
        userController.postUser(user);
        user=new User();
        user.setBirthday(LocalDate.parse("1985-01-01"));
        user.setEmail("usere@email.com");
        user.setLogin("corUser");
        userList.add(user);
        userController.putUser(0L,user);
        Assertions.assertEquals(userList, userController.getUsers());
    }
}