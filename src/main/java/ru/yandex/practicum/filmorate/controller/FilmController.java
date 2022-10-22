package ru.yandex.practicum.filmorate.controller;


import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.*;

import static ru.yandex.practicum.filmorate.validator.Validator.validatedFilm;

@RestController
@Slf4j
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;
    @GetMapping
    public Collection<Film> getFilms() {
        return filmService.getFilmStorage().findAll();
    }

    @PostMapping
    public Film postFilm(@RequestBody Film film) throws ValidationException {
       return filmService.getFilmStorage().create(film);
    }

    @PutMapping
    public Film putFilm(@RequestBody Film film) throws ValidationException {
        return filmService.getFilmStorage().update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film likeToFilm(@PathVariable Long id, @PathVariable Long userId){
        return filmService.like(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film disLikeFilm(@PathVariable Long id, @PathVariable Long userId){
        return filmService.disLike(id, userId);
    }

    @GetMapping("/popular?count={count}")
    public List<Film> lidersFilm(@PathVariable(required = false) Integer count){
        if (count==null){
            count=10;
        }
        return (List<Film>) filmService.liders(count);
    }

}