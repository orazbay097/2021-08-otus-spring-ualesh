package ru.otus.homework08.repositories;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.homework08.config", "ru.otus.homework08.repositories", "ru.otus.homework08.events"})
public abstract class AbstractRepositoryTest {
}
