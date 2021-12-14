package ru.otus.homework09.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.otus.homework09.dto.AuthorDto;
import ru.otus.homework09.exceptions.NoSuchAuthorException;
import ru.otus.homework09.mappers.AuthorDtoMapper;
import ru.otus.homework09.models.Author;
import ru.otus.homework09.repositories.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;
    private final AuthorDtoMapper authorDtoMapper;

    @Override
    public AuthorDto findById(long id) {
        val author = authorRepository.findById(id).orElseThrow(NoSuchAuthorException::new);
        return authorDtoMapper.toDto(author);
    }

    @Override
    public List<AuthorDto> findAll() {
        return this.authorRepository.findAll().stream().map(authorDtoMapper::toDto).collect(Collectors.toList());
    }
}
