package ru.otus.homework08.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.homework08.models.Comment;
import ru.otus.homework08.repositories.BookRepository;

@Component
@RequiredArgsConstructor
public class MongoCommentCascadeDeleteEventsListener extends AbstractMongoEventListener<Comment> {

    private final BookRepository bookRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Comment> event) {
        super.onAfterDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        bookRepository.removeCommentById(id);
    }
}
