package com.book.management.presentation.dto.book;

import com.book.management.application.model.BookModel;
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
     * The book identifier.
     */
    private Long id;

    /**
     * The book author.
     */
    private String author;

    public static FilteredBookDto convertFrom(BookModel model) {
        return new FilteredBookDto(model.getId(), model.getAuthor());
    }
}
