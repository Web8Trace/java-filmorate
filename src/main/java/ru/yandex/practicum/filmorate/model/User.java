package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.controller.Validators.UserLogin;

import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Set;

@Data
public class User {
    Integer id;
    @Email(message = "Email.")
    final String email;

    @UserLogin
    final String login;
     String name;
    @Past(message = "Дата рождения.")
    final LocalDate birthday;
    Set<Integer> friends;

    public User(Integer id, String email, String login, String name, LocalDate birthday, Set<Integer> friends) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        this.friends = friends;
    }
}
