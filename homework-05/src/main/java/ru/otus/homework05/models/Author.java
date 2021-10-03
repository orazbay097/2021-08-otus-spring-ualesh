package ru.otus.homework05.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Author {
    private long id;
    private final String surname;
    private final String name;
}
