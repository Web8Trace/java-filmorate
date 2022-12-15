package ru.yandex.practicum.filmorate.exception;

public class UserAlreadyException extends AlreadyException {
    public UserAlreadyException(String message, String parm, String value) {
        super(message, parm, value);
    }
}
