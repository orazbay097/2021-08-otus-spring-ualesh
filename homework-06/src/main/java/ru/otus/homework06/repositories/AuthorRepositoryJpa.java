package ru.otus.homework06.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework06.models.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJpa implements AuthorRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Author> getAllAuthors() {
        return this.em.createQuery("select a from Author a", Author.class).getResultList();
    }
}
