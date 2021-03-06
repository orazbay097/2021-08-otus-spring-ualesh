package ru.otus.homework08.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework08.models.Genre;
import java.util.List;
import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, String> {
    Optional<Genre> findById(String id);
    List<Genre> findAll();
}
