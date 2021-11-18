package ru.otus.homework08.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework08.models.Author;

public interface AuthorRepository extends CrudRepository<Author, String> {
}
