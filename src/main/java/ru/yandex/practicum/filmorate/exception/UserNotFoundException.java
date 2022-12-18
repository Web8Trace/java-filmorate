package ru.yandex.practicum.filmorate.exception;

import lombok.RequiredArgsConstructor;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String s, String parm, String value) {
        super(s, parm, value);
    }
}

