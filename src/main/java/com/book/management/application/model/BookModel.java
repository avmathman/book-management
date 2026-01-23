package com.book.management.application.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * The model of the book.
 */
@Getter
@Setter
public class BookModel implements Serializable {

    private static final long serialVersionUID = 1L;

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
}
