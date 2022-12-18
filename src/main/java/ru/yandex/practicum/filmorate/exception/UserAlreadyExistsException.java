package ru.yandex.practicum.filmorate.exception;

public class UserAlreadyExistsException extends AlreadyExistsException {
    public UserAlreadyExistsException(String message, String parm, String value) {
        super(message, parm, value);
    }
}
