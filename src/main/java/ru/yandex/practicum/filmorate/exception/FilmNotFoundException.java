package ru.yandex.practicum.filmorate.exception;

public class FilmNotFoundException extends NotFoundException {
    public FilmNotFoundException(String s, String parm, String value) {
        super(s, parm, value);
    }
}
