package ru.otus.homework08.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document
public class Book {
    @Id
    private String id;

    private String name;

    @DBRef
    private Author author;

    private List<Genre> genres;

    private List<BookComment> comments;

    @Override
    public String toString() {
        return String.format("Book(id=%s, name=%s, author=%s, genres=%s)", this.id, this.name, this.author == null ? "null" : author.toString(), this.genres == null ? "null" : this.genres.toString());
    }
}