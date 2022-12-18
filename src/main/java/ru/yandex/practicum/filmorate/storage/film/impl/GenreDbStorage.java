package ru.yandex.practicum.filmorate.storage.film.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
   private final JdbcTemplate jdbcTemplate;

    @Override
    public Genre findGenreById(Integer genreId) throws GenreNotFoundException {
        String sql = "SELECT * FROM GENRES WHERE GENRES_ID =?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> createGenre(rs), genreId).stream().findAny()
                .orElseThrow(()->new GenreNotFoundException("Жанр не найден","id",String.valueOf(genreId)));
    }

    @Override
    public Collection<Genre> findAllGenre() {
        String sql = "SELECT * FROM GENRES ORDER BY GENRES_ID";
        return jdbcTemplate.query(sql, (rs, rowNum) -> createGenre(rs));
    }

    @Override
    public Set<Genre> findGenreFilm(Integer filmId) {
        String sql = "SELECT G2.GENRES_ID, G2.GENRE_NAME\n" +
                "FROM FILM_GENRES AS gf\n" +
                "JOIN GENRES G2 on G2.GENRES_ID = gf.GENRES_ID\n" +
                "WHERE FILM_ID=?" +
                "ORDER BY GENRES_ID";
        Collection<Genre> genres = jdbcTemplate.query(sql, (rs, rowNum) -> createGenre(rs),filmId);
        if (genres.isEmpty()){
            return new HashSet<>();
        }
        return new HashSet<>(genres);
    }

    @Override
    public void addFilmToGenre(Integer filmId, Integer genreId) {
        String sql = "INSERT INTO FILM_GENRES(GENRES_ID, FILM_ID) VALUES ( ?,? ) ";
        jdbcTemplate.update(sql, ps -> {
            ps.setInt(1, genreId);
            ps.setInt(2, filmId);
        });
    }


    @Override
    public void deleteFilmToGenre(Integer filmId, Integer genreId) {
        String sql ="DELETE FROM FILM_GENRES WHERE FILM_ID=? AND GENRES_ID = ?";
        jdbcTemplate.update(sql,ps -> {
            ps.setInt(1,filmId);
            ps.setInt(2,genreId);
        });

    }


    private Genre createGenre(ResultSet rs) throws SQLException {
        int id = rs.getInt("GENRES_ID");
        String name = rs.getString("GENRE_NAME");
        return new Genre(id, name);
    }
}

