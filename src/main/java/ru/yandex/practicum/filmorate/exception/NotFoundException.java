package ru.yandex.practicum.filmorate.exception;

public class NotFoundException extends Exception{
    public NotFoundException(){
        super("Ошибка поиска");
    }
}
