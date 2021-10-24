package ru.otus.homework06.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework06.models.Genre;
import ru.otus.homework06.repositories.GenreRepository;

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
