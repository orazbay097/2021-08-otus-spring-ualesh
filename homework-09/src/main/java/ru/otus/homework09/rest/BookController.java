package ru.otus.homework09.rest;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework09.dto.BookDto;
import ru.otus.homework09.services.AuthorService;
import ru.otus.homework09.services.BookService;
import ru.otus.homework09.services.GenreService;

@RequiredArgsConstructor
@Controller
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping({"/", "/books"})
    public String listPage(Model model) {
        val books = bookService.findAll();
        model.addAttribute("books", books);
        return "books_list";
    }

    @GetMapping("/books/form")
    public String formPage(@RequestParam(name = "id",required = false) Long id, Model model) {
        val book = id == null ? new BookDto() : bookService.findById(id);
        val authors = authorService.findAll();
        val genres = genreService.findAll();
        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "book_form";
    }

    @PostMapping("/books/save")
    public String saveBook(
            BookDto book
    ) {
        bookService.save(book);
        return "redirect:/books";
    }

    @PostMapping("/books/delete")
    public String deleteBook(
            @RequestParam("id") long id
    ) {
        bookService.delete(id);
        return "redirect:/books";
    }
}
