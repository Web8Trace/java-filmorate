package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserControllerTest {

    @Test
    void dontCorrectValidation() {
        UserController userController=new UserController(new UserService(new InMemoryUserStorage()));
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
    void postAndGetUsers() throws ValidationException, NotFoundException {
        UserController userController=new UserController(new UserService(new InMemoryUserStorage()));
        User user=new User();
        user.setName("user");
        user.setBirthday(LocalDate.parse("1975-01-01"));
        user.setEmail("user@email.com");
        user.setLogin("corUser");
        userController.postUser(user);
        Assertions.assertEquals(user, userController.getUser(user.getId()));

    }
    @Test
    void putUser() throws ValidationException, NotFoundException {
        UserController userController=new UserController(new UserService(new InMemoryUserStorage()));
        User user=new User();
        user.setName("user");
        user.setBirthday(LocalDate.parse("1975-01-01"));
        user.setEmail("user@email.com");
        user.setLogin("corUser");
        userController.postUser(user);
        User user1=new User();
        user1.setBirthday(LocalDate.parse("1985-01-01"));
        user1.setEmail("usere@email.com");
        user1.setLogin("corUser");
        user1.setId(1L);
        userController.putUser(user1);
        Assertions.assertEquals(user1, userController.getUser(user1.getId()));
    }
}