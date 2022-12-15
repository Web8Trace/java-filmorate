package ru.yandex.practicum.filmorate.exception;

public class FilmAlreadyException extends AlreadyException {
    public FilmAlreadyException(String message, String parm, String value) {
        super(message, parm, value);
    }
}
