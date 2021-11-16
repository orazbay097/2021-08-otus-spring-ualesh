package ru.otus.homework08.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework08.exceptions.NoSuchBookException;
import ru.otus.homework08.exceptions.NoSuchCommentException;
import ru.otus.homework08.models.Book;
import ru.otus.homework08.models.BookComment;
import ru.otus.homework08.models.Genre;
import ru.otus.homework08.repositories.BookRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public Iterable<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Book findById(String id) {
        return this.bookRepository.findById(id).orElseThrow(NoSuchBookException::new);
    }

    @Override
    public Book save(String bookName, String authorId, String... genreIds) {
        return this.bookRepository.save(
                new Book(
                        null,
                        bookName,
                        this.authorService.findById(authorId),
                        this.collectGenres(genreIds),
                        List.of()
                ));
    }

    @Override
    public void setName(String id, String name) {
        val book = this.findById(id);
        book.setName(name);
        this.bookRepository.save(book);
    }

    @Override
    public void setAuthor(String bookId, String authorId) {
        val book = this.findById(bookId);
        val author = this.authorService.findById(authorId);
        book.setAuthor(author);
        this.bookRepository.save(book);
    }

    @Override
    public void setGenres(String id, String... genreIds) {
        val book = this.findById(id);
        book.setGenres(this.collectGenres(genreIds));
        this.bookRepository.save(book);
    }

    @Override
    public List<BookComment> getCommentsByBook(String bookId) {
        return this.findById(bookId).getComments();
    }

    @Transactional
    @Override
    public void addComment(String id, String commentText) throws NoSuchBookException {
        val book = this.findById(id);
        book.getComments().add(new BookComment(commentText));
        this.bookRepository.save(book);
    }

    @Override
    public void setCommentText(String id, int commentIndex, String commentText) {
        try {
            val book = this.findById(id);
            book.getComments().get(commentIndex).setText(commentText);
            this.bookRepository.save(book);
        } catch (IndexOutOfBoundsException ex) {
            throw new NoSuchCommentException(ex);
        }
    }

    @Override
    public void deleteComment(String id, int commentIndex) {
        try {
            val book = this.findById(id);
            book.getComments().remove(commentIndex);
            this.bookRepository.save(book);
        } catch (IndexOutOfBoundsException ex) {
            throw new NoSuchCommentException(ex);
        }
    }

    @Transactional
    @Override
    public void clearComments(String id) {
        val book = this.findById(id);
        book.setComments(List.of());
        this.bookRepository.save(book);
    }

    @Override
    public void delete(String id) {
        this.bookRepository.delete(this.findById(id));
    }

    private List<Genre> collectGenres(String... genreIds) {
        return Arrays.stream(genreIds).map(this.genreService::findById).collect(Collectors.toList());
    }
}
