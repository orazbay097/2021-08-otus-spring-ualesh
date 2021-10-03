package ru.otus.homework05.repositories;

import ru.otus.homework05.exceptions.NoSuchAuthorException;
import ru.otus.homework05.exceptions.NoSuchBookException;
import ru.otus.homework05.exceptions.NoSuchGenreException;
import ru.otus.homework05.models.Book;

import java.util.List;

public interface BookRepository {
    List<Book> getAll();
    Book getById(long id);
    long create(Book book) throws NoSuchAuthorException, NoSuchGenreException;
    void setName (long id, String name) throws NoSuchBookException;
    void setAuthor (long bookId, long authorId) throws NoSuchBookException, NoSuchAuthorException;
    void setGenres (long id, long... genreIds) throws NoSuchBookException, NoSuchGenreException;
    void delete(long id) throws NoSuchBookException;
}
