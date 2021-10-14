package ru.otus.homework06.repositories;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Repository;
import ru.otus.homework06.exceptions.NoSuchCommentException;
import ru.otus.homework06.models.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
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
    public void setText(long id, String text) {
        Query query = em.createQuery("update Comment c " +
                "set c.text = :text " +
                "where c.id = :id");
        query.setParameter("text", text);
        query.setParameter("id", id);
        if(query.executeUpdate()==0) throw new NoSuchCommentException();
    }

    @Override
    public void delete(long id) {
        Query query = em.createQuery("delete " +
                "from Comment c " +
                "where c.id = :id");
        query.setParameter("id", id);
        if(query.executeUpdate()==0) throw new NoSuchCommentException();
    }
}
