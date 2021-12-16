package ru.otus.homework09.repositories;

import org.springframework.data.repository.Repository;
import ru.otus.homework09.models.Author;
import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends Repository<Author, Long> {
    Optional<Author> findById(long id);
    List<Author> findAll();
}
