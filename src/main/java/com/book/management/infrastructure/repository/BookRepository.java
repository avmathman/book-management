package com.book.management.infrastructure.repository;

import java.util.Optional;

import com.book.management.domain.book.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data repository for users.
 */
@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findByTitle(String email);

    Optional<BookEntity> findByAuthor(String email);
}
