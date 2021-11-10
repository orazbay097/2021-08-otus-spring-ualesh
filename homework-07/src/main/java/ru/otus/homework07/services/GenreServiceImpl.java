package ru.otus.homework07.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework07.exceptions.NoSuchGenreException;
import ru.otus.homework07.models.Genre;
import ru.otus.homework07.repositories.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService{
    private final GenreRepository genreRepository;

    @Override
    public List<Genre> findAll() {
        return this.genreRepository.findAll();
    }

    @Override
    public Genre findById(long id) {
        return this.genreRepository.findById(id).orElseThrow(NoSuchGenreException::new);
    }
}
