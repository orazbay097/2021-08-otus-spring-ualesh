package ru.otus.homework06.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework06.models.Comment;
import ru.otus.homework06.repositories.CommentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public Comment save(Comment comment) {
        return this.commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAll() {
        return this.commentRepository.getAll();
    }

    @Transactional
    @Override
    public void setText(long id, String text) {
        this.commentRepository.setText(id, text);
    }

    @Transactional
    @Override
    public void delete(long id) {
        this.commentRepository.delete(id);
    }
}
