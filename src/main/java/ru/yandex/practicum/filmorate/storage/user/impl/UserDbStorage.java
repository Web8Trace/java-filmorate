package ru.yandex.practicum.filmorate.storage.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.FriendshipDao;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Component
@Primary
@Slf4j
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private final FriendshipDao friendshipDao;

    @Override
    public User findById(int id) throws UserNotFoundException {
        String sql = "SELECT * FROM USERS WHERE USER_ID=?";
        return jdbcTemplate.query(sql, (rs, colNum) -> createUser(rs), String.valueOf(id))
                .stream().findAny()
                .orElseThrow(()->new UserNotFoundException("Ползователь не найден.", "id", String.valueOf(id)));
    }

    private User createUser(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("USER_ID");
        String email = rs.getString("EMAIL");
        String login = rs.getString("LOGIN");
        String name = rs.getNString("NAME");
        LocalDate birthday = rs.getDate("BIRTHDAY").toLocalDate();
        Set<Integer> friends = friendshipDao.findFriendshipsByUserId(id);
        return new User(id, email, login, name, birthday, friends);
    }

    @Override
    public User findByEmail(String email) throws UserNotFoundException {
        String sql = "SELECT * FROM USERS WHERE EMAIL=?";
        return jdbcTemplate.query(sql, (rs, colNum) -> createUser(rs), String.valueOf(email))
                .stream().findAny()
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден", "Email", email));
    }

    @Override
    public User add(User user) throws UserAlreadyExistsException {

        if (user.getId() == null) {
            String sql = "INSERT INTO USERS(email, login, name, birthday) VALUES (?,?,?,?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, user.getEmail());
                statement.setString(2, user.getLogin());
                statement.setString(3, user.getName());
                statement.setDate(4, Date.valueOf(user.getBirthday()));
                return statement;
            }, keyHolder);

            user.setId(keyHolder.getKey().intValue());
            return user;
        } else {
            throw new UserAlreadyExistsException("Создание нового пользователя с id запрещено", "id",
                    String.valueOf(user.getId()));
        }
    }


    @Override
    public User update(User user) throws UserNotFoundException {
        this.findById(user.getId());

        String sql = "UPDATE USERS SET EMAIL =?, LOGIN =?,NAME=?,BIRTHDAY=?" +
                "WHERE USER_ID = ?";
        int update = jdbcTemplate.update(sql, ps -> {
            ps.setInt(5, user.getId());
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getName());
            ps.setDate(4, Date.valueOf(user.getBirthday()));
        });
        if (user.getFriends() != null) {
            user.getFriends().forEach(r -> friendshipDao.addFriend(user.getId(), r));
        }
        return user;
    }

    @Override
    public User delete(User user) {
        String sql = "DELETE FROM FRIENDSHIP WHERE USER_ID = ?";
        jdbcTemplate.update(sql, ps -> ps.setInt(1, user.getId()));
        return user;
    }

    @Override
    public Collection<User> findAll() {
        String sql = "SELECT * FROM USERS";

        return jdbcTemplate.query(sql, (rs, rowNum) -> createUser(rs));
    }

    @Override
    public void deleteFriendship(Integer userId, Integer friendId) {
        friendshipDao.deleteFriendShip(userId, friendId);
    }
}
