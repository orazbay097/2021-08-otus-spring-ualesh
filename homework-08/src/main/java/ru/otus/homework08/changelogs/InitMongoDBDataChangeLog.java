package ru.otus.homework08.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import lombok.val;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework08.models.Author;
import ru.otus.homework08.models.Book;
import ru.otus.homework08.models.Genre;

import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {
    private Author author;
    private Genre genre;

    @ChangeSet(order = "000", id = "dropDB", author = "oualesh", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthor", author = "oualesh", runAlways = true)
    public void initAuthor(MongoTemplate template){
        author = template.save(new Author(null, "Ualesh", "Orazbay"));
    }

    @ChangeSet(order = "002", id = "initGenre", author = "oualesh", runAlways = true)
    public void initGenre(MongoTemplate template){
        genre = template.save(new Genre(null, "Fantasy"));
    }

    @ChangeSet(order = "003", id = "initBook", author = "oualesh", runAlways = true)
    public void initBook(MongoTemplate template){
        val book = new Book(null, "Art of Java", author, List.of(genre), List.of());
        template.save(book);
    }
}
