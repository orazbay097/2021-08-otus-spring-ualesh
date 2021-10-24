package ru.otus.homework06.repositories;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;
import ru.otus.homework06.exceptions.NoSuchAuthorException;
import ru.otus.homework06.exceptions.NoSuchBookException;
import ru.otus.homework06.exceptions.NoSuchGenreException;
import ru.otus.homework06.models.Author;
import ru.otus.homework06.models.Book;
import ru.otus.homework06.models.Comment;
import ru.otus.homework06.models.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Book> getAll() {
        val query = this.em.createQuery("select b from Book b join fetch b.author", Book.class);
        return query.getResultList();
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public Book save(Book book) {
        try {
            if (book.getId() <= 0) {
                if (book.getGenres() != null)
                    IntStream.range(0, book.getGenres().size())
                            .forEach(
                                    index -> book.getGenres().set(
                                            index,
                                            Optional.ofNullable(
                                                    this.em.find(Genre.class, book.getGenres().get(index).getId())
                                            ).orElseThrow(NoSuchGenreException::new)
                                    )
                            );
                em.persist(book);
                return book;
            } else {
                return em.merge(book);
            }
        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) throw new NoSuchAuthorException(ex);
            else throw new NoSuchGenreException(ex);
        }
    }

    @Override
    public void setAuthor(long bookId, long authorId) {
        val optionalBook = this.getById(bookId);
        if (optionalBook.isEmpty()) throw new NoSuchBookException();

        val book = optionalBook.get();
        book.setAuthor(new Author(authorId, null, null));
        this.save(book);
    }

    @Override
    public void setGenres(long id, long... genreIds) {
        try {
            val optionalBook = this.getById(id);
            if (optionalBook.isEmpty()) throw new NoSuchBookException();

            val genres = Arrays.stream(genreIds).mapToObj(
                    genreId -> new Genre(genreId, null)
            ).collect(Collectors.toList());

            val book = optionalBook.get();
            book.setGenres(genres);
            this.save(book);
        } catch (RuntimeException ex) {
            throw new NoSuchGenreException(ex);
        }
    }

    @Override
    public void addComment(long id, String commentText) throws NoSuchBookException {
        val optionalBook = this.getById(id);
        if (optionalBook.isEmpty()) throw new NoSuchBookException();

        val book = optionalBook.get();
        book.getComments().add(new Comment(0, commentText));
        this.save(book);
    }

    @Override
    public void clearComments(long id) throws NoSuchBookException {
        val optionalBook = this.getById(id);
        if (optionalBook.isEmpty()) throw new NoSuchBookException();

        val book = optionalBook.get();
        book.setComments(new ArrayList<>());
        this.save(book);
    }

    @Override
    public void delete(long id) {
        val book = this.getById(id).orElseThrow(NoSuchBookException::new);
        em.remove(book);
    }
}