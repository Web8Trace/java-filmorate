package ru.yandex.practicum.filmorate.storage.film.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@Qualifier("inMemoryFilmStorage")
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    final Map<Integer, Film> filmMap = new HashMap<>();
    int id = 1;

    @Override
    public Film findById(int id) throws FilmNotFoundException {
        if (filmMap.containsKey(id)) {
            log.info("Передан  фильм id {}", id);
            return filmMap.get(id);
        } else {
            log.info("Фильм не найден. id {}", id);
            throw new FilmNotFoundException("Фильм не найден.", "id", String.valueOf(id));
        }

    }

    @Override
    public Film addFilm(Film film) throws FilmAlreadyExistsException {
        if (film.getId() == null) {
            film.setId(id);
            filmMap.put(film.getId(), film);
            id++;
            log.info("Добавлен фильм: {} присвоенно id {}", film.getName(), id);
            return film;
        } else if (!filmMap.containsKey(film.getId())) {
            filmMap.put(film.getId(), film);
            log.info("Добавлен фильм: {}", film.getName());
            return film;
        } else {
            throw new FilmAlreadyExistsException("Фильм уже существует", "id", String.valueOf(film.getId()));
        }

    }

    @Override
    public Film update(Film film) throws FilmNotFoundException {
        if (film.getId() != null && filmMap.containsKey(film.getId())) {
            filmMap.put(film.getId(), film);
            log.info("Обновлен фильм: {}", film.getName());
        } else {
            log.info("Фильм не найден. id {}", id);
            throw new FilmNotFoundException("Фильм не найден.", "id", String.valueOf(film.getId()));
        }
        return film;
    }

    @Override
    public void delete(int id) throws FilmNotFoundException {
        if (filmMap.containsKey(id)) {
            filmMap.remove(id);
            log.info("Обновлен удален: {}", id);
        } else {
            log.info("Фильм не найден. id {}", id);
            throw new FilmNotFoundException("Фильм не найден.", "id", String.valueOf(id));
        }

    }

    @Override
    public Collection<Film> findAll() {
        return Collections.unmodifiableCollection(filmMap.values());
    }

    @Override
    public Collection<Film> findPopularFilm(Integer count) {
        return null;
    }
}
