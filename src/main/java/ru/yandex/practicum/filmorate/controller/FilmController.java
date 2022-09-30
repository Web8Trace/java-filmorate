package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmorate.validator.Validator.validatedFilm;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private Map<Long, Film> films = new HashMap<>();
    private Long generatedId = 0L;
    @GetMapping
    public List<Film> getFilms() {
        log.info("Текущее число фильмов: {}", films.size());
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film postFilm(@RequestBody Film film) throws ValidationException {
        if (validatedFilm(film)) {
            film.setId(generatedId++);
            films.put(film.getId(), film);
        } else {
            throw new ValidationException();
        }
        log.debug("Текущее число фильмов: {}", films.size());
        return film;
    }

    @PutMapping
    public Film putFilm(@RequestBody Film film) throws ValidationException {
        Long id = film.getId();
        if (validatedFilm(film)) {
                if (!films.containsKey(id)){
                    films.put(film.getId(), film);
                    log.debug("Фильм не найден. добавлен новый фильм");
                } else {
                    films.put(film.getId(), film);
                    log.debug("Фильм изменен под идентификатором {}", film.getId());

                }
            } else {
            throw new ValidationException();
        }
        return film;
    }
}