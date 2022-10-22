package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage{
    User create(User user) throws ValidationException;
    User update(User user) throws ValidationException;
    User findByEmail(String email);
    User findById(Long id);
    Collection<User> findAll();

}
