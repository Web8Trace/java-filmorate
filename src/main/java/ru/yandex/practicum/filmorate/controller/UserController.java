package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;


import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;


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

    @PostMapping
    public User postUser(@RequestBody User user) throws ValidationException {
        return userService.getUserStorage().create(user);
    }

    @PutMapping
    public User putUser(@RequestBody User user) throws ValidationException {
        return userService.getUserStorage().update(user);
    }
    @PutMapping("/{id}/friends/{friendId}")
    public User addFriends(@PathVariable Long id, @PathVariable Long friendId){
        return userService.addToFriends(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriends(@PathVariable Long friendId, @PathVariable Long id){
        return userService.deleteFromFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Set<Long> findFriends(@PathVariable Long id){
        return userService.getUserStorage().findById(id).getFriends();
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<Long> findGenericFriends(@PathVariable Long id, @PathVariable Long otherId){
        return userService.findGenericFriends(id, otherId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public void handleError(final ValidationException e) {
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public void handleError(final NotFoundException e){
    }


}