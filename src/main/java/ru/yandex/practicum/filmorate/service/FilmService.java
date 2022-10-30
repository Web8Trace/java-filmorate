package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.*;

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

    public Film like(Long filmId, Long userId) throws NotFoundException {
        if(filmStorage.findById(filmId)==null){
            throw new NotFoundException();
        }
        Film film=filmStorage.findById(filmId);
        film.getLikes().add(userId);
        return film;
    }

    public Film disLike(Long filmId, Long userId) throws NotFoundException {
        Film film=filmStorage.findById(filmId);
        film.getLikes().remove(userId);
        return film;
    }

    public List<Film> liders(int count){
        Collection<Film> filmCollections=filmStorage.findAll();
        filmCollections.stream().sorted();
        List<Film> liders=new ArrayList<>();
        for(int i=filmCollections.size()-1; i>0; i--){
            if(liders.size()>=count){
                break;
            }

            liders.add((Film) filmCollections.toArray()[i]);
        }
        return liders;
    }
}
