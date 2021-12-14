package ru.otus.homework09.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework09.dto.BookDto;
import ru.otus.homework09.exceptions.NoSuchBookException;
import ru.otus.homework09.mappers.AuthorDtoMapper;
import ru.otus.homework09.mappers.BookDtoMapper;
import ru.otus.homework09.mappers.GenreDtoMapper;
import ru.otus.homework09.models.Book;
import ru.otus.homework09.models.Comment;
import ru.otus.homework09.repositories.BookRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;
    private final BookDtoMapper bookDtoMapper;
    private final AuthorDtoMapper authorDtoMapper;
    private final GenreDtoMapper genreDtoMapper;

    @Override
    public Iterable<BookDto> findAll() {
        val res = new ArrayList<BookDto>();
        this.bookRepository.findAll().forEach(book -> res.add(bookDtoMapper.toDto(book)));
        return res;
    }

    @Override
    public BookDto findById(long id) {
        return bookDtoMapper.toDto(this.findByIdInternal(id));
    }

    @Override
    public BookDto save(BookDto bookDto) {
        val book = this.bookRepository.save(bookDtoMapper.fromDto(bookDto));
        return bookDtoMapper.toDto(book);
    }

    @Override
    public void setName(long id, String name) {
        val book = this.findByIdInternal(id);
        book.setName(name);
        this.bookRepository.save(book);
    }

    @Override
    public void setAuthor(long bookId, long authorId) {
        val book = this.findByIdInternal(bookId);
        val author = authorDtoMapper.fromDto(this.authorService.findById(authorId));
        book.setAuthor(author);
        this.bookRepository.save(book);
    }

    @Override
    public void setGenres(long id, long... genreIds) {
        val book = this.findByIdInternal(id);
        val genres = Arrays.stream(genreIds).mapToObj(gId->genreDtoMapper.fromDto(genreService.findById(gId))).collect(Collectors.toList());
        book.setGenres(genres);
        this.bookRepository.save(book);
    }

    @Override
    public List<Comment> getCommentsByBook(long bookId) {
        return this.findByIdInternal(bookId).getComments();
    }

    @Transactional
    @Override
    public void addComment(long id, String commentText) throws NoSuchBookException {
        val book = this.findByIdInternal(id);
        book.getComments().add(commentService.save(new Comment(0, commentText)));
        this.bookRepository.save(book);
    }

    @Transactional
    @Override
    public void clearComments(long id) {
        val book = this.findByIdInternal(id);
        book.setComments(List.of());
        this.bookRepository.save(book);
    }

    @Override
    public void delete(long id) {
        this.bookRepository.delete(this.findByIdInternal(id));
    }

    private Book findByIdInternal(long id) {
        return this.bookRepository.findById(id).orElseThrow(NoSuchBookException::new);
    }

}
