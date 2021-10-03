package ru.otus.homework05.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework05.models.Genre;
import ru.otus.homework05.repositories.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService{
    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getAllGenres() {
        return this.genreRepository.getAllGenres();
    }
}
