package ru.otus.homework09.services;

import ru.otus.homework09.dto.GenreDto;
import java.util.List;

public interface GenreService {
    GenreDto findById(long id);
    List<GenreDto> findAll();
}
