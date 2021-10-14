package ru.otus.homework06.repositories;

import ru.otus.homework06.exceptions.NoSuchCommentException;
import ru.otus.homework06.models.Comment;

import java.util.List;

public interface CommentRepository {
    Comment save(Comment comment);

    List<Comment> getAll();

    void setText(long id, String text) throws NoSuchCommentException;

    void delete(long id) throws NoSuchCommentException;
}
