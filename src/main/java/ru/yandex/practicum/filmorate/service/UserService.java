package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }


    public User addToFriends(Long idOne, Long idTho) throws NotFoundException {
        User user = userStorage.findById(idOne);
        user.getFriends().add(idTho);
        user = userStorage.findById(idTho);
        user.getFriends().add(idOne);
        return user;
    }

    public User deleteFromFriends(Long idOne, Long idTho) throws NotFoundException {
        User user = userStorage.findById(idOne);
        user.getFriends().remove(idTho);
        user = userStorage.findById(idTho);
        user.getFriends().remove(idOne);
        return user;
    }

    public Set<Long> findGenericFriends(Long idOne, Long idTho) throws NotFoundException {
        User userOne = userStorage.findById(idOne);
        User userTho = userStorage.findById(idTho);
        Set<Long> genericFriends = new HashSet<>();
        for(Long one : userOne.getFriends()){
            for (Long tho:userTho.getFriends()){
                if (one.equals(tho)){
                    genericFriends.add(one);
                }
            }
        }
        return genericFriends;
    }

    public User create(User user) throws ValidationException, NotFoundException{
        return userStorage.create(user);
    }
    public User update(User user) throws ValidationException, NotFoundException{
        return userStorage.update(user);
    }
    public User findByEmail(String email) throws NotFoundException{
        return userStorage.findByEmail(email);
    }
    public User findById(Long id) throws NotFoundException{
        return userStorage.findById(id);
    }
    public Collection<User> findAll(){
        return userStorage.findAll();
    }
}