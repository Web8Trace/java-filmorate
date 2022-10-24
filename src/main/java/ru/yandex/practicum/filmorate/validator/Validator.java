package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
public class Validator {
    public static boolean validatedFilm(Film film) {
        if(film.getName()==null||film.getName().isEmpty()){
            log.error("film name is empty");
            return false;
        }
        if(film.getDescription().length()>200){
            log.error("film description is so long");
            return false;
        }
        if(film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))){
            log.error("film is so old");
            return false;
        }
        if (film.getDuration()<0){
            log.error("film duration is null");
            return false;
        }
        log.info("film is valid");
        return true;
    }

    public static boolean validatedUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            log.error("user email is empty");
            return false;
        }
        if (!user.getEmail().contains("@")) {
            log.error("user email dont contain symbol @");
            return false;
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()){
            log.error("user login is empty");
            return false;
        }
        if (user.getLogin().contains(" ")) {
            log.error("user login contain whitespace");
            return false;
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("users birthday dont correct");
            return false;
        }

        log.info("user is valid");
        return true;
    }
}