package ru.otus.homework06.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToOne(targetEntity = Author.class)
    private Author author;
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
    @ManyToMany(targetEntity = Genre.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "book_genres", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
    @OneToMany(targetEntity = Comment.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "book_comments", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<Comment> comments;
}