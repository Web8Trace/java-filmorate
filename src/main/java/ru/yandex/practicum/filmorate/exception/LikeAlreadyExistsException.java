package ru.yandex.practicum.filmorate.exception;

public class LikeAlreadyExistsException extends AlreadyExistsException {
    public LikeAlreadyExistsException(String message, String parm, String value) {
        super(message, parm, value);
    }
}
