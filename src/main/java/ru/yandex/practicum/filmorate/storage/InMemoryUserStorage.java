package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ru.yandex.practicum.filmorate.validator.Validator.validatedUser;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage{
    private final Map<Long, User> users = new HashMap<>();
    private Long generatedId = 1L;
    @Override
    public User create(User user) throws ValidationException {
        if (validatedUser(user)) {
            if (user.getName() == null || user.getName().isEmpty()){
                user.setName(user.getLogin());
            }
            user.setId(generatedId++);
            users.put(user.getId(), user);
        } else {
            throw new ValidationException();
        }
        log.debug("Польователь создан, общее количество пользователей: {}", users.size());
        return user;
    }

    @Override
    public User update(User user) throws ValidationException, NotFoundException {
        if (!validatedUser(user)) {
            throw new ValidationException();
        }
        Long id = user.getId();
        if (id < 0){
            log.error("id is less than zero");
            throw new ValidationException();
        }
        if (user.getName() == null || user.getName().isEmpty()){
            user.setName(user.getLogin());
        }
        if (!users.containsKey(id)) {
            log.error("Пользователь не найден. не добавлен новый пользователь");
            throw new NotFoundException();
        } else {
            users.put(id,user);
            log.debug("Польователь c идентификатором {} изменен", user.getId());
        }
        return user;
    }

    @Override
    public User findByEmail(String email) throws NotFoundException {
        if (email == null) {
            log.debug("Пользователь не найден. email пуст");
            throw new NotFoundException();
        }
        for (User user:users.values()){
            if (user.getEmail().equals(email)){
                return user;
            }
        }
        log.debug("Пользователь не найден");
        throw new NotFoundException();
    }

    @Override
    public User findById(Long id) throws NotFoundException {
        if (id == null){
            throw new NotFoundException();
        }
        if (!users.containsKey(id)) {
            log.debug("Пользователь не найден. не добавлен новый пользователь");
            throw new NotFoundException();

        }
        return users.get(id);
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }
}
