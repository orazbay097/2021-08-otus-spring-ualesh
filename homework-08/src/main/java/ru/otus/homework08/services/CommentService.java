package ru.otus.homework08.services;

import ru.otus.homework08.models.Comment;

public interface CommentService {
    Comment findById(String id);
    Comment save(Comment comment);
    Iterable<Comment> getAll();
    void setText(String id, String text);
    void delete(String id);
}
