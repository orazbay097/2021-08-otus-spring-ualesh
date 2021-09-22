package ru.otus.homework04.parser;

import java.util.Queue;

public interface Parser<T> {
    Queue<T> parse(String input);
}
