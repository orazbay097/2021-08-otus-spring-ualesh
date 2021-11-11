package ru.otus.homework08.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.homework08.models.Book;
import ru.otus.homework08.repositories.BookRepository;
import ru.otus.homework08.repositories.CommentRepository;

@Component
@RequiredArgsConstructor
public class MongoBookCascadeSaveEventsListener extends AbstractMongoEventListener<Book> {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        super.onBeforeConvert(event);
        val bookToSave = event.getSource();
        if (
                bookToSave.getId() != null &&
                bookToSave.getComments().isEmpty()
        ) {
            val bookFromDb = bookRepository.findById(bookToSave.getId()).get();
            if(!bookFromDb.getComments().isEmpty()){
                commentRepository.deleteAll(bookFromDb.getComments());
            }
        }
    }
}
