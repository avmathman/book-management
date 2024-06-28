package com.book.management.presentation.mapper;

import com.book.management.application.model.BookModel;
import com.book.management.presentation.dto.book.BookCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for converting BookCreateDto to BookModel.
 */
@Mapper(componentModel = "spring")
public interface BookCreateMapper {

    /**
     * Converts entity to model.
     *
     * @param bookCreateDto - The dto{@link BookCreateDto} object.
     * @return The model{@link BookModel} object.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    BookModel dtoToModel(BookCreateDto bookCreateDto);
}
