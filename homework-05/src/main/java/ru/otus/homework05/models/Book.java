package ru.otus.homework05.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    private long id;
    private final String name;
    private final Author author;
    private final List<Genre> genres;
}
