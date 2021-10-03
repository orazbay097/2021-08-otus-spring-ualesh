package ru.otus.homework05.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework05.exceptions.NoSuchAuthorException;
import ru.otus.homework05.exceptions.NoSuchBookException;
import ru.otus.homework05.exceptions.NoSuchGenreException;
import ru.otus.homework05.models.Author;
import ru.otus.homework05.models.Book;
import ru.otus.homework05.models.Genre;
import ru.otus.homework05.services.AuthorService;
import ru.otus.homework05.services.BookService;
import ru.otus.homework05.services.GenreService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class LibraryCommands {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    private final static String MESSAGE_NO_SUCH_BOOK = "No such book";
    private final static String MESSAGE_NO_SUCH_AUTHOR = "No such author";
    private final static String MESSAGE_NO_SUCH_GENRE = "No such genre";

    @ShellMethod(value = "Get all books", key = {"books"})
    public List<Book> getAllBooks() {
        return this.bookService.getAll();
    }

    @ShellMethod(value = "Get book by id", key = {"book"})
    public Book getBookById(@ShellOption({"book id"}) long bookId) {
        return this.bookService.getById(bookId);
    }

    @ShellMethod(value = "Create book", key = {"create book"})
    public String createBook(
            @ShellOption({"book name"}) String bookName,
            @ShellOption({"author id"}) long authorId,
            @ShellOption({"genre ids"}) String genreIds
    ) {
        try {
            return String.valueOf(this.bookService.create(
                    new Book(
                            bookName,
                            new Author(authorId, null, null),
                            Arrays.stream(splitGenres(genreIds)).mapToObj(g -> new Genre(g, null))
                            .collect(Collectors.toList())
                    )
            ));
        } catch (NoSuchGenreException e) {
            return MESSAGE_NO_SUCH_GENRE;
        } catch (NoSuchAuthorException e) {
            return MESSAGE_NO_SUCH_AUTHOR;
        }
    }

    @ShellMethod(value = "Change name of book", key = {"change book name"})
    public String setNameOfBook(
            @ShellOption({"book id"}) long bookId,
            @ShellOption({"book name"}) String bookName
    ) {
        try {
            this.bookService.setName(bookId, bookName);
            return "Book name changed";
        }catch (NoSuchBookException e){
            return MESSAGE_NO_SUCH_BOOK;
        }
    }

    @ShellMethod(value = "Change author of book", key = {"change book author"})
    public String setAuthorOfBook(
            @ShellOption({"book id"}) long bookId,
            @ShellOption({"author id"}) long authorId
    ) {
        try {
            this.bookService.setAuthor(bookId, authorId);
            return "Book author changed";
        }catch (NoSuchBookException e){
            return MESSAGE_NO_SUCH_BOOK;
        } catch (NoSuchAuthorException e) {
            return MESSAGE_NO_SUCH_AUTHOR;
        }
    }

    @ShellMethod(value = "Change genres of book", key = {"change book genres"})
    public String setGenresOfBook(
            @ShellOption({"book id"}) long bookId,
            @ShellOption({"genre ids"}) String genreIds
    ) {
        try {
            this.bookService.setGenres(bookId, this.splitGenres(genreIds));
            return "Book genres changed";
        }catch (NoSuchBookException e){
            return MESSAGE_NO_SUCH_BOOK;
        } catch (NoSuchGenreException e) {
            return MESSAGE_NO_SUCH_GENRE;
        }
    }

    @ShellMethod(value = "Delete book", key = {"delete book"})
    public String deleteBook(
            @ShellOption({"book id"}) long bookId
    ) {
        try {
            this.bookService.delete(bookId);
            return "Book deleted";
        }catch (NoSuchBookException e){
            return MESSAGE_NO_SUCH_BOOK;
        } catch (NoSuchGenreException e) {
            return MESSAGE_NO_SUCH_GENRE;
        }
    }
    @ShellMethod(value = "Get all authors", key = {"authors"})
    public List<Author> getAllAuthors() {
        return this.authorService.getAllAuthors();
    }

    @ShellMethod(value = "Get all genres", key = {"genres"})
    public List<Genre> getAllGenres() {
        return this.genreService.getAllGenres();
    }

    private long [] splitGenres(String genres) {
        return Arrays.stream(genres.split(" ")).mapToLong(Long::parseLong).toArray();
    }

}
