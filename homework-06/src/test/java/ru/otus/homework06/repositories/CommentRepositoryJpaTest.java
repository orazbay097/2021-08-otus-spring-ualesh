package ru.otus.homework06.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework06.models.Comment;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({CommentRepositoryJpa.class})
class CommentRepositoryJpaTest {
    private static final long FIRST_COMMENT_ID = 1;
    @Autowired
    private CommentRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("creates new comment")
    @Test
    void save() {
        val text = "some text";
        val comment = this.repositoryJpa.save(new Comment(0,text));
        assertThat(this.em.find(Comment.class, comment.getId()).getText()).isEqualTo(text);
    }

    @DisplayName("gets all comments")
    @Test
    void getAll() {
        val texts = List.of("c1", "c2", "c3", "c4","c5");
        val comments = this.em.getEntityManager().createQuery("select c from Comment c", Comment.class).getResultList();
        assertThat(comments.stream().map(Comment::getText).collect(Collectors.toList())).isEqualTo(texts);
    }

    @DisplayName("changes comment text")
    @Test
    void setText() {
        val text  = "some new text";
        this.repositoryJpa.setText(FIRST_COMMENT_ID, text);
        assertThat(this.em.find(Comment.class, FIRST_COMMENT_ID).getText()).isEqualTo(text);
    }

    @DisplayName("deletes comment")
    @Test
    void delete() {
        this.repositoryJpa.delete(FIRST_COMMENT_ID);
        assertThat(this.em.find(Comment.class, FIRST_COMMENT_ID)).isNull();
    }
}