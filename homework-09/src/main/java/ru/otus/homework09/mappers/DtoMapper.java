package ru.otus.homework09.mappers;

public interface DtoMapper <E,D>{
    D toDto(E e);
    E fromDto(D d);
}
