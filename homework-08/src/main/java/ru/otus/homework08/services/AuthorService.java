package ru.otus.homework08.services;

import ru.otus.homework08.models.Author;

public interface AuthorService {
    Author findById(String id);
    Iterable<Author> getAllAuthors();
    void setSurname(String id, String surname);
    void setName(String id, String name);
}
