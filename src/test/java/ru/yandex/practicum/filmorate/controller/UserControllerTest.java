package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.impl.UserDbStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserControllerTest {

    private final UserDbStorage userDbStorage;
    @Test
    public void testFindUserById() throws UserAlreadyExistsException {

        User user = new User(null, "user@email.com", "user", "User",
                LocalDate.parse("1975-01-01"), new HashSet<>());
        User friend = new User(null, "friend@email.com", "friend", "friendName",
                LocalDate.parse("1985-01-01"), new HashSet<>());
        userDbStorage.add(user);
        userDbStorage.add(friend);
        System.out.println(userDbStorage.findAll());
        user = userDbStorage.findById(1);
        assertEquals(user.getName(), "User");
        assertEquals(user.getEmail(), "user@email.com");
        assertEquals(user.getBirthday(), LocalDate.parse("1975-01-01"));
    }

    @Test
    public void testFindUserDontCorrectId() {

        assertThrows(UserNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                User user = userDbStorage.findById(999);
            }
        });
    }

    @Test
    void testFindAll() throws UserAlreadyExistsException {
        User user = new User(null, "user@email.com", "user", "User",
                LocalDate.parse("1975-01-01"), new HashSet<>());
        User friend = new User(null, "friend@email.com", "friend", "friendName",
                LocalDate.parse("1985-01-01"), new HashSet<>());
        userDbStorage.add(user);
        userDbStorage.add(friend);
        Collection<User> user1 = userDbStorage.findAll();
        assertEquals(2, user1.size());
        assertTrue(user1.stream().anyMatch(r -> r.getName().equals("User")));
    }

    @Test
    @DirtiesContext
    void testPutUser() throws UserAlreadyExistsException {
        User user = new User(null, "user@email.com", "user", "User",
                LocalDate.parse("1975-01-01"), new HashSet<>());
        User friend = new User(null, "friend@email.com", "friend", "friendName",
                LocalDate.parse("1985-01-01"), new HashSet<>());
        userDbStorage.add(user);
        userDbStorage.add(friend);
        User user1 = new User(null, "email@mail.ru",
                "User1", null, LocalDate.parse("1985-09-09"), new HashSet<>());
        User putUser = userDbStorage.add(user1);

        assertEquals(3, putUser.getId());
        assertTrue(userDbStorage.findAll().stream().anyMatch(r -> r.getEmail().equals("email@mail.ru")));
        assertEquals(3, userDbStorage.findAll().size());
        assertEquals(user1.getLogin(), putUser.getLogin());
    }

    @Test
    @DirtiesContext
    void testUpdateUserNormal() throws UserNotFoundException, UserAlreadyExistsException {
        User user = new User(1, "updated@mail.ru",
                "updatedUser", "updateName",
                LocalDate.of(1974, 1, 1), null);
        userDbStorage.update(user);
        assertEquals("updateName", userDbStorage.findById(1).getName());
        assertEquals(2, userDbStorage.findAll().size());
    }

    @Test
    @DirtiesContext
    void testUpdateNotFoundUser() {
        assertThrows(UserNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                User user = new User(47, "updated@mail.ru",
                        "updatedUser", "updateName",
                        LocalDate.of(1974, 1, 1), null);
                userDbStorage.update(user);
            }
        });
    }

    @Test
    @DirtiesContext
    void testDontCorrectYeareUser() {
        assertThrows(UserNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                User user = new User(3, "updated@mail.ru",
                        "updatedUser", "updateName",
                        LocalDate.of(3300, 1, 1), null);
                userDbStorage.update(user);
            }
        });
    }

    @Test
    @DirtiesContext
    void testDontCorrectNameUser() {
        assertThrows(UserNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                User user = new User(3, "updated@mail.ru",
                        "updatedUser", "",
                        LocalDate.of(1974, 1, 1), null);
                userDbStorage.update(user);
            }
        });
    }
}