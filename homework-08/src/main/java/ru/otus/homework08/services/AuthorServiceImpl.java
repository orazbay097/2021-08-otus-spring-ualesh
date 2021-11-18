package ru.otus.homework08.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.otus.homework08.exceptions.NoSuchAuthorException;
import ru.otus.homework08.models.Author;
import ru.otus.homework08.repositories.AuthorRepository;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;

    @Override
    public Author findById(String id) {
        return authorRepository.findById(id).orElseThrow(NoSuchAuthorException::new);
    }

    @Override
    public Iterable<Author> getAllAuthors() {
        return this.authorRepository.findAll();
    }

    @Override
    public void setSurname(String id, String surname) {
        val author = this.findById(id);
        author.setSurname(surname);
        authorRepository.save(author);
    }

    @Override
    public void setName(String id, String name) {
        val author = this.findById(id);
        author.setName(name);
        authorRepository.save(author);
    }
}
