package ru.otus.homework05.repositories;

import ru.otus.homework05.models.Genre;
import java.util.List;

public interface GenreRepository {
    List<Genre> getGenresOfBook(long bookId);
    List<Genre> getAllGenres();
}
