package ru.otus.homework07.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework07.exceptions.NoSuchAuthorException;
import ru.otus.homework07.models.Author;
import ru.otus.homework07.repositories.AuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;

    @Override
    public Author findById(long id) {
        return authorRepository.findById(id).orElseThrow(NoSuchAuthorException::new);
    }

    @Override
    public List<Author> getAllAuthors() {
        return this.authorRepository.findAll();
    }
}
