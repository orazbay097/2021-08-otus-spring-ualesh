package ru.otus.homework09.services;

import ru.otus.homework09.models.Comment;

public interface CommentService {
    Comment findById(long id);
    Comment save(Comment comment);
    Iterable<Comment> getAll();
    void setText(long id, String text);
    void delete(long id);
}
