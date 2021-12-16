package ru.otus.homework09.rest;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.homework09.services.GenreService;

@RequiredArgsConstructor
@Controller
public class GenreController {
    private final GenreService genreService;

    @GetMapping({ "/genres"})
    public String listPage(Model model) {
        val genres = genreService.findAll();
        model.addAttribute("genres", genres);
        return "genres_list";
    }
}
