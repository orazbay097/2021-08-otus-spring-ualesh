package ru.otus.homework08.services;

import ru.otus.homework08.models.Author;

import java.util.List;

public interface AuthorService {
    Author findById(String id);
    List<Author> getAllAuthors();
}
