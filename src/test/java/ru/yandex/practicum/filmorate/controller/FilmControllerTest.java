package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.controller.Validators.FilmReleaseDateValidator;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.impl.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.film.impl.MpaDbStorage;



import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.mock.mockito.MockReset.before;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmControllerTest {
    private final FilmDbStorage filmDbStorage;
    private final MpaDbStorage mpaStorage;

    @Test
    void testGetFilms() throws FilmNotFoundException, MpaNotFoundException, FilmAlreadyExistsException {
        Film film = new Film("Film1","ddd",LocalDate.parse("2020-12-12"),
                120,15, mpaStorage.findMpaById(1));
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

    @Test
    void testDontCorrectDurarion() throws FilmAlreadyExistsException {
        Film film = new Film("Film1","ddd",LocalDate.parse("2020-12-12"),
                -200,15, mpaStorage.findMpaById(1));

        filmDbStorage.addFilm(film);
        assertEquals(true, film.getDuration() < 0);
        assertThrows(FilmAlreadyExistsException.class, () -> filmDbStorage.addFilm(film));
    }

    @Test
    void testDontCorrect() throws FilmAlreadyExistsException {
        Film film = new Film("Film1","ddd",LocalDate.parse("1700-12-12"),
                200,15, mpaStorage.findMpaById(1));

        filmDbStorage.addFilm(film);
        assertEquals(true, LocalDate.parse("1895-12-12").isBefore(LocalDate.now()));
        assertThrows(FilmAlreadyExistsException.class, () -> filmDbStorage.addFilm(film));
    }


}