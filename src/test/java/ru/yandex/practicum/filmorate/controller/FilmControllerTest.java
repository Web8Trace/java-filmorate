package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FilmControllerTest {

    @Autowired
    FilmController filmController;

    @Test
    void postFilm() {
        Film film =new Film();
        film.setName("film");
        film.setDescription("very interesting film");
        film.setReleaseDate(LocalDate.parse("1988-02-02"));
        film.setDuration(120);
        Film noCorrect=new Film();
        filmController.postFilm(film);
        filmController.postFilm(noCorrect);
    }

    @Test
    void getFilms() {
        Film film =new Film();
        film.setName("film");
        film.setDescription("very interesting film");
        film.setReleaseDate(LocalDate.parse("1988-02-02"));
        film.setDuration(120);
        filmController.postFilm(film);
        List<Film>films=new ArrayList<>();
        films.add(film);
        assertEquals(films, filmController.getFilms());
    }


    @Test
    void putFilm() {
        Film film =new Film();
        film.setName("film");
        film.setDescription("very interesting film");
        film.setReleaseDate(LocalDate.parse("1988-02-02"));
        film.setDuration(120);
        filmController.postFilm(film);
        film.setName("new Film");
        filmController.putFilm(0L, film);
        List<Film>films=new ArrayList<>();
        films.add(film);
        assertEquals(films, filmController.getFilms());
    }
}