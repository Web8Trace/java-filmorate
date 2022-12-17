package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashSet;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserControllerTest {
    private final UserController userController;

    @SneakyThrows
    @Test
    void dontCorrectValidation() {
        User user = new User(null, "user@email.com", "user", "User",
                LocalDate.parse("1975-01-01"), new HashSet<>());
        userController.addUser(user);
        Assertions.assertEquals(user, userController.findUserById(user.getId()));
    }



    @SneakyThrows
    @Test
    void postAndGetUsers()  {
        User user = new User(null, "user@email.com", "user", "User",
                LocalDate.parse("1975-01-01"), new HashSet<>());
        userController.addUser(user);
        User user2=userController.findUserById(2);
        Assertions.assertEquals(user, user2);

    }
    @SneakyThrows
    @Test
    void putUser(){
        User user = new User(null, "user@email.com", "user", "User",
                LocalDate.parse("1975-01-01"), new HashSet<>());
        userController.addUser(user);
        user= (User) userController.findAll().toArray()[0];
        User user1 = new User(null, "user1@email.com", "user1", "User1",
                LocalDate.parse("1985-01-01"), new HashSet<>());
        user1.setId(user.getId());
        userController.update(user1);
        Assertions.assertEquals(user1, userController.findUserById(user.getId()));
    }
}