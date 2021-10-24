package ru.otus.homework06.repositories;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import ru.otus.homework06.exceptions.NoSuchCommentException;
import ru.otus.homework06.models.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentRepositoryJpa implements CommentRepository{
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() <= 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public List<Comment> getAll() {
        val query = this.em.createQuery("select c from Comment c", Comment.class);
        return  query.getResultList();
    }

    @Override
    public Optional<Comment> getById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public void setText(long id, String text) {
        val comment  = this.getById(id).orElseThrow(NoSuchCommentException::new);
        comment.setText(text);
        this.save(comment);
    }

    @Override
    public void delete(long id) {
        val comment  = this.getById(id).orElseThrow(NoSuchCommentException::new);
        em.remove(comment);
    }
}
