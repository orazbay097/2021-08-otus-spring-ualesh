package ru.otus.homework07.services;

import ru.otus.homework07.models.Comment;

public interface CommentService {
    Comment findById(long id);
    Comment save(Comment comment);
    Iterable<Comment> getAll();
    void setText(long id, String text);
    void delete(long id);
}
