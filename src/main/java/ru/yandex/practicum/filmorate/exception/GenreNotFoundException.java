package ru.yandex.practicum.filmorate.exception;

import lombok.RequiredArgsConstructor;


public class GenreNotFoundException extends NotFoundException {
    public GenreNotFoundException(String s, String parm, String value) {
        super(s, parm, value);
    }
}
