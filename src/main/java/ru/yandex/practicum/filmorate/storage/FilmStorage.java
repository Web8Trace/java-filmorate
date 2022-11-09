package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {
    Film create(Film film) throws ValidationException;
    Film update(Film film) throws ValidationException, NotFoundException;
    Film findById(Long id) throws NotFoundException;
    Film[] findAll();
}