package ru.yandex.practicum.filmorate.exception;

public class MpaNotFoundException extends NotFoundException{
    public MpaNotFoundException(String s, String parm, String value) {
        super(s, parm, value);
    }
}
