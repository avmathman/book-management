package com.book.management.application.mapper;

import com.book.management.application.model.BookModel;
import com.book.management.domain.book.BookEntity;
import org.mapstruct.Mapper;

/**
 * Mapper for converting BookEntity to BookModel and vice versa.
 */
@Mapper(componentModel = "spring")
public interface BookMapper extends ModelMapper<BookEntity, BookModel> {

}
