package ru.yandex.practicum.filmorate.storage.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.user.FriendshipDao;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class FriendshipDaoImpl implements FriendshipDao {
    public static final String UDATE_STATUS_SQL = "UPDATE FRIENDSHIP SET STATUS=? WHERE ID=?";
    private final JdbcTemplate jdbcTemplate;


    @Override
    public Set<Integer> findFriendshipsByUserId(Integer userId) {
        String sql = "SELECT FRIEND_ID FROM FRIENDSHIP WHERE USER_ID=?";
        return new HashSet<>(jdbcTemplate.query(sql,
                (rs, rowNum) -> rs.getInt("FRIEND_ID"), userId));
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        String insertSql = "INSERT INTO FRIENDSHIP(user_id, friend_id, status) VALUES ( ?,?,? ) ON CONFLICT DO NOTHING";
        Optional<Integer> mayBeFriendshipId = checkFrindship(userId, friendId);
        if (mayBeFriendshipId.isPresent()) {
            jdbcTemplate.update(insertSql, ps -> {
                ps.setInt(1, userId);
                ps.setInt(2, friendId);
                ps.setBoolean(3, true);
            });
            jdbcTemplate.update(UDATE_STATUS_SQL, ps -> {
                ps.setBoolean(1, true);
                ps.setInt(2, mayBeFriendshipId.get());
            });
        } else {
            jdbcTemplate.update(insertSql, ps -> {
                ps.setInt(1, userId);
                ps.setInt(2, friendId);
                ps.setBoolean(3, false);
            });
        }
    }

    @Override
    public Optional<Integer> checkFrindship(Integer userId, Integer friendId) {
        String sql = "SELECT ID FROM FRIENDSHIP WHERE USER_ID=? AND FRIEND_ID=?";
        Optional<Integer> mayBeFriendshipId;
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, friendId, userId);
        if (rowSet.next()) {
            return Optional.of(rowSet.getInt("ID"));
        } else return Optional.empty();
    }

    @Override
    public void deleteFriendShip(Integer userId, Integer friendId) {
        String deleteFriendshipSql = "DELETE FROM FRIENDSHIP WHERE USER_ID=? AND FRIEND_ID=?";
        Optional<Integer> mayBeFriendshipId = checkFrindship(userId, friendId);
        mayBeFriendshipId.ifPresent(integer -> jdbcTemplate.update(UDATE_STATUS_SQL, ps -> {
            ps.setBoolean(1, true);
            ps.setInt(2, integer);
        }));
        jdbcTemplate.update(deleteFriendshipSql, ps -> {
            ps.setInt(1, userId);
            ps.setInt(2, friendId);
        });
    }
}
