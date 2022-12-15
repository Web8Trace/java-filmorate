package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.LikeAlreadyException;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.sql.SQLException;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) throws FilmAlreadyException {
        return filmService.addFilm(film);
    }

    @PutMapping("/films")
    public Film update(@Valid @RequestBody Film film) throws FilmNotFoundException, MpaNotFoundException {
        return filmService.updateFilm(film);
    }

    @GetMapping("/films")
    public Collection<Film> findAll() {
        return filmService.findAll();
    }

    @GetMapping("/films/{id}")
    public Film findById(@PathVariable @Min(1) int id) throws FilmNotFoundException, SQLException, MpaNotFoundException {
        return filmService.findById(id);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) throws FilmNotFoundException, SQLException, MpaNotFoundException, LikeAlreadyException {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable @Min(1) int id, @PathVariable @Min(1) int userId) throws FilmNotFoundException, SQLException, MpaNotFoundException {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/films/popular")
    public Collection<Film> findPopularFilm(@RequestParam(defaultValue = "10", value = "count") int count) {
        return filmService.findPopularFilm(count);
    }
}
