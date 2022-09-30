package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserControllerTest {

    @Test
    void dontCorrectValidation() {
        UserController userController=new UserController();
        User user=new User();
        user.setName("user");
        user.setBirthday(LocalDate.parse("1975-01-01"));
        user.setEmail("useremail.com");
        user.setLogin("corUser");
        assertThrows(ValidationException.class, ()->{
            userController.postUser(user);
        });
        user.setEmail("");
        assertThrows(ValidationException.class, ()->{
            userController.postUser(user);
        });
        user.setEmail("user@email.com");
        user.setLogin("");
        assertThrows(ValidationException.class, ()->{
            userController.postUser(user);
        });
        user.setLogin("cor User");
        assertThrows(ValidationException.class, ()->{
            userController.postUser(user);
        });
    }



        @Test
    void postAndGetUsers() throws ValidationException {
        UserController userController=new UserController();
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
    void putUser() throws ValidationException {
        UserController userController=new UserController();
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
        user.setId(0L);
        userList.add(user);
        userController.putUser(user);
        Assertions.assertEquals(userList, userController.getUsers());
    }
}