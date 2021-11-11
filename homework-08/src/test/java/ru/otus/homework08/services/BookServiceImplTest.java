package ru.otus.homework08.services;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework08.models.Author;
import ru.otus.homework08.models.Book;
import ru.otus.homework08.models.Genre;
import ru.otus.homework08.repositories.BookRepository;

import java.util.ArrayList;
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
    @Mock
    private AuthorService authorService;
    @Mock
    private GenreService genreService;
    @InjectMocks
    private BookServiceImpl bookService;


    @DisplayName("gets all books")
    @Test
    void getAll() {
        val books = List.of(
                new Book(null, "name", new Author(null, "surname", "name"), List.of(
                        new Genre(null, "genre")
                ), null)
        );
        given(this.bookRepository.findAll())
                .willReturn(books);

        assertThat(this.bookService.findAll()).isEqualTo(books);
    }

    @DisplayName("gets book by id")
    @Test
    void getById() {
        val book = new Book(
                null, "name",
                new Author(null, "surname", "name"),
                List.of(new Genre(null, "genre")), null
        );
        given(this.bookRepository.findById(book.getId()))
                .willReturn(Optional.of(book));

        assertThat(this.bookService.findById(book.getId())).isEqualTo(book);
    }

    @DisplayName("creates new book")
    @Test
    void create() {
        val book = new Book();
        given(this.bookRepository.save(any()))
                .willReturn(book);

        assertThat(this.bookService.save("","","")).isEqualTo(book);
    }

    @DisplayName("sets name of book")
    @Test
    void setName() {
        this.mockRepositoryFindById();
        val newBookName = "new name";
        bookService.setName(null, newBookName);
        verify(bookRepository, times(1)).save(new Book(null, newBookName, null, null, null));
    }

    @DisplayName("sets author")
    @Test
    void setAuthorId() {
        this.mockRepositoryFindById();
        val authorId = "2L";
        when(authorService.findById(authorId)).thenReturn(new Author(authorId, null, null));
        bookService.setAuthor(null, authorId);
        verify(bookRepository, times(1)).save(new Book(null, null, new Author(authorId, null, null), null, null));
    }

    @DisplayName("sets genres")
    @Test
    void setGenres() {
        this.mockRepositoryFindById();
        val genreIds = new String[]{"1L", "2L"};
        val genres = new ArrayList<Genre>();
        for (String genreId : genreIds) {
            when(genreService.findById(genreId)).thenReturn(new Genre(genreId, null));
            genres.add(new Genre(genreId, null));
        }
        bookService.setGenres(null, genreIds);
        verify(bookRepository, times(1)).save(new Book(null, null, null, genres, null));
    }

    @DisplayName("deletes book")
    @Test
    void delete() {
        this.mockRepositoryFindById();
        bookService.delete(null);
        verify(bookRepository, times(1)).delete(new Book());
    }

    private void mockRepositoryFindById() {
        given(this.bookRepository.findById(any()))
                .willReturn(Optional.of(new Book()));
    }
}