package ru.otus.homework09.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework09.models.Comment;


public interface CommentRepository extends CrudRepository<Comment, Long> {
}
