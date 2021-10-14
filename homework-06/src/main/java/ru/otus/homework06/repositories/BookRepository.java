package ru.otus.homework06.repositories;

import ru.otus.homework06.exceptions.NoSuchAuthorException;
import ru.otus.homework06.exceptions.NoSuchBookException;
import ru.otus.homework06.exceptions.NoSuchGenreException;
import ru.otus.homework06.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> getAll();
    Optional<Book> getById(long id);
    Book save(Book book) throws NoSuchAuthorException, NoSuchGenreException;
    void setName (long id, String name) throws NoSuchBookException;
    void setAuthor (long bookId, long authorId) throws NoSuchBookException, NoSuchAuthorException;
    void setGenres (long id, long... genreIds) throws NoSuchBookException, NoSuchGenreException;
    void addComment (long id, String commentText) throws NoSuchBookException;
    void clearComments (long id) throws NoSuchBookException;
    void delete(long id) throws NoSuchBookException;
}
