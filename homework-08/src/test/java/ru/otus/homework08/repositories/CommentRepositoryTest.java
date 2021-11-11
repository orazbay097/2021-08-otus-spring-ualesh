package ru.otus.homework08.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework08.models.Book;
import ru.otus.homework08.models.Comment;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class CommentRepositoryTest extends AbstractRepositoryTest{
    @Autowired
    private CommentRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName("creates new comment")
    @Test
    void save() {
        val text = "some text";
        val comment = this.repository.save(new Comment(null, text));
        assertThat(this.mongoTemplate.findById(comment.getId(), Comment.class).getText()).isEqualTo(text);
    }

    @DisplayName("gets all comments")
    @Test
    void getAll() {
        val texts = List.of("c1", "c2", "c3", "c4", "c5");
        texts.forEach(text->{
            mongoTemplate.save(new Comment(null, text));
        });
        val comments = this.mongoTemplate.findAll(Comment.class);
        assertThat(comments.stream().map(Comment::getText).collect(Collectors.toList())).isEqualTo(texts);
    }

    @Test
    @DisplayName("should be removed from book when deleted")
    void shouldBeRemovedFromBookWhenDeleted() {
        val comment = mongoTemplate.save(new Comment(null, "samsink"));
        val book = mongoTemplate.findAll(Book.class).get(0);
        book.setComments(List.of(comment));
        mongoTemplate.save(book);
        repository.delete(comment);
        assertThat(mongoTemplate.findAll(Book.class).get(0).getComments()).isEmpty();
    }
}