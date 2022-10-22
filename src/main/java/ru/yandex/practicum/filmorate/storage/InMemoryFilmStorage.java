package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ru.yandex.practicum.filmorate.validator.Validator.validatedFilm;
@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage{
    private Map<Long, Film> films = new HashMap<>();
    private Long generatedId = 1L;

    @Override
    public Film create(Film film) throws ValidationException {
        if (validatedFilm(film)) {
            film.setId(generatedId++);
            films.put(film.getId(), film);
        } else {
            throw new ValidationException();
        }
        log.debug("Текущее число фильмов: {}", films.size());
        return film;    }

    @Override
    public Film update(Film film) throws ValidationException {
        if (validatedFilm(film)) {
            Long id = film.getId();
            if (id<0){
                log.error("id is less than zero");
                throw new ValidationException();
            }
            if (!films.containsKey(id)){
                films.put(film.getId(), film);
                log.debug("Фильм не найден. добавлен новый фильм");
            } else {
                films.put(film.getId(), film);
                log.debug("Фильм изменен под идентификатором {}", film.getId());

            }
        } else {
            throw new ValidationException();
        }
        return film;
    }

    @Override
    public Film findById(Long id) {
        if (id == null){
            return null;
        }
        return films.get(id);
    }

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }
}
