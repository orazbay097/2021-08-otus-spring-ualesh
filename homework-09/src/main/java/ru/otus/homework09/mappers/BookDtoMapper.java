package ru.otus.homework09.mappers;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import ru.otus.homework09.dto.BookDto;
import ru.otus.homework09.models.Book;
import ru.otus.homework09.models.Genre;
import ru.otus.homework09.services.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BookDtoMapper implements DtoMapper<Book, BookDto> {
    private final AuthorDtoMapper authorDtoMapper;
    private final GenreDtoMapper genreDtoMapper;
    private final GenreService genreService;

    @Override
    public BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getName(),
                authorDtoMapper.toDto(book.getAuthor()),
                book.getGenres().stream().map(genreDtoMapper::toDto).collect(Collectors.toList()),
                book.getGenres().stream().mapToLong(Genre::getId).boxed().collect(Collectors.toList())
        );
    }

    @Override
    public Book fromDto(BookDto bookDto) {
        val genres = bookDto.
                getGenreIds().
                stream().
                map(
                        gId ->
                                genreDtoMapper.fromDto(
                                        genreService.findById(gId))
                )
                .collect(Collectors.toList());
        return new Book(
                bookDto.getId(),
                bookDto.getName(),
                authorDtoMapper.fromDto(bookDto.getAuthor()),
                genres,
                List.of()
        );
    }
}
