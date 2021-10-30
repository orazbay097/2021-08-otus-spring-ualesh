package ru.otus.homework06.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework06.exceptions.NoSuchAuthorException;
import ru.otus.homework06.exceptions.NoSuchBookException;
import ru.otus.homework06.exceptions.NoSuchCommentException;
import ru.otus.homework06.exceptions.NoSuchGenreException;
import ru.otus.homework06.models.Author;
import ru.otus.homework06.models.Book;
import ru.otus.homework06.models.Comment;
import ru.otus.homework06.models.Genre;
import ru.otus.homework06.services.AuthorService;
import ru.otus.homework06.services.BookService;
import ru.otus.homework06.services.CommentService;
import ru.otus.homework06.services.GenreService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class LibraryCommands {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    private final static String MESSAGE_NO_SUCH_BOOK = "No such book";
    private final static String MESSAGE_NO_SUCH_AUTHOR = "No such author";
    private final static String MESSAGE_NO_SUCH_GENRE = "No such genre";
    private final static String MESSAGE_NO_SUCH_COMMENT = "No such comment";

    @Transactional
    @ShellMethod(value = "Get all books", key = {"books"})
    public List<Book> getAllBooks() {
        return this.bookService.getAll();
    }

    @ShellMethod(value = "Get book by id", key = {"book"})
    public Book getBookById(@ShellOption({"book id"}) long bookId) {
        return this.bookService.getById(bookId).get();
    }

    @ShellMethod(value = "Create book", key = {"create book"})
    public String createBook(
            @ShellOption({"book name"}) String bookName,
            @ShellOption({"author id"}) long authorId,
            @ShellOption({"genre ids"}) String genreIds
    ) {
        try {
            return String.valueOf(this.bookService.save(
                    new Book(
                            0,
                            bookName,
                            new Author(authorId, null, null),
                            Arrays.stream(splitGenres(genreIds)).mapToObj(g -> new Genre(g, null))
                                    .collect(Collectors.toList()), null
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
        } catch (NoSuchBookException e) {
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
        } catch (NoSuchBookException e) {
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
        } catch (NoSuchBookException e) {
            return MESSAGE_NO_SUCH_BOOK;
        } catch (NoSuchGenreException e) {
            return MESSAGE_NO_SUCH_GENRE;
        }
    }

    @ShellMethod(value = "Get comments of books", key = {"book comments"})
    public List<Comment> getCommentsOfBook(
            @ShellOption({"book id"}) long bookId
            ) {
        return this.bookService.getCommentsByBook(bookId);
    }

    @ShellMethod(value = "Add comment to book", key = {"add book comment"})
    public String addCommentToBook(
            @ShellOption({"book id"}) long bookId,
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
            @ShellOption({"book id"}) long bookId
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
            @ShellOption({"book id"}) long bookId
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
    public List<Author> getAllAuthors() {
        return this.authorService.getAllAuthors();
    }

    @ShellMethod(value = "Get all genres", key = {"genres"})
    public List<Genre> getAllGenres() {
        return this.genreService.getAllGenres();
    }

    @ShellMethod(value = "Get all comments", key = {"comments"})
    public List<Comment> getAllComments() {
        return this.commentService.getAll();
    }

    @ShellMethod(value = "Set comment text", key = {"update comment"})
    public String setCommentText(
            @ShellOption({"comment id"}) long commentId,
            @ShellOption({"new comment text"}) String commentText
    ) {
        try {
            this.commentService.setText(commentId, commentText);
            return "Comment updated";
        } catch (NoSuchCommentException ex) {
            return MESSAGE_NO_SUCH_COMMENT;
        }
    }

    @ShellMethod(value = "Delete comment", key = {"delete comment"})
    public String deleteComment(
            @ShellOption({"comment id"}) long commentId
    ) {
        try {
            this.commentService.delete(commentId);
            return "Comment deleted";
        } catch (NoSuchCommentException ex) {
            return MESSAGE_NO_SUCH_COMMENT;
        }
    }

    private long[] splitGenres(String genres) {
        return Arrays.stream(genres.split(" ")).mapToLong(Long::parseLong).toArray();
    }

}
