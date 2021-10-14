package ru.otus.homework06.repositories;

import ru.otus.homework06.models.Author;
import java.util.List;

public interface AuthorRepository {
    List<Author> getAllAuthors();
}
