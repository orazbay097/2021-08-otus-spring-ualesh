package ru.otus.homework08.repositories;

import org.springframework.data.repository.Repository;
import ru.otus.homework08.models.Author;
import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends Repository<Author, String> {
    Optional<Author> findById(String id);
    List<Author> findAll();
}
