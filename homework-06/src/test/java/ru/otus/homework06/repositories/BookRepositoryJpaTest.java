package ru.otus.homework06.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework06.exceptions.NoSuchAuthorException;
import ru.otus.homework06.exceptions.NoSuchGenreException;
import ru.otus.homework06.models.Author;
import ru.otus.homework06.models.Book;
import ru.otus.homework06.models.Comment;
import ru.otus.homework06.models.Genre;

import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({BookRepositoryJpa.class, GenreRepositoryJpa.class})
class BookRepositoryJpaTest {
    private static final int EXPECTED_NUMBER_OF_BOOKS = 5;
    private static final long FIRST_BOOK_ID = 1;

    @Autowired
    private BookRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("must get all books with full info")
    @Test
    void shouldReturnAllBooks() {
        val books = this.repositoryJpa.getAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(Objects::nonNull)
                .allMatch(s -> !s.getName().equals(""))
                .allMatch(s -> s.getAuthor() != null)
                .allMatch(s -> s.getGenres() != null && s.getGenres().size() > 0);
    }

    @DisplayName("must get book by id")
    @Test
    void shouldReturnBookById() {
        val actualOptionalBook = this.repositoryJpa.getById(FIRST_BOOK_ID);
        val expectedBook = this.em.find(Book.class, FIRST_BOOK_ID);
        assertThat(actualOptionalBook).isPresent().get().usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("must create new book without genres")
    @Test
    void shouldCreateBook() {
        val book = new Book(0, "Some name", new Author(1, null, null), null, null);
        this.repositoryJpa.save(book);
        assertThat(repositoryJpa.getAll().size()).isEqualTo(EXPECTED_NUMBER_OF_BOOKS + 1);
    }

    @DisplayName("must not create new book with invalid author_id")
    @Test
    void shouldThrowNoSuchAuthorExceptionOnCreate() {
        val book = new Book(0, "Some name", new Author(10, null, null), null, null);
        assertThatThrownBy(() -> this.repositoryJpa.save(book)).isInstanceOf(NoSuchAuthorException.class);
    }

    @DisplayName("must create book with genres")
    @Test
    void shouldCreateBookWithGenres() {
        val genres = Arrays.asList(
                new Genre(1, null),
                new Genre(2, null)
        );
        val book = new Book(0, "Some name",
                new Author(1, null, null),
                genres,
                null
        );
        this.repositoryJpa.save(book);

        assertThat(this.em.find(Book.class, book.getId()).getGenres().stream().mapToLong(Genre::getId).toArray())
                .isEqualTo(genres.stream().mapToLong(Genre::getId).toArray());
    }

    @DisplayName("must not create new book with invalid genre_id")
    @Test
    void shouldThrowNoSuchGenreExceptionOnCreate() {
        val genres = Arrays.asList(
                new Genre(11, null),
                new Genre(21, null)
        );
        val book = new Book(0, "Some name",
                new Author(1, null, null),
                genres, null
        );
        assertThatThrownBy(
                () -> {
                    this.repositoryJpa.save(book);

                    System.out.println(this.em.getEntityManager().createQuery("select b from Book b").getResultList());
                    System.out.println(this.em.getEntityManager().createQuery("select g from Genre g").getResultList());
                }
        ).isInstanceOf(NoSuchGenreException.class);
    }

    @DisplayName("must change name of book")
    @Test
    void shouldSetNameOfBook() {
        val newName = "new name";
        this.repositoryJpa.setName(FIRST_BOOK_ID, newName);
        assertThat(this.em.find(Book.class, FIRST_BOOK_ID).getName()).isEqualTo(newName);
    }

    @DisplayName("must change author of book")
    @Test
    void shouldSetAuthorOfBook() {
        val newAuthorId = 2L;
        this.repositoryJpa.setAuthor(FIRST_BOOK_ID, newAuthorId);
        assertThat(this.em.find(Book.class, FIRST_BOOK_ID).getAuthor().getId()).isEqualTo(newAuthorId);
    }

    @DisplayName("must not change author of book with invalid author_id")
    @Test
    void shouldThrowNoSuchAuthorOnSet() {
        assertThatThrownBy(
                () -> this.repositoryJpa.setAuthor(FIRST_BOOK_ID, 13L)
        ).isInstanceOf(NoSuchAuthorException.class);
    }

    @DisplayName("must change genres of book")
    @Test
    void shouldSetGenresOfBook() {
        val genreIds = new long[]{1, 2, 3};
        this.repositoryJpa.setGenres(FIRST_BOOK_ID, genreIds);
        assertThat(
                this.em.find(Book.class, FIRST_BOOK_ID).getGenres().stream().mapToLong(Genre::getId).toArray()
        ).isEqualTo(genreIds);
    }

    @DisplayName("must comment to book")
    @Test
    void shouldAddCommentToBook() {
        val commentText = "some text";
        this.repositoryJpa.addComment(FIRST_BOOK_ID, commentText);
        assertThat(
                this.em.find(Book.class, FIRST_BOOK_ID).getComments().get(0).getText()
        ).isEqualTo(commentText);
    }

    @DisplayName("must comment to book")
    @Test
    void shouldClearCommentsOfBook() {
        val book = this.em.find(Book.class, FIRST_BOOK_ID);
        book.getComments().add(new Comment(0, "some comment"));
        this.em.merge(book);
        this.repositoryJpa.clearComments(book.getId());
        assertThat(this.em.find(Book.class, FIRST_BOOK_ID).getComments()).isEmpty();
    }

    @DisplayName("must not change genres of book with invalid genre_id")
    @Test
    void shouldThrowNoSuchGenreOnSet() {
        assertThatThrownBy(
                () -> this.repositoryJpa.setGenres(FIRST_BOOK_ID, 13L)
        ).isInstanceOf(NoSuchGenreException.class);

    }

    @DisplayName("must delete book")
    @Test
    void shouldDeleteBook() {
        this.repositoryJpa.delete(FIRST_BOOK_ID);
        assertThat(
                this.em.find(Book.class, FIRST_BOOK_ID)
        ).isNull();
    }
}