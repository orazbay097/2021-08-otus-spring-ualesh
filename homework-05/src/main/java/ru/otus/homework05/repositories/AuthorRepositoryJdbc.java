package ru.otus.homework05.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework05.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJdbc implements AuthorRepository {
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public List<Author> getAllAuthors() {
        return this.jdbcOperations.query(
                "select id, surname, name from authors",
                new AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Author(rs.getLong("id"), rs.getString("surname"), rs.getString("name"));
        }
    }
}
