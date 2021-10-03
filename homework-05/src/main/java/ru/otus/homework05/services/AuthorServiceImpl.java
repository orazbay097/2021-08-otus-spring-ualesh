package ru.otus.homework05.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework05.models.Author;
import ru.otus.homework05.repositories.AuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;

    @Override
    public List<Author> getAllAuthors() {
        return this.authorRepository.getAllAuthors();
    }
}
