package ru.otus.homework08.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework08.exceptions.NoSuchAuthorException;
import ru.otus.homework08.models.Author;
import ru.otus.homework08.repositories.AuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;

    @Override
    public Author findById(String id) {
        return authorRepository.findById(id).orElseThrow(NoSuchAuthorException::new);
    }

    @Override
    public List<Author> getAllAuthors() {
        return this.authorRepository.findAll();
    }
}
