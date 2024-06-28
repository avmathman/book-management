package com.book.management.presentation.mapper;

import com.book.management.application.model.BookModel;
import com.book.management.presentation.dto.book.BookDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookReadMapper {

    BookDto modelToDto(BookModel model);

    BookModel dtoToModel(BookDto dto);

    List<BookDto> modelsToDtos(List<BookModel> models);

    List<BookModel> dtosToModels(List<BookDto> dtos);
}
