package ru.otus.homework06.services;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework06.models.Author;
import ru.otus.homework06.models.Book;
import ru.otus.homework06.models.Genre;
import ru.otus.homework06.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    @DisplayName("gets all books")
    @Test
    void getAll() {
        val books = List.of(
                new Book(1, "name", new Author(1, "surname", "name"), List.of(
                        new Genre(1, "genre")
                ), null)
        );
        given(this.bookRepository.getAll())
                .willReturn(books);

        assertThat(this.bookService.getAll()).isEqualTo(books);
    }

    @DisplayName("gets book by id")
    @Test
    void getById() {
        val book = new Book(
                1, "name",
                new Author(1, "surname", "name"),
                List.of(new Genre(1, "genre")), null
        );
        given(this.bookRepository.getById(book.getId()))
                .willReturn(Optional.of(book));

        assertThat(this.bookService.getById(book.getId()).get()).isEqualTo(book);
    }

    @DisplayName("creates new book")
    @Test
    void create() {
        val book = new Book();
        given(this.bookRepository.save(any()))
                .willReturn(book);

        assertThat(this.bookService.save(any())).isEqualTo(book);
    }

    @DisplayName("sets name of book")
    @Test
    void setName() {
        val bookId = 1L;
        when(bookRepository.getById(bookId)).thenReturn(Optional.of(new Book()));
        bookService.setName(bookId, "name");
        verify(bookRepository, times(1)).getById(bookId);
    }

    @DisplayName("sets author")
    @Test
    void setAuthorId() {
        val bookId = 1L;
        val authorId = 2L;
        bookService.setAuthor(bookId, authorId);
        verify(bookRepository, times(1)).setAuthor(bookId, authorId);
    }

    @DisplayName("sets genres")
    @Test
    void setGenres() {
        val bookId = 1L;
        val genreIds = new long[]{1L, 2L};
        bookService.setGenres(bookId, genreIds);
        verify(bookRepository, times(1)).setGenres(bookId, genreIds);
    }

    @DisplayName("deletes book")
    @Test
    void delete() {
        val bookId = 1L;
        bookService.delete(bookId);
        verify(bookRepository, times(1)).delete(bookId);
    }
}