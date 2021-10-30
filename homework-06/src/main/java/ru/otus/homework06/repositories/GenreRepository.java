package ru.otus.homework06.repositories;

import ru.otus.homework06.models.Genre;
import java.util.List;

public interface GenreRepository {
    List<Genre> getAllGenres();
}
