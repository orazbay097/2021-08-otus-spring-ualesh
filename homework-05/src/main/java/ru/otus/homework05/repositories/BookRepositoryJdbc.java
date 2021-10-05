package ru.otus.homework05.repositories;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework05.exceptions.NoSuchAuthorException;
import ru.otus.homework05.exceptions.NoSuchBookException;
import ru.otus.homework05.exceptions.NoSuchGenreException;
import ru.otus.homework05.models.Author;
import ru.otus.homework05.models.Book;
import ru.otus.homework05.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJdbc implements BookRepository {
    private final GenreRepository genreRepository;
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public List<Book> getAll() {
        val books = this.jdbcOperations.query(
                "select " +
                        "b.id, b.name," +
                        "a.id author_id, a.surname author_surname, a.name author_name," +
                        "g.id genre_id, g.name genre_name " +
                        "from books b " +
                        "inner join authors a on b.author_id=a.id " +
                        "left join book_genres bg on b.id=bg.book_id " +
                        "left join genres g on bg.genre_id=g.id",
                new BookResultSetExtractor());
        return new ArrayList<>(Objects.requireNonNull(books).values());
    }

    @Override
    public Book getById(long id) {
        val books = this.jdbcOperations.query(
                "select " +
                        "b.id, b.name," +
                        "a.id author_id, a.surname author_surname, a.name author_name," +
                        "g.id genre_id, g.name genre_name " +
                        "from books b " +
                        "inner join authors a on b.author_id=a.id " +
                        "left join book_genres bg on b.id=bg.book_id " +
                        "left join genres g on bg.genre_id=g.id " +
                        "where b.id=:id",
                Map.of("id", id),
                new BookResultSetExtractor()
        );
        return Objects.requireNonNull(books).get(id);
    }

    @Override
    public long create(Book book) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("name", book.getName());
            params.addValue("author_id", book.getAuthor().getId());
            this.jdbcOperations.update(
                    "insert into books(name,author_id)" +
                            "values(:name, :author_id)",
                    params,
                    keyHolder);
            long bookId = Objects.requireNonNull(keyHolder.getKey()).longValue();
            if (book.getGenres() != null && book.getGenres().size() > 0)
                this.addGenres(bookId, book.getGenres().stream().mapToLong(Genre::getId).toArray());

            return bookId;
        } catch (DataIntegrityViolationException ex) {
            throw new NoSuchAuthorException(ex);
        }
    }

    @Override
    public void setName(long id, String name) {
        if (this.jdbcOperations.update(
                "update books set name=:name where id=:id",
                Map.of("id", id, "name", name)
        ) == 0) throw new NoSuchBookException();
    }

    @Override
    public void setAuthor(long bookId, long authorId) {
        try {
            if (this.jdbcOperations.update(
                    "update books set author_id=:authorId where id=:id",
                    Map.of("id", bookId, "authorId", authorId)
            ) == 0) throw new NoSuchBookException();
        } catch (DataIntegrityViolationException ex) {
            throw new NoSuchAuthorException(ex);
        }
    }

    @Override
    public void setGenres(long id, long... genreIds) {
        this.clearGenres(id);
        this.addGenres(id, genreIds);
    }

    @Override
    public void delete(long id) {
        if (this.jdbcOperations.update(
                "delete from books where id=:id",
                Map.of("id", id)
        ) == 0) throw new NoSuchBookException();
    }

    private void clearGenres(long id) {
        if (this.jdbcOperations.update(
                "delete from book_genres where book_id=:id",
                Map.of("id", id)
        ) == 0) throw new NoSuchBookException();
    }

    private void addGenres(long bookId, long... genreIds) {
        try {
            val values = Arrays.stream(genreIds).mapToObj(
                    genreId -> String.format("(%d, %d)", bookId, genreId)
            ).collect(Collectors.joining(", "));
            this.jdbcOperations.update(
                    "insert into book_genres(book_id, genre_id) values" + values, Map.of());
        } catch (DataIntegrityViolationException ex) {
            throw new NoSuchGenreException(ex);
        }
    }

    private static class BookResultSetExtractor implements ResultSetExtractor<Map<Long, Book>> {
        @Override
        public Map<Long, Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, Book> books = new HashMap<>();
            while (rs.next()) {
                long id = rs.getLong("id");
                Book book = books.get(id);
                if (book == null) {
                    book = new Book(
                            id,
                            rs.getString("name"),
                            new Author(
                                    rs.getLong("author_id"),
                                    rs.getString("author_surname"),
                                    rs.getString("author_name")
                            ),
                            new ArrayList<>()
                    );
                    books.put(id, book);
                }
                book.getGenres().add(
                        new Genre(rs.getLong("genre_id"), rs.getString("genre_name")));
            }
            return books;
        }
    }
}