package ru.otus.homework06.services;

import ru.otus.homework06.models.Book;
import ru.otus.homework06.models.Comment;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAll();

    Optional<Book> getById(long id);

    Book save(Book book);

    void setName(long id, String name);

    void setAuthor(long bookId, long authorId);

    void setGenres(long id, long... genreIds);

    List<Comment> getCommentsByBook(long bookId);

    void addComment(long id, String commentText);

    void clearComments(long id);

    void delete(long id);
}
