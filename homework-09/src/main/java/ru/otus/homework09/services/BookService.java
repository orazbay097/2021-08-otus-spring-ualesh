package ru.otus.homework09.services;

import ru.otus.homework09.dto.BookDto;
import ru.otus.homework09.models.Comment;

import java.util.List;

public interface BookService {
    Iterable<BookDto> findAll();

    BookDto findById(long id);

    BookDto save(BookDto book);

    void setName(long id, String name);

    void setAuthor(long bookId, long authorId);

    void setGenres(long id, long... genreIds);

    List<Comment> getCommentsByBook(long bookId);

    void addComment(long id, String commentText);

    void clearComments(long id);

    void delete(long id);
}
