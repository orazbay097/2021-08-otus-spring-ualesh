package ru.otus.homework09.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework09.models.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}
