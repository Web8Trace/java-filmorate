package ru.yandex.practicum.filmorate.exception;

public class AlreadyExistsException extends InvalidRequestException{
    public AlreadyExistsException(String message, String parm, String value) {
        super(message, parm, value);
    }
}
