package ru.otus.homework06.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework06.models.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJpa implements GenreRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Genre> getAllGenres() {
        return this.em.createQuery("select g from Genre g", Genre.class).getResultList();
    }
}
