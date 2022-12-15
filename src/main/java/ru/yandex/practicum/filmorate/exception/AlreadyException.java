package ru.yandex.practicum.filmorate.exception;

public class AlreadyException extends InvalidRequestException{
    public AlreadyException(String message, String parm, String value) {
        super(message, parm, value);
    }
}
