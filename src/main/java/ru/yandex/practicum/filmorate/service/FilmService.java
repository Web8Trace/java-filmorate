package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.LikesStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final LikesStorage likesStorage;

    public void addLike(int id, int userId) throws FilmNotFoundException, MpaNotFoundException {
        Film film = filmStorage.findById(id);
        if (film.getLikeSet() != null) {
            film.getLikeSet().add(userId);
            film.setRate(film.getRate()+1);
        } else {
            Set<Integer> likeSet = new HashSet<>(id);
        }
        likesStorage.addLike(userId, id);
        filmStorage.update(film);
        log.info("Пользователь id {} лайкнул id {}", userId, id);
    }

    public void deleteLike(int id, int userId) throws FilmNotFoundException, MpaNotFoundException {
        Film film = filmStorage.findById(id);
        if (film.getLikeSet() != null && film.getLikeSet().contains(userId)) {
            film.getLikeSet().remove(userId);
            likesStorage.deleteLike(userId, id);
            film.setRate(film.getRate()-1);
            filmStorage.update(film);
            log.info("Пользователь id {} удалил лайк фильму id {}", userId, id);
        } else {
            throw new FilmNotFoundException("Лайк не найден", "Id", String.valueOf(id));
        }
    }

    public Collection<Film> findPopularFilm(int count) {
        log.info("Передан список из {} популярных фильмов", count);
        return filmStorage.findPopularFilm(count);
    }

    public Film addFilm(Film film) throws FilmAlreadyException {
        filmStorage.addFilm(film);
        return film;
    }

    public Film updateFilm(Film film) throws FilmNotFoundException, MpaNotFoundException {
        return filmStorage.update(film);
    }


    public Collection<Film> findAll() {
        log.info("Передан список всех фильмов");
        return filmStorage.findAll();
    }

    public Film findById(int id) throws FilmNotFoundException, MpaNotFoundException {
        return filmStorage.findById(id);
    }
}
