package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class FilmService {
    FilmStorage filmStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public FilmStorage getFilmStorage() {
        return filmStorage;
    }

    public void setFilmStorage(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film like(Long filmId, Long userId){
        Film film=filmStorage.findById(filmId);
        film.getLikes().add(userId);
        return film;
    }

    public Film disLike(Long filmId, Long userId){
        Film film=filmStorage.findById(filmId);
        film.getLikes().remove(userId);
        return film;
    }

    public Set<Film> liders(int count){
        Collection<Film> filmCollections=filmStorage.findAll();
        filmCollections.stream().sorted();
        Set<Film>liders=new HashSet<>();
        for(Film film:filmCollections){
            if(liders.size()>count){
                break;
            }
            liders.add(film);
        }
        return liders;

    }
}
