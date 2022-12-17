package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.event.annotation.PrepareTestInstance;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.impl.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.film.impl.MpaStorageImpl;
import ru.yandex.practicum.filmorate.storage.user.impl.UserDbStorage;

import javax.persistence.PrePersist;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmorateApplicationTest {
    private final UserDbStorage userDbStorage;
    private final FilmDbStorage filmDbStorage;
    private final MpaStorageImpl mpaStorage;
    User user;
    User friend;

    @Test
    public void testFindUserById() throws UserAlreadyException {
        user = new User(null, "user@email.com", "user", "User",
                LocalDate.parse("1975-01-01"), new HashSet<>());
        friend = new User(null, "friend@email.com", "friend", "friendName",
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
    void testFindAll() throws UserAlreadyException {
        user = new User(null, "user@email.com", "user", "User",
                LocalDate.parse("1975-01-01"), new HashSet<>());
        friend = new User(null, "friend@email.com", "friend", "friendName",
                LocalDate.parse("1985-01-01"), new HashSet<>());
        userDbStorage.add(user);
        userDbStorage.add(friend);
        Collection<User> user = userDbStorage.findAll();
        assertEquals(2, user.size());
        assertTrue(user.stream().anyMatch(r -> r.getName().equals("User")));
    }

    @Test
    @DirtiesContext
    void testPutUser() throws UserAlreadyException {
        user = new User(null, "user@email.com", "user", "User",
                LocalDate.parse("1975-01-01"), new HashSet<>());
        friend = new User(null, "friend@email.com", "friend", "friendName",
                LocalDate.parse("1985-01-01"), new HashSet<>());
        userDbStorage.add(user);
        userDbStorage.add(friend);
        User user = new User(null, "email@mail.ru",
                "User1", null, LocalDate.parse("1985-09-09"), new HashSet<>());
        User putUser = userDbStorage.add(user);

        assertEquals(3, putUser.getId());
        assertTrue(userDbStorage.findAll().stream().anyMatch(r -> r.getEmail().equals("email@mail.ru")));
        assertEquals(3, userDbStorage.findAll().size());
        assertEquals(user.getLogin(), putUser.getLogin());
    }

    @Test
    @DirtiesContext
    void testUpdateUserNormal() throws UserNotFoundException, UserAlreadyException {
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
    void testGetFilms() throws FilmNotFoundException, MpaNotFoundException, FilmAlreadyException {
        Film film=new Film("Film1","ddd",LocalDate.parse("2020-12-12"),120,15, mpaStorage.findMpaById(1));
       filmDbStorage.addFilm(film);
        assertEquals(1, filmDbStorage.findAll().size());
        Film film1 = filmDbStorage.findById(1);
        assertEquals("Film1", film1.getName());
        Mpa mpa = mpaStorage.findMpaById(1);
        assertEquals(mpa, film1.getMpa());
        assertThrows(FilmNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                filmDbStorage.findById(78);
            }
        });
    }
}
