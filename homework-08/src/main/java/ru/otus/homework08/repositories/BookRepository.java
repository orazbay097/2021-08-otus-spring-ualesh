package ru.otus.homework08.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework08.models.Book;

public interface BookRepository extends CrudRepository<Book, String>, BookRepositoryCustom {
}
