package ru.otus.homework06.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework06.exceptions.NoSuchBookException;
import ru.otus.homework06.models.Book;
import ru.otus.homework06.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll() {
        return this.bookRepository.getAll();
    }

    @Transactional(readOnly = true)
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
        this.bookRepository.setName(id,name);
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
