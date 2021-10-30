package ru.otus.homework07.repositories;

import org.springframework.data.repository.Repository;
import ru.otus.homework07.models.Genre;
import java.util.List;
import java.util.Optional;

public interface GenreRepository extends Repository<Genre, Long> {
    Optional<Genre> findById(long id);
    List<Genre> findAll();
}
