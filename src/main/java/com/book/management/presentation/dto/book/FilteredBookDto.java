package com.book.management.presentation.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The DTO of the filtered book.
 */
@Getter
@Setter
@AllArgsConstructor
public class FilteredBookDto {

    /**
     * The book author.
     */
    private String author;

    /**
     * The book identifier.
     */
    private Long count;
}
