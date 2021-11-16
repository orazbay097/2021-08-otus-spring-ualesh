package ru.otus.homework08.exceptions;

public class NoSuchCommentException extends BaseLibraryException{
    public NoSuchCommentException(RuntimeException ex){
        super(ex);
    }
}