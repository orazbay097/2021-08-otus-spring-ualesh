package ru.otus.homework05.repositories;

import ru.otus.homework05.models.Author;
import java.util.List;

public interface AuthorRepository {
    List<Author> getAllAuthors();
}
