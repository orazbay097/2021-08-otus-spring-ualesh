package ru.otus.homework07.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework07.exceptions.NoSuchBookException;
import ru.otus.homework07.models.Book;
import ru.otus.homework07.models.Comment;
import ru.otus.homework07.repositories.BookRepository;

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

    @Override
    public Iterable<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Book findById(long id) {
        return this.bookRepository.findById(id).orElseThrow(NoSuchBookException::new);
    }

    @Override
    public Book save(Book book) {
        return this.bookRepository.save(book);
    }

    @Override
    public void setName(long id, String name) {
        val book = this.findById(id);
        book.setName(name);
        this.bookRepository.save(book);
    }

    @Override
    public void setAuthor(long bookId, long authorId) {
        val book = this.findById(bookId);
        val author = this.authorService.findById(authorId);
        book.setAuthor(author);
        this.bookRepository.save(book);
    }

    @Override
    public void setGenres(long id, long... genreIds) {
        val book = this.findById(id);
        val genres = Arrays.stream(genreIds).mapToObj(this.genreService::findById).collect(Collectors.toList());
        book.setGenres(genres);
        this.bookRepository.save(book);
    }

    @Override
    public List<Comment> getCommentsByBook(long bookId) {
        return this.findById(bookId).getComments();
    }

    @Transactional
    @Override
    public void addComment(long id, String commentText) throws NoSuchBookException {
        val book = this.findById(id);
        book.getComments().add(commentService.save(new Comment(0, commentText)));
        this.bookRepository.save(book);
    }

    @Transactional
    @Override
    public void clearComments(long id) {
        val book = this.findById(id);
        book.setComments(List.of());
        this.bookRepository.save(book);
    }

    @Override
    public void delete(long id) {
        this.bookRepository.delete(this.findById(id));
    }
}
