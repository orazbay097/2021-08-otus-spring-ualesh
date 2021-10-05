package ru.otus.homework05.exceptions;

public class NoSuchAuthorException extends BaseLibraryException{
    public NoSuchAuthorException(RuntimeException ex) {
        super(ex);
    }
}