package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
   Film findById(int id) throws FilmNotFoundException;
   Film addFilm(Film film) throws FilmAlreadyExistsException;
   Film update(Film film) throws FilmNotFoundException, MpaNotFoundException;
   void delete(int id) throws FilmNotFoundException;
   Collection<Film> findAll();
   Collection<Film> findPopularFilm(Integer count);


}

