package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Friendship {
    private final Integer id;
    private final Integer userId;
    private final Integer friendId;
    private final Boolean status;

}
