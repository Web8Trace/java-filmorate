package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage{
    User create(User user) throws ValidationException, NotFoundException;
    User update(User user) throws ValidationException, NotFoundException;
    User findByEmail(String email) throws NotFoundException;
    User findById(Long id) throws NotFoundException;
    Collection<User> findAll();

}
