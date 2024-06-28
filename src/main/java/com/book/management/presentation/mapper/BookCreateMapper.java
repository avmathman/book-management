package com.book.management.presentation.mapper;

import com.book.management.application.model.BookModel;
import com.book.management.presentation.dto.book.BookCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookCreateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    BookModel dtoToModel(BookCreateDto bookCreateDto);
}
