package ru.otus.homework08.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework08.models.Author;
import ru.otus.homework08.models.Book;
import ru.otus.homework08.models.Comment;
import ru.otus.homework08.models.Genre;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class BookRepositoryTest extends AbstractRepositoryTest {
    private static final int EXPECTED_NUMBER_OF_BOOKS = 1;

    @Autowired
    private BookRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

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
        val book = mongoTemplate.findAll(Book.class).get(0);

        val actualOptionalBook = this.repository.findById(book.getId());
        val expectedBook = this.mongoTemplate.findById(book.getId(), Book.class);
        assertThat(actualOptionalBook).isPresent().get().usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("must create new book without genres")
    @Test
    void shouldCreateBook() {
        val book = new Book(null, "Some name", this.mongoTemplate.findAll(Author.class).get(0), null, null);
        this.repository.save(book);
        assertThat(repository.findAll()).hasSize(EXPECTED_NUMBER_OF_BOOKS + 1);
    }

    @DisplayName("must create book with genres")
    @Test
    void shouldCreateBookWithGenres() {
        val genre = mongoTemplate.findAll(Genre.class).get(0);
        val book = this.repository.save(new Book(null, "Some name",
                mongoTemplate.findAll(Author.class).get(0),
                List.of(genre),
                null
        ));

        assertThat(this.mongoTemplate.findById(book.getId(), Book.class).getGenres().get(0)).isEqualTo(genre);
    }

    @DisplayName("should delete comments of book when book deleted")
    @Test
    void shouldDeleteCommentsOfBookWhenDeleted(){
        val comment = mongoTemplate.save(new Comment(null,"comment"));
        val book = mongoTemplate.findAll(Book.class).get(0);
        book.setComments(List.of(comment));
        mongoTemplate.save(book);
        repository.delete(book);
        assertThat(mongoTemplate.findById(comment.getId(), Comment.class)).isNull();
        //rollback
        book.setId(null);
        mongoTemplate.save(book);
    }

    @DisplayName("should delete comments of book when book comments cleared")
    @Test
    void shouldDeleteCommentsOfBookWhenCleared(){
        val comment = mongoTemplate.save(new Comment(null,"comment"));
        val book = mongoTemplate.findAll(Book.class).get(0);
        book.setComments(List.of(comment));
        mongoTemplate.save(book);
        book.setComments(List.of());
        repository.save(book);
        assertThat(mongoTemplate.findById(comment.getId(), Comment.class)).isNull();
    }

    @DisplayName("should delete book comments by id")
    @Test
    void shouldDeleteCommentsOfBookById(){
        val comment = mongoTemplate.save(new Comment(null,"some comment"));
        val book = mongoTemplate.findAll(Book.class).get(0);
        book.setComments(List.of(comment));
        mongoTemplate.save(book);
        repository.removeCommentById(comment.getId());
        assertThat(mongoTemplate.findById(book.getId(), Book.class).getComments()).isEmpty();
    }
}