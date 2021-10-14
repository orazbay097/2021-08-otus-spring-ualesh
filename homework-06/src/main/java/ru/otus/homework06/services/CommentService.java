package ru.otus.homework06.services;

import ru.otus.homework06.models.Comment;

import java.util.List;

public interface CommentService {
    Comment save(Comment comment);
    List<Comment> getAll();
    void setText(long id, String text);
    void delete(long id);
}
