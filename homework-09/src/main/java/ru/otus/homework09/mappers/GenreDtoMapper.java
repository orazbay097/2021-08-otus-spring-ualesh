package ru.otus.homework09.mappers;

import org.springframework.stereotype.Component;
import ru.otus.homework09.dto.GenreDto;
import ru.otus.homework09.models.Genre;

@Component
public class GenreDtoMapper implements DtoMapper<Genre, GenreDto> {
    @Override
    public GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

    @Override
    public Genre fromDto(GenreDto genreDto) {
        return new Genre(genreDto.getId(), genreDto.getName());
    }
}
