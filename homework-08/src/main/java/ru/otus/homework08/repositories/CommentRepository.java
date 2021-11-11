package ru.otus.homework08.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework08.models.Comment;


public interface CommentRepository extends CrudRepository<Comment, String> {
}
