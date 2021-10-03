package ru.otus.homework05.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework05.models.Book;
import ru.otus.homework05.repositories.BookRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    @Override
    public List<Book> getAll() {
        return this.bookRepository.getAll();
    }

    @Override
    public Book getById(long id) {
        return this.bookRepository.getById(id);
    }

    @Override
    public long create(Book book) {
        return this.bookRepository.create(book);
    }

    @Override
    public void setName(long id, String name) {
        this.bookRepository.setName(id,name);
    }

    @Override
    public void setAuthor(long bookId, long authorId) {
        this.bookRepository.setAuthor(bookId, authorId);
    }

    @Override
    public void setGenres(long id, long... genreIds) {
        this.bookRepository.setGenres(id, genreIds);
    }

    @Override
    public void delete(long id) {
        this.bookRepository.delete(id);
    }
}
