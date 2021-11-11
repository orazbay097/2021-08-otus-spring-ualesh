package ru.otus.homework08.services;

import ru.otus.homework08.models.Book;
import ru.otus.homework08.models.Comment;

import java.util.List;

public interface BookService {
    Iterable<Book> findAll();

    Book findById(String id);

    Book save(String bookName, String authorId, String... genreIds);

    void setName(String id, String name);

    void setAuthor(String bookId, String authorId);

    void setGenres(String id, String... genreIds);

    List<Comment> getCommentsByBook(String bookId);

    void addComment(String id, String commentText);

    void clearComments(String id);

    void delete(String id);
}
