package ru.otus.homework07.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework07.models.Comment;


public interface CommentRepository extends CrudRepository<Comment, Long> {
}
