package ru.otus.homework08.services;

import ru.otus.homework08.models.Genre;
import java.util.List;

public interface GenreService {
    Genre findById(String id);
    List<Genre> findAll();
}
