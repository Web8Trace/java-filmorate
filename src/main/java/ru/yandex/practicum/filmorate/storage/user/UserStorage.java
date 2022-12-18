package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.exception.UserAlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User findById(int id) throws UserNotFoundException;
    User findByEmail(String email) throws UserNotFoundException;
    User add(User user) throws UserAlreadyExistsException;
    User update(User user) throws UserNotFoundException;
    User delete(User user) throws UserNotFoundException;
    Collection<User> findAll();
    void deleteFriendship(Integer userId, Integer friendId);
}
