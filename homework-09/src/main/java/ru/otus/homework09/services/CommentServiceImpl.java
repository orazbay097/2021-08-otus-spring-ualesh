package ru.otus.homework09.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.otus.homework09.exceptions.NoSuchCommentException;
import ru.otus.homework09.models.Comment;
import ru.otus.homework09.repositories.CommentRepository;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public Comment findById(long id) {
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
    public void setText(long id, String text) {
        val comment = findById(id);
        comment.setText(text);
        this.commentRepository.save(comment);
    }

    @Override
    public void delete(long id) {
        val comment = findById(id);
        this.commentRepository.delete(comment);
    }
}
