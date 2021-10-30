package ru.otus.homework07.services;

import ru.otus.homework07.models.Genre;
import java.util.List;

public interface GenreService {
    Genre findById(long id);
    List<Genre> findAll();
}
