package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ErrorResponse;
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
        return List.of(filmService.findAll().toArray(new Film[0]));
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Long id) throws NotFoundException {
        return filmService.findById(id);
    }

    @PostMapping
    public Film postFilm(@RequestBody Film film) throws ValidationException {
       return filmService.create(film);
    }

    @PutMapping
    public Film putFilm(@RequestBody Film film) throws ValidationException, NotFoundException {
        return filmService.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film setLikeToFilm(@PathVariable Long id, @PathVariable Long userId) throws NotFoundException {
        return filmService.setLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLikeFilm(@PathVariable Long id, @PathVariable Long userId) throws NotFoundException {
        return filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> bestOfFilms(@RequestParam(required = false, defaultValue= "10")  Integer count){
        return filmService.getBestFilms(count);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleError(final NotFoundException e){
        return new ErrorResponse(
                e.getMessage()
        );
    }
}