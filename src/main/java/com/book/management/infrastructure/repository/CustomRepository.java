package com.book.management.infrastructure.repository;


import java.util.List;
import java.util.Optional;

public interface CustomRepository<T> {

    T save(T book);

    Optional<T> update(T book);

    int deleteById(Long id);

    List<T> findAll();

    Optional<T> findById(long id);
}