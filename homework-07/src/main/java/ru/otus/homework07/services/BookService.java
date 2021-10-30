package ru.otus.homework07.services;

import ru.otus.homework07.models.Book;
import ru.otus.homework07.models.Comment;

import java.util.List;

public interface BookService {
    Iterable<Book> findAll();

    Book findById(long id);

    Book save(Book book);

    void setName(long id, String name);

    void setAuthor(long bookId, long authorId);

    void setGenres(long id, long... genreIds);

    List<Comment> getCommentsByBook(long bookId);

    void addComment(long id, String commentText);

    void clearComments(long id);

    void delete(long id);
}
