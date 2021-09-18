package ru.otus.homework03.parser;

import java.util.Queue;

public interface Parser<T> {
    Queue<T> parse(String input);
}
