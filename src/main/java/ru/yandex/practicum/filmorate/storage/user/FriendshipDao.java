package ru.yandex.practicum.filmorate.storage.user;

import java.util.Optional;
import java.util.Set;

public interface FriendshipDao {

    Set<Integer> findFriendshipsByUserId(Integer id);

    void addFriend(Integer userId, Integer friendId);

    Optional<Integer> checkFrindship(Integer userId, Integer friendId);

    void deleteFriendShip(Integer userId, Integer friendId);

}
