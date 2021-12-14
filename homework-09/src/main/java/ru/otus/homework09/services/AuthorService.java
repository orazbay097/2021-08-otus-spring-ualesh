package ru.otus.homework09.services;

import ru.otus.homework09.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    AuthorDto findById(long id);
    List<AuthorDto> findAll();
}
