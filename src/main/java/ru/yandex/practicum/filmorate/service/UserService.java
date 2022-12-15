package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.yandex.practicum.filmorate.exception.UserAlreadyException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;
    public void addFriend(int userId, int friendId) throws UserNotFoundException {
        User user =userStorage.findById(userId);
        user.getFriends().add(friendId);
        userStorage.update(user);
        log.debug("Пользователь {} добавил в друзья {}", userId, friendId);
    }

    public void deleteFriend(int userId, int friendId) throws UserNotFoundException {
        User user =userStorage.findById(userId);
        user.getFriends().remove(friendId);
        userStorage.update(user);
        userStorage.deleteFriendship(userId,friendId);
        log.debug("Пользователь {} удалил из друзей {}", userId, friendId);
    }

    public Collection<User> findCommonFriends(int id, int other) throws UserNotFoundException {
        Set<Integer> userFriends = userStorage.findById(id).getFriends();
        Set<Integer> otherFriends = userStorage.findById(other).getFriends();
        List<Integer> commonId = userFriends.stream().filter(otherFriends::contains)
                .collect(Collectors.toList());
        return commonId.stream().map(r -> {
            try {
                log.debug("Передан список пересекающихся друзей пользователя {} с {}", id, other);
                return userStorage.findById(r);
            } catch (UserNotFoundException e) {
                userFriends.remove(r);

                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public Collection<User> getAllFriends(int id) throws UserNotFoundException {
        Set<Integer> friendsId = userStorage.findById(id).getFriends();

        return friendsId.stream().map(r -> {
            try {
                log.debug("Передан список  друзей пользователя {}", id);
                return userStorage.findById(r);
            } catch (UserNotFoundException e) {
                friendsId.remove(r);
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public User addUser(User user) throws UserAlreadyException {
        checkUserName(user);

        return userStorage.add(user);
    }

    public User update(User user) throws UserNotFoundException {
        checkUserName(user);

        return userStorage.update(user);
    }

    public Collection<User> findAll() {
        log.info("Передан список всех Users");
        return userStorage.findAll();
    }

    public User findUserById(int id) throws UserNotFoundException {
        log.info("Передан пользователь id = {}", id);
        return userStorage.findById(id);
    }

    private void checkUserName(User user) {
        if (!StringUtils.hasText(user.getName())) {
            user.setName(user.getLogin());
        }
    }
}
