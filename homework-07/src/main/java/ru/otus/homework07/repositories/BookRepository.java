package ru.otus.homework07.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework07.models.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}
