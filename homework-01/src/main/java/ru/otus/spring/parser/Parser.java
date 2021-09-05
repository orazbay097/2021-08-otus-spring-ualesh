package ru.otus.spring.parser;

import java.util.Collection;
import java.util.Queue;

public interface Parser<T> {
    Queue<T> parse(String input);
}
