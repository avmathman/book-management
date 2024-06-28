package com.book.management.application.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class BookModel {

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
