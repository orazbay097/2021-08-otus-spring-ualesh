package ru.otus.homework09.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.otus.homework09.dto.GenreDto;
import ru.otus.homework09.exceptions.NoSuchGenreException;
import ru.otus.homework09.mappers.GenreDtoMapper;
import ru.otus.homework09.models.Genre;
import ru.otus.homework09.repositories.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService{
    private final GenreRepository genreRepository;
    private final GenreDtoMapper genreDtoMapper;

    @Override
    public List<GenreDto> findAll() {
        return this.genreRepository.findAll().stream().map(genreDtoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public GenreDto findById(long id) {
        val genre = this.genreRepository.findById(id).orElseThrow(NoSuchGenreException::new);
        return genreDtoMapper.toDto(genre);
    }
}
