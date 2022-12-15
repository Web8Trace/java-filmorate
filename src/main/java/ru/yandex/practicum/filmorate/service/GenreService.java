package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.GenreStorage;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenreService {
    private final GenreStorage genreStorage;

    public Collection<Genre> getAll() {
        log.info("Передан список Всех Жанров");
        return genreStorage.findAllGenre();
    }

    public Genre getById(int id) throws GenreNotFoundException {
        log.info("Запрошенж жанр Id = {}",id);
        return genreStorage.findGenreById(id);
    }
}
