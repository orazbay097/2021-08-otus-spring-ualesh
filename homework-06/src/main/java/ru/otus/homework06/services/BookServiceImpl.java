package ru.otus.homework06.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework06.exceptions.NoSuchBookException;
import ru.otus.homework06.models.Book;
import ru.otus.homework06.models.Comment;
import ru.otus.homework06.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    @Override
    public List<Book> getAll() {
        return this.bookRepository.getAll();
    }

    @Override
    public Optional<Book> getById(long id) {
        return this.bookRepository.getById(id);
    }

    @Transactional
    @Override
    public Book save(Book book) {
        return this.bookRepository.save(book);
    }

    @Transactional
    @Override
    public void setName(long id, String name) {
        val optionalBook = this.bookRepository.getById(id);
        val book = optionalBook.orElseThrow(NoSuchBookException::new);
        this.bookRepository.save(book);
    }

    @Transactional
    @Override
    public void setAuthor(long bookId, long authorId) {
        this.bookRepository.setAuthor(bookId, authorId);
    }

    @Transactional
    @Override
    public void setGenres(long id, long... genreIds) {
        this.bookRepository.setGenres(id, genreIds);
    }

    @Override
    public List<Comment> getCommentsByBook(long bookId) {
        return this.bookRepository.getById(bookId).get().getComments();
    }

    @Transactional
    @Override
    public void addComment(long id, String commentText) throws NoSuchBookException {
        this.bookRepository.addComment(id, commentText);
    }

    @Transactional
    @Override
    public void clearComments(long id) throws NoSuchBookException {
        this.bookRepository.clearComments(id);
    }

    @Transactional
    @Override
    public void delete(long id) {
        this.bookRepository.delete(id);
    }
}
