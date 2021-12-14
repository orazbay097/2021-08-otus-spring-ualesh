package ru.otus.homework09.rest;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.homework09.services.AuthorService;

@RequiredArgsConstructor
@Controller
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping({ "/authors"})
    public String listPage(Model model) {
        val authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "authors_list";
    }
}
