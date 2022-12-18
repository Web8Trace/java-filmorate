package ru.yandex.practicum.filmorate.exception;

public class FilmAlreadyExistsException extends AlreadyExistsException {
    public FilmAlreadyExistsException(String message, String parm, String value) {
        super(message, parm, value);
    }
}
