package ru.yandex.practicum.filmorate.exception;

public class LikeAlreadyException extends AlreadyException {
    public LikeAlreadyException(String message, String parm, String value) {
        super(message, parm, value);
    }
}
