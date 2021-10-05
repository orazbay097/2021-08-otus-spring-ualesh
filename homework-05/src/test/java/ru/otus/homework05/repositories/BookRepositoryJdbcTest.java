package ru.otus.homework05.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework05.exceptions.NoSuchAuthorException;
import ru.otus.homework05.exceptions.NoSuchGenreException;
import ru.otus.homework05.models.Author;
import ru.otus.homework05.models.Book;
import ru.otus.homework05.models.Genre;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@Import({BookRepositoryJdbc.class, GenreRepositoryJdbc.class})
class BookRepositoryJdbcTest {
    private static final int EXPECTED_NUMBER_OF_BOOKS = 5;
    private static final String FIRST_BOOK_NAME = "b1";
    private static final long FIRST_BOOK_AUTHOR_ID = 1;
    private static final long FIRST_BOOK_ID = 1;

    @Autowired
    private BookRepositoryJdbc repositoryJdbc;

    @DisplayName("must get all books with full info")
    @Test
    void shouldReturnAllBooks() {
        val books = this.repositoryJdbc.getAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(Objects::nonNull)
                .allMatch(s -> !s.getName().equals(""))
                .allMatch(s -> s.getAuthor() != null)
                .allMatch(s -> s.getGenres() != null && s.getGenres().size() > 0);
    }

    @DisplayName("must get book by id")
    @Test
    void shouldReturnBookById() {
        val book = this.repositoryJdbc.getById(FIRST_BOOK_ID);
        assertThat(book).isNotNull().matches(
                (b -> b.getName().equals(FIRST_BOOK_NAME) &&
                        b.getAuthor().getId() == FIRST_BOOK_AUTHOR_ID), "not full first book"
        );
    }

    @DisplayName("must create new book without genres")
    @Test
    void shouldCreateBook() {
        val book = new Book("Some name", new Author(1, null, null), null);
        this.repositoryJdbc.create(book);
        assertThat(repositoryJdbc.getAll().size()).isEqualTo(EXPECTED_NUMBER_OF_BOOKS + 1);
    }

    @DisplayName("must not create new book with invalid author_id")
    @Test
    void shouldThrowNoSuchAuthorExceptionOnCreate() {
        val book = new Book("Some name", new Author(10, null, null), new ArrayList<>());
        assertThatThrownBy(() -> this.repositoryJdbc.create(book)).isInstanceOf(NoSuchAuthorException.class);
    }

    @DisplayName("must create book with genres")
    @Test
    void shouldCreateBookWithGenres() {
        val genres = Arrays.asList(
                new Genre(1, null),
                new Genre(2, null)
        );
        val book = new Book("Some name",
                new Author(1, null, null),
                genres
        );
        val bookId = this.repositoryJdbc.create(book);
        assertThat(this.repositoryJdbc.getById(bookId).getGenres().stream().mapToLong(Genre::getId).toArray())
                .isEqualTo(genres.stream().mapToLong(Genre::getId).toArray());
    }

    @DisplayName("must not create new book with invalid genre_id")
    @Test
    void shouldThrowNoSuchGenreExceptionOnCreate() {
        val genres = Arrays.asList(
                new Genre(11, null),
                new Genre(21, null)
        );
        val book = new Book("Some name",
                new Author(1, null, null),
                genres
        );
        assertThatThrownBy(
                () -> this.repositoryJdbc.create(book)
        ).isInstanceOf(NoSuchGenreException.class);
    }

    @DisplayName("must change name of book")
    @Test
    void shouldSetNameOfBook() {
        val newName = "new name";
        this.repositoryJdbc.setName(FIRST_BOOK_ID, newName);
        assertThat(this.repositoryJdbc.getById(FIRST_BOOK_ID).getName()).isEqualTo(newName);
    }

    @DisplayName("must change author of book")
    @Test
    void shouldSetAuthorOfBook() {
        val newAuthorId = 2L;
        this.repositoryJdbc.setAuthor(FIRST_BOOK_ID, newAuthorId);
        assertThat(this.repositoryJdbc.getById(FIRST_BOOK_ID).getAuthor().getId()).isEqualTo(newAuthorId);
    }

    @DisplayName("must not change author of book with invalid author_id")
    @Test
    void shouldThrowNoSuchAuthorOnSet() {
        assertThatThrownBy(
                ()->this.repositoryJdbc.setAuthor(FIRST_BOOK_ID, 13L)
        ).isInstanceOf(NoSuchAuthorException.class);
    }

    @DisplayName("must change genres of book")
    @Test
    void shouldSetGenresOfBook() {
        val genreIds = new long[]{1, 2, 3};
        this.repositoryJdbc.setGenres(FIRST_BOOK_ID, genreIds);
        assertThat(
                this.repositoryJdbc.getById(FIRST_BOOK_ID).getGenres().stream().mapToLong(Genre::getId).toArray()
        ).isEqualTo(genreIds);
    }

    @DisplayName("must not change genres of book with invalid genre_id")
    @Test
    void shouldThrowNoSuchGenreOnSet() {
        assertThatThrownBy(
                ()->this.repositoryJdbc.setGenres(FIRST_BOOK_ID, 13L)
        ).isInstanceOf(NoSuchGenreException.class);

    }

    @DisplayName("must delete book")
    @Test
    void shouldDeleteBook() {
        this.repositoryJdbc.delete(FIRST_BOOK_ID);
        assertThat(
                this.repositoryJdbc.getById(FIRST_BOOK_ID)
        ).isNull();
    }
}