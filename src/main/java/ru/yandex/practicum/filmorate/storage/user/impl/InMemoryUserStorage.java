package ru.yandex.practicum.filmorate.storage.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserAlreadyException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    int id = 1;
    final Map<Integer, User> userMap = new HashMap<>();

    @Override
    public User findById(int findedId) throws UserNotFoundException {
        if (userMap.containsKey(findedId)) {
            return userMap.get(findedId);
        } else {
            log.debug("Ползователь не найден. id {}", findedId);
            throw new UserNotFoundException("Ползователь не найден.", "id", String.valueOf(findedId));
        }

    }

    @Override
    public User findByEmail(String email) {
        return userMap.values().stream().filter(u -> u.getEmail().equals(email))
                .findFirst().orElseThrow();
    }

    @Override
    public User add(User user) throws UserAlreadyException {
        if (user.getId() == null) {
            user.setId(id);
            userMap.put(user.getId(), user);
            id++;
            log.info("Добавлен новый ползователь: {} присвоенно id {} ", user.getLogin(), id);
            return user;
        } else if (!userMap.containsKey(user.getId())) {
            userMap.put(user.getId(), user);
            log.info("Добавлен новый ползователь: {} ", user.getLogin());
            return user;
        } else {
            log.info("Ползователь уже существует.Id {}", user.getId());
            throw new UserAlreadyException("Ползователь уже существует.", "Id", String.valueOf(user.getId()));
        }

    }

    @Override
    public User update(User user) throws UserNotFoundException {
        if (user.getId() != null && userMap.containsKey(user.getId())) {
            log.info("Обновлен ползователь: {}", user.getLogin());
            userMap.put(user.getId(), user);
        } else {
            log.info("Пользователь не найден {}", id);
            throw new UserNotFoundException("Ползователь не найден.", "id", String.valueOf(user.getId()));
        }
        return user;
    }

    @Override
    public User delete(User user) throws UserNotFoundException {
        if (userMap.containsKey(user.getId())) {
            userMap.remove(user.getId());
            log.info("Удален ползователь: {}", user.getLogin());
            return user;
        } else {
            log.info("Пользователь не найден {}", id);
            throw new UserNotFoundException("Ползователь не найден.", "id", String.valueOf(user.getId()));
        }
    }

    @Override
    public Collection<User> findAll() {
        return Collections.unmodifiableCollection(userMap.values());
    }

    @Override
    public void deleteFriendship(Integer userId, Integer friendId) {

    }
}
