package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

import static ru.yandex.practicum.filmorate.validator.Validator.validatedFilm;
@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage{
    private Map<Long, Film> films = new HashMap<>();
    private Long generatedId = 1L;

    @Override
    public Film create(Film film) throws ValidationException {
        if (!validatedFilm(film)) {
            throw new ValidationException();
        }
        film.setId(generatedId++);
        films.put(film.getId(), film);
        log.debug("Текущее число фильмов: {}", films.size());
        return film;
    }

    @Override
    public Film update(Film film) throws ValidationException, NotFoundException {
        if (!validatedFilm(film)) {
            throw new ValidationException();
        }
        Long id = film.getId();
        if (id < 0){
            log.error("id is less than zero");
            throw new ValidationException();
        }
        if (!films.containsKey(id)){
            log.debug("Фильм не найден. не добавлен новый фильм");
            throw new NotFoundException();
        } else {
            films.put(film.getId(), film);
            log.debug("Фильм изменен под идентификатором {}", film.getId());

        }
        return film;
    }

    @Override
    public Film findById(Long id) throws NotFoundException {
        if ((id == null)||(films.get(id) == null)){
            throw new NotFoundException();
        }
        return films.get(id);
    }

    @Override
    public Collection <Film> findAll() {
        return films.values();
    }
}
