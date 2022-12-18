package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;


@RestController
@Validated
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) throws UserAlreadyExistsException {

        return userService.addUser(user);
    }


    @PutMapping("/users")
    public User update(@Valid @RequestBody User user) throws UserNotFoundException {

        return userService.update(user);
    }

    @GetMapping("/users")
    public Collection<User> findAll() {

        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User findUserById(@PathVariable @Min(0) int id) throws UserNotFoundException {

        return userService.findUserById(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable @Min(0) int id, @PathVariable @Min(0) int friendId) throws UserNotFoundException {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable @Min(0) int id, @PathVariable @Min(0) int friendId) throws UserNotFoundException {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public Collection<User> findFriends(@PathVariable @Min(0) int id) throws UserNotFoundException {
        return userService.getAllFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Collection<User> findCommonFriends(@PathVariable @Min(0) int id, @PathVariable @Min(0) int otherId) throws UserNotFoundException {
        return userService.findCommonFriends(id, otherId);
    }

}
