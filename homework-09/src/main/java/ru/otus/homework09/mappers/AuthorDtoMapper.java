package ru.otus.homework09.mappers;

import org.springframework.stereotype.Component;
import ru.otus.homework09.dto.AuthorDto;
import ru.otus.homework09.models.Author;

@Component
public class AuthorDtoMapper implements DtoMapper<Author, AuthorDto>{
    @Override
    public AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getSurname(), author.getName());
    }

    @Override
    public Author fromDto(AuthorDto authorDto) {
        return new Author(authorDto.getId(), authorDto.getSurname(), authorDto.getName());
    }
}
