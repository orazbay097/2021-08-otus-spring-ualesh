package ru.otus.homework05.services;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework05.models.Author;
import ru.otus.homework05.models.Book;
import ru.otus.homework05.models.Genre;
import ru.otus.homework05.repositories.BookRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void getAll() {
        val books = List.of(
                new Book(1, "name", new Author(1, "surname", "name"), List.of(
                        new Genre(1, "genre")
                ))
        );
        given(this.bookRepository.getAll())
                .willReturn(books);

        assertThat(this.bookService.getAll()).isEqualTo(books);
    }

    @Test
    void getById() {
        val book = new Book(
                1, "name",
                new Author(1, "surname", "name"),
                List.of(new Genre(1, "genre"))
        );
        given(this.bookRepository.getById(book.getId()))
                .willReturn(book);

        assertThat(this.bookService.getById(book.getId())).isEqualTo(book);
    }

    @Test
    void create() {
        val bookId = 1L;
        given(this.bookRepository.create(any()))
                .willReturn(bookId);

        assertThat(this.bookService.create(any())).isEqualTo(bookId);
    }

    @Test
    void setName() {
        val bookId = 1L;
        val bookName = "name";
        bookService.setName(bookId, bookName);
        verify(bookRepository, times(1)).setName(bookId, bookName);
    }

    @Test
    void setAuthorId() {
        val bookId = 1L;
        val authorId = 2L;
        bookService.setAuthor(bookId, authorId);
        verify(bookRepository, times(1)).setAuthor(bookId, authorId);
    }

    @Test
    void setGenres() {
        val bookId = 1L;
        val genreIds = new long[]{1L, 2L};
        bookService.setGenres(bookId, genreIds);
        verify(bookRepository, times(1)).setGenres(bookId, genreIds);
    }

    @Test
    void delete() {
        val bookId = 1L;
        bookService.delete(bookId);
        verify(bookRepository, times(1)).delete(bookId);
    }
}