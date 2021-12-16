package ru.otus.homework09.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.homework09.models.Author;
import ru.otus.homework09.models.Book;
import ru.otus.homework09.models.Comment;
import ru.otus.homework09.models.Genre;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {
    private static final int EXPECTED_NUMBER_OF_BOOKS = 5;
    private static final long FIRST_BOOK_ID = 1;

    @Autowired
    private BookRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("must get all books with full info")
    @Test
    void shouldReturnAllBooks() {
        val books = this.repository.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(Objects::nonNull)
                .allMatch(s -> !s.getName().equals(""))
                .allMatch(s -> s.getAuthor() != null)
                .allMatch(s -> s.getGenres() != null && s.getGenres().size() > 0);
    }

    @DisplayName("must get book by id")
    @Test
    void shouldReturnBookById() {
        val actualOptionalBook = this.repository.findById(FIRST_BOOK_ID);
        val expectedBook = this.em.find(Book.class, FIRST_BOOK_ID);
        assertThat(actualOptionalBook).isPresent().get().usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("must create new book without genres")
    @Test
    void shouldCreateBook() {
        val book = new Book(0, "Some name", new Author(1, null, null), null, null);
        this.repository.save(book);
        assertThat(repository.findAll()).hasSize(EXPECTED_NUMBER_OF_BOOKS+1);
    }

    @DisplayName("must create book with genres")
    @Test
    void shouldCreateBookWithGenres() {
        val genreIds = new long[]{1, 2, 3};
        val genres = Arrays.stream(genreIds).mapToObj(genreId-> this.em.find(Genre.class, genreId)).collect(Collectors.toList());
        val book = new Book(0, "Some name",
                new Author(1, null, null),
                genres,
                null
        );
        this.repository.save(book);

        assertThat(this.em.find(Book.class, book.getId()).getGenres().stream().mapToLong(Genre::getId).toArray())
                .isEqualTo(genres.stream().mapToLong(Genre::getId).toArray());
    }

    @DisplayName("must change name of book")
    @Test
    void shouldSetNameOfBook() {
        val newName = "new name";
        val book = this.em.find(Book.class, FIRST_BOOK_ID);
        book.setName(newName);
        this.repository.save(book);
        assertThat(this.em.find(Book.class, FIRST_BOOK_ID).getName()).isEqualTo(newName);
    }

    @DisplayName("must change author of book")
    @Test
    void shouldSetAuthorOfBook() {
        val book = this.em.find(Book.class, FIRST_BOOK_ID);
        val newAuthorId = 2L;
        val newAuthor = this.em.find(Author.class, newAuthorId);
        book.setAuthor(newAuthor);
        repository.save(book);
        assertThat(this.em.find(Book.class, FIRST_BOOK_ID).getAuthor().getId()).isEqualTo(newAuthorId);
    }

    @DisplayName("must change genres of book")
    @Test
    void shouldSetGenresOfBook() {
        val genreIds = new long[]{1, 2, 3};
        val genres = Arrays.stream(genreIds).mapToObj(genreId-> this.em.find(Genre.class, genreId)).collect(Collectors.toList());
        val book = this.em.find(Book.class, FIRST_BOOK_ID);
        book.setGenres(genres);
        assertThat(
                this.em.find(Book.class, FIRST_BOOK_ID).getGenres().stream().mapToLong(Genre::getId).toArray()
        ).isEqualTo(genreIds);
    }

    @DisplayName("must comment to book")
    @Test
    void shouldAddCommentToBook() {
        val commentText = "some text";
        val book = this.em.find(Book.class, FIRST_BOOK_ID);
        book.getComments().add(new Comment(0, commentText));
        this.repository.save(book);
        assertThat(
                this.em.find(Book.class, FIRST_BOOK_ID).getComments().get(0).getText()
        ).isEqualTo(commentText);
    }

    @DisplayName("must clear comments of book")
    @Test
    void shouldClearCommentsOfBook() {
        val book = this.em.find(Book.class, FIRST_BOOK_ID);
        book.getComments().add(new Comment(0, "some comment"));
        this.em.merge(book);
        book.getComments().clear();
        this.repository.save(book);
        assertThat(this.em.find(Book.class, FIRST_BOOK_ID).getComments()).isEmpty();
    }

    @DisplayName("must delete book")
    @Test
    void shouldDeleteBook() {
        this.repository.delete(this.em.find(Book.class, FIRST_BOOK_ID));
        assertThat(
                this.em.find(Book.class, FIRST_BOOK_ID)
        ).isNull();
    }
}