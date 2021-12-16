package ru.otus.homework09.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDto {
    private long id;
    private String name;
    private AuthorDto author;
    private List<GenreDto> genres;
    private List<Long> genreIds;
}
