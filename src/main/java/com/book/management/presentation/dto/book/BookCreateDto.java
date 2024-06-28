package com.book.management.presentation.dto.book;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * The DTO with available fields for creating book.
 */
@Getter
@Setter
public class BookCreateDto {

    /**
     * The book title.
     */
    @NotNull(message = "Book title must be set")
    private String title;

    /**
     * The book author.
     */
    @NotNull(message = "Book author must be set")
    private String author;

    /**
     * The book description.
     */
    @NotNull(message = "Book description must be set")
    private String description;
}
