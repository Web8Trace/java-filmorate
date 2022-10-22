package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film create(Film film) throws ValidationException;
    Film update(Film film) throws ValidationException;
    Film findById(Long id);
    Collection<Film>findAll();
}
