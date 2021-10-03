package ru.otus.homework05.exceptions;

public class NoSuchAuthorException extends RuntimeException{
    public NoSuchAuthorException(RuntimeException ex) {
        super(ex);
    }
}
