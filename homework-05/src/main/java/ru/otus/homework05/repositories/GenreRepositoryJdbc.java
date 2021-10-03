package ru.otus.homework05.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework05.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJdbc implements GenreRepository {
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public List<Genre> getGenresOfBook(long bookId) {
        return this.jdbcOperations.query(
                "select g.id, g.name " +
                    "from genres g " +
                    "inner join book_genres bg on g.id=bg.genre_id " +
                    "where bg.book_id=:bookId",
                Map.of("bookId", bookId), new GenreMapper());
    }

    @Override
    public List<Genre> getAllGenres() {
        return this.jdbcOperations.query(
                "select id, name from genres",
                new GenreMapper());
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Genre(rs.getLong("id"), rs.getString("name"));
        }
    }
}
