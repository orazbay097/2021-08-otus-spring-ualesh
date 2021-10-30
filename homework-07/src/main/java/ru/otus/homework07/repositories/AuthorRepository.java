package ru.otus.homework07.repositories;

import org.springframework.data.repository.Repository;
import ru.otus.homework07.models.Author;
import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends Repository<Author, Long> {
    Optional<Author> findById(long id);
    List<Author> findAll();
}
