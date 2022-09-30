package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmorate.validator.Validator.validatedUser;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private Map<Long, User> users = new HashMap<>();
    public Long generatedId = 0L;

    @GetMapping
    public List<User> getUsers() {
        log.info("Текущее число пользователей: {}", users.size());
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User postUser(@RequestBody User user) throws ValidationException {
        if (validatedUser(user)) {
            if (user.getName() == null || user.getName().isEmpty()){
                user.setName(user.getLogin());
            }
            user.setId(generatedId++);
            users.put(user.getId(),user);
        } else {
            throw new ValidationException();
        }
        log.debug("Польователь создан, общее количество пользователей: {}", users.size());
        log.debug("Польователь c идентификатором {} изменен", user.getId());
        return user;
    }

    @PutMapping
    public User putUser(@RequestBody User user) throws ValidationException {
        Long id = user.getId();
        if (validatedUser(user)) {
            if (user.getName() == null || user.getName().isEmpty()){
                user.setName(user.getLogin());
            }
                if (!users.containsKey(id)) {
                    users.put(id,user);
                    log.debug("Пользователь не найден. добавлен новый пользователь");
                } else {
                    users.put(id,user);
                    log.debug("Польователь c идентификатором {} изменен", user.getId());
                }
        } else {
            throw new ValidationException();
        }
        return user;
    }
}