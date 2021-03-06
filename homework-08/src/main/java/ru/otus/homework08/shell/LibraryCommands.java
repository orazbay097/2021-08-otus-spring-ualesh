package ru.otus.homework08.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework08.exceptions.NoSuchAuthorException;
import ru.otus.homework08.exceptions.NoSuchBookException;
import ru.otus.homework08.exceptions.NoSuchCommentException;
import ru.otus.homework08.exceptions.NoSuchGenreException;
import ru.otus.homework08.models.Author;
import ru.otus.homework08.models.Book;
import ru.otus.homework08.models.BookComment;
import ru.otus.homework08.models.Genre;
import ru.otus.homework08.services.AuthorService;
import ru.otus.homework08.services.BookService;
import ru.otus.homework08.services.GenreService;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class LibraryCommands {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    private final static String MESSAGE_NO_SUCH_BOOK = "No such book";
    private final static String MESSAGE_NO_SUCH_AUTHOR = "No such author";
    private final static String MESSAGE_NO_SUCH_GENRE = "No such genre";
    private final static String MESSAGE_NO_SUCH_COMMENT = "No such comment";

    @Transactional
    @ShellMethod(value = "Get all books", key = {"books"})
    public Iterable<Book> getAllBooks() {
        return this.bookService.findAll();
    }

    @ShellMethod(value = "Get book by id", key = {"book"})
    public Book getBookById(@ShellOption({"book id"}) String bookId) {
        return this.bookService.findById(bookId);
    }

    @ShellMethod(value = "Create book", key = {"create book"})
    public String createBook(
            @ShellOption({"book name"}) String bookName,
            @ShellOption({"author id"}) String authorId,
            @ShellOption({"genre ids"}) String genreIds
    ) {
        try {
            return String.valueOf(this.bookService.save(
                    bookName,
                    authorId,
                    splitGenres(genreIds)

            ));
        } catch (NoSuchGenreException e) {
            return MESSAGE_NO_SUCH_GENRE;
        } catch (NoSuchAuthorException e) {
            return MESSAGE_NO_SUCH_AUTHOR;
        }
    }

    @ShellMethod(value = "Change name of book", key = {"change book name"})
    public String setNameOfBook(
            @ShellOption({"book id"}) String bookId,
            @ShellOption({"book name"}) String bookName
    ) {
        try {
            this.bookService.setName(bookId, bookName);
            return "Book name changed";
        } catch (NoSuchBookException e) {
            return MESSAGE_NO_SUCH_BOOK;
        }
    }

    @ShellMethod(value = "Change author of book", key = {"change book author"})
    public String setAuthorOfBook(
            @ShellOption({"book id"}) String bookId,
            @ShellOption({"author id"}) String authorId
    ) {
        try {
            this.bookService.setAuthor(bookId, authorId);
            return "Book author changed";
        } catch (NoSuchBookException e) {
            return MESSAGE_NO_SUCH_BOOK;
        } catch (NoSuchAuthorException e) {
            return MESSAGE_NO_SUCH_AUTHOR;
        }
    }

    @ShellMethod(value = "Change genres of book", key = {"change book genres"})
    public String setGenresOfBook(
            @ShellOption({"book id"}) String bookId,
            @ShellOption({"genre ids"}) String genreIds
    ) {
        try {
            this.bookService.setGenres(bookId, this.splitGenres(genreIds));
            return "Book genres changed";
        } catch (NoSuchBookException e) {
            return MESSAGE_NO_SUCH_BOOK;
        } catch (NoSuchGenreException e) {
            return MESSAGE_NO_SUCH_GENRE;
        }
    }

    @ShellMethod(value = "Get comments of books", key = {"book comments"})
    public List<BookComment> getCommentsOfBook(
            @ShellOption({"book id"}) String bookId
    ) {
        return this.bookService.getCommentsByBook(bookId);
    }

    @ShellMethod(value = "Add comment to book", key = {"add book comment"})
    public String addCommentToBook(
            @ShellOption({"book id"}) String bookId,
            @ShellOption({"comment"}) String commentText
    ) {
        try {
            this.bookService.addComment(bookId, commentText);
            return "Comment added to book";
        } catch (NoSuchBookException e) {
            return MESSAGE_NO_SUCH_BOOK;
        }
    }

    @ShellMethod(value = "Clear comments of book", key = {"clear book comments"})
    public String clearCommentsOfBook(
            @ShellOption({"book id"}) String bookId
    ) {
        try {
            this.bookService.clearComments(bookId);
            return "Comments of book cleared";
        } catch (NoSuchBookException e) {
            return MESSAGE_NO_SUCH_BOOK;
        }
    }

    @ShellMethod(value = "Delete book", key = {"delete book"})
    public String deleteBook(
            @ShellOption({"book id"}) String bookId
    ) {
        try {
            this.bookService.delete(bookId);
            return "Book deleted";
        } catch (NoSuchBookException e) {
            return MESSAGE_NO_SUCH_BOOK;
        } catch (NoSuchGenreException e) {
            return MESSAGE_NO_SUCH_GENRE;
        }
    }

    @ShellMethod(value = "Get all authors", key = {"authors"})
    public Iterable<Author> getAllAuthors() {
        return this.authorService.getAllAuthors();
    }

    @ShellMethod(value = "Change author surname", key = {"change author surname"})
    public String setAuthorSurname(
            @ShellOption({"author id"}) String authorId,
            @ShellOption({"surname"}) String surname) {
        try {
            this.authorService.setSurname(authorId, surname);
            return "Author surname changed";
        } catch (NoSuchAuthorException ex) {
            return MESSAGE_NO_SUCH_AUTHOR;
        }
    }

    @ShellMethod(value = "Change author name", key = {"change author name"})
    public String setAuthorName(
            @ShellOption({"author id"}) String authorId,
            @ShellOption({"surname"}) String name) {
        try {
            this.authorService.setName(authorId, name);
            return "Author name changed";
        } catch (NoSuchAuthorException ex) {
            return MESSAGE_NO_SUCH_AUTHOR;
        }
    }

    @ShellMethod(value = "Get all genres", key = {"genres"})
    public List<Genre> getAllGenres() {
        return this.genreService.findAll();
    }

    @ShellMethod(value = "Set book comment text", key = {"update book comment"})
    public String setCommentText(
            @ShellOption({"book id"}) String bookId,
            @ShellOption({"comment index"}) int commentIndex,
            @ShellOption({"new comment text"}) String commentText
    ) {
        try {
            this.bookService.setCommentText(bookId, commentIndex, commentText);
            return "Comment updated";
        } catch (NoSuchBookException ex) {
            return MESSAGE_NO_SUCH_BOOK;
        } catch (NoSuchCommentException ex) {
            return MESSAGE_NO_SUCH_COMMENT;
        }
    }

    @ShellMethod(value = "Delete book comment", key = {"delete book comment"})
    public String deleteComment(
            @ShellOption({"book id"}) String bookId,
            @ShellOption({"comment index"}) int commentIndex
    ) {
        try {
            this.bookService.deleteComment(bookId, commentIndex);
            return "Comment deleted";
        } catch (NoSuchBookException ex) {
            return MESSAGE_NO_SUCH_BOOK;
        } catch (NoSuchCommentException ex) {
            return MESSAGE_NO_SUCH_COMMENT;
        }
    }

    private String[] splitGenres(String genres) {
        return genres.split(" ");
    }

}
