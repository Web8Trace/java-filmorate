package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ErrorResponse;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.*;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping
    public Collection<User> getUsers() {
        return  userService.getUserStorage().findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) throws NotFoundException {
        return userService.getUserStorage().findById(id);
    }

    @PostMapping
    public User postUser(@RequestBody User user) throws ValidationException, NotFoundException {
        return userService.getUserStorage().create(user);
    }

    @PutMapping
    public User putUser(@RequestBody User user) throws ValidationException, NotFoundException {
        return userService.getUserStorage().update(user);
    }
    @PutMapping("/{id}/friends/{friendId}")
    public User addFriends(@PathVariable Long id, @PathVariable Long friendId) throws NotFoundException {
        if(userService.getUserStorage().findById(friendId) != null) {
            return userService.addToFriends(id, friendId);
        }else {
            return userService.getUserStorage().findById(id);
        }
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriends(@PathVariable Long friendId, @PathVariable Long id) throws NotFoundException {
        return userService.deleteFromFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> findFriends(@PathVariable Long id) throws NotFoundException {
        Set<Long> set;
        set=userService.getUserStorage().findById(id).getFriends();
        List<User>userSet = new ArrayList<>();
        for (Long i : set){
            userSet.add(userService.getUserStorage().findById(i));
        }
        return userSet;
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> findGenericFriends(@PathVariable Long id, @PathVariable Long otherId) throws NotFoundException {
        Set<Long> set;
        set = userService.findGenericFriends(id, otherId);
        List<User>userSet = new ArrayList<>();
        for (Long i:set){
            userSet.add(userService.getUserStorage().findById(i));
        }
        return userSet;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleError(final NotFoundException e){
        return new ErrorResponse(
                e.getMessage()
        );
    }
}