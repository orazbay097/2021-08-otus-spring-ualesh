package ru.otus.homework07.services;

import ru.otus.homework07.models.Author;

import java.util.List;

public interface AuthorService {
    Author findById(long id);
    List<Author> getAllAuthors();
}
