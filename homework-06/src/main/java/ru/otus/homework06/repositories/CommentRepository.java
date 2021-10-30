package ru.otus.homework06.repositories;

import ru.otus.homework06.exceptions.NoSuchCommentException;
import ru.otus.homework06.models.Book;
import ru.otus.homework06.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);

    List<Comment> getAll();

    Optional<Comment> getById(long id);

    void setText(long id, String text) throws NoSuchCommentException;

    void delete(long id) throws NoSuchCommentException;
}
