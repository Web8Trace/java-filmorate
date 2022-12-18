package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Set;

public interface GenreStorage {
    Genre findGenreById(Integer genreId) throws GenreNotFoundException;
    Collection<Genre> findAllGenre();
    Set<Genre> findGenreFilm(Integer filmId);
    void addFilmToGenre(Integer filmId,Integer genreId);
    void deleteFilmToGenre(Integer filmId, Integer genreId);
}
