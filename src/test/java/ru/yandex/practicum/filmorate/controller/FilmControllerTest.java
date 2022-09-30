package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FilmControllerTest {


    @Test
    void dontCorrectValidation(){
        FilmController filmController=new FilmController();
        Film film =new Film();
        film.setName("film");
        film.setDescription("very interesting film");
        film.setReleaseDate(LocalDate.parse("1788-02-02"));
        film.setDuration(120);
        assertThrows(ValidationException.class, ()->{
            filmController.postFilm(film);
        });
        film.setReleaseDate(LocalDate.parse("1988-02-02"));
        film.setName("");
        assertThrows(ValidationException.class, ()->{
            filmController.postFilm(film);
        });
        film.setName("film");
        film.setDuration(-120);
        assertThrows(ValidationException.class, ()->{
            filmController.postFilm(film);
        });
    }


    @Test
    void postAndGetFilms() throws ValidationException {
        FilmController filmController=new FilmController();
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
    void putFilm() throws ValidationException {
        FilmController filmController=new FilmController();
        Film film =new Film();
        film.setName("film");
        film.setDescription("very interesting film");
        film.setReleaseDate(LocalDate.parse("1988-02-02"));
        film.setDuration(120);
        filmController.postFilm(film);
        film.setName("new Film");
        filmController.putFilm(film);
        List<Film>films=new ArrayList<>();
        films.add(film);
        assertEquals(films, filmController.getFilms());
    }
}