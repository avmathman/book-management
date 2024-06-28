package com.book.management.presentation.dto.book;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * The DTO of the book.
 */
@Getter
@Setter
public class BookDto {

    /**
     * The book identifier.
     */
    private Long id;

    /**
     * The book title.
     */
    private String title;

    /**
     * The book author.
     */
    private String author;

    /**
     * The book description.
     */
    private String description;

    /**
     * The date when the book entry was created.
     */
    private Timestamp createdAt;
}