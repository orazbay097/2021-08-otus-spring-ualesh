package ru.otus.homework08.exceptions;

public class BaseLibraryException extends RuntimeException{
    public BaseLibraryException(){
        super();
    }
    public BaseLibraryException(RuntimeException ex){
        super(ex);
    }
}
