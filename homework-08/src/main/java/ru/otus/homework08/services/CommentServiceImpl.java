package ru.otus.homework08.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.otus.homework08.exceptions.NoSuchCommentException;
import ru.otus.homework08.models.Comment;
import ru.otus.homework08.repositories.CommentRepository;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public Comment findById(String id) {
        return this.commentRepository.findById(id).orElseThrow(NoSuchCommentException::new);
    }

    @Override
    public Comment save(Comment comment) {
        return this.commentRepository.save(comment);
    }

    @Override
    public Iterable<Comment> getAll() {
        return this.commentRepository.findAll();
    }

    @Override
    public void setText(String id, String text) {
        val comment = findById(id);
        comment.setText(text);
        this.commentRepository.save(comment);
    }

    @Override
    public void delete(String id) {
        val comment = findById(id);
        this.commentRepository.delete(comment);
    }
}
