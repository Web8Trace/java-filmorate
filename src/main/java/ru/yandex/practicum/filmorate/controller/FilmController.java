package ru.yandex.practicum.filmorate.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.*;


@RestController
@Slf4j
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;
    @GetMapping
    public List<Film> getFilms() {
        return List.of(filmService.getFilmStorage().findAll());
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Long id) throws NotFoundException {
        return filmService.getFilmStorage().findById(id);
    }

    @PostMapping
    public Film postFilm(@RequestBody Film film) throws ValidationException {
       return filmService.getFilmStorage().create(film);
    }

    @PutMapping
    public Film putFilm(@RequestBody Film film) throws ValidationException, NotFoundException {
        return filmService.getFilmStorage().update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film likeToFilm(@PathVariable Long id, @PathVariable Long userId) throws NotFoundException {
        return filmService.like(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film disLikeFilm(@PathVariable Long id, @PathVariable Long userId) throws NotFoundException {
        return filmService.disLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> lidersFilm(@RequestParam(required = false) Integer count){
        if (count==null){
            count=10;
        }
        return filmService.liders(count);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public void handleError(final NotFoundException e){
    }

}