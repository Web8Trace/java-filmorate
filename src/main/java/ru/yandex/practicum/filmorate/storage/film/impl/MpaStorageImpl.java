package ru.yandex.practicum.filmorate.storage.film.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class MpaStorageImpl implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Mpa findMpaById(Integer mpaId) throws MpaNotFoundException {
        String sql = "SELECT * FROM MPAS WHERE MPA_ID = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mpaCreate(rs), mpaId).stream().findAny()
                .orElseThrow(()->new MpaNotFoundException("MPA не найден", "mpa id", String.valueOf(mpaId)));
    }

    @Override
    public Collection<Mpa> findAllMpa() {
        String sql = "SELECT * FROM MPAS";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mpaCreate(rs));
    }

    private Mpa mpaCreate(ResultSet rs) throws SQLException {
        int id = rs.getInt("MPA_ID");
        String name = rs.getString("MPA_NAME");
        String description = rs.getString("MPA_DESCRIPTION");
        return new Mpa(id, name, description);
    }
}
