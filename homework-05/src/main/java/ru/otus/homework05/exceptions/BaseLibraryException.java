package ru.otus.homework05.exceptions;

public class BaseLibraryException extends RuntimeException{
    public BaseLibraryException(){
        super();
    }
    public BaseLibraryException(RuntimeException ex){
        super(ex);
    }
}
