package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmorate.validator.Validator.validatedFilm;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private Map<Long, Film> films =new HashMap<>();
    public static Long staticId=0L;
    @GetMapping
    List<Film>getFilms(){
        log.info("Текущее число пользователей: {}", films.size());
        return  new ArrayList<>(films.values());
    }

    @PostMapping
    Film postFilm(@RequestBody Film film){
        if(validatedFilm(film)){
            film.setId(staticId++);
            films.put(film.getId(), film);
        } else {
            throw new ValidationException();
        }
        log.debug("Текущее число фильмов: {}", films.size());
        return film;
    }

    @PutMapping
    Film putFilm(@PathVariable Long id,
                 @RequestBody Film film){
        if(validatedFilm(film)){
                if (films.get(id)==null){
                    films.put(film.getId(), film);
                    log.debug("Фильм не найден. добавлен новый фильм");
                }else {
                    films.put(film.getId(), film);
                    log.debug("Фильм изменен под идентификатором {}", film.getId());

                }
            }
        return film;
    }
}