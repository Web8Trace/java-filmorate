package ru.yandex.practicum.filmorate.exception;

public class InvalidRequestException extends Exception{
    final String parm;
    final String value;

    public InvalidRequestException(String message, String parm, String value) {
        super(message);
        this.parm = parm;
        this.value = value;
    }

    public String getParm() {
        return parm;
    }

    public String getValue() {
        return value;
    }
}
