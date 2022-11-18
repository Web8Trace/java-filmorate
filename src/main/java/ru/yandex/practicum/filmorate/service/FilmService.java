package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.*;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }
    
    

    public Film setLike(Long filmId, Long userId) throws NotFoundException {
        if(filmStorage.findById(filmId) == null){
            throw new NotFoundException();
        }
        Film film = filmStorage.findById(filmId);
        film.getLikes().add(userId);
        return film;
    }

    public Film removeLike(Long filmId, Long userId) throws NotFoundException {
        if(filmStorage.findById(filmId) == null){
            throw new NotFoundException();
        }
        Film film = filmStorage.findById(filmId);
        if(!film.getLikes().contains(userId)){
            throw new NotFoundException();
        }
        film.getLikes().remove(userId);
        return film;
    }

    public List<Film> getBestFilms(int count){
        Film[] films = filmStorage.findAll().toArray(new Film[0]);
        Arrays.sort(films);
        if(count < films.length) {
            List<Film> sortedFilms = new ArrayList<>();
            for (int i = films.length - count; i < films.length; i++){
                sortedFilms.add(films[i]);
            }
            return sortedFilms;
        }
        return List.of(films);
    }

   public Collection <Film> findAll(){
        return filmStorage.findAll();
   }

   public Film findById(Long id) throws NotFoundException{
        return filmStorage.findById(id);
   }

   public Film create(Film film) throws ValidationException {
        return filmStorage.create(film);
   }

   public Film update(Film film) throws ValidationException, NotFoundException {
        return filmStorage.update(film);
   }
}