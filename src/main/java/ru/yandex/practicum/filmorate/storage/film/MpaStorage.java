package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

public interface MpaStorage {
    Mpa findMpaById(Integer mpaId) throws MpaNotFoundException;
    Collection<Mpa> findAllMpa();
}
