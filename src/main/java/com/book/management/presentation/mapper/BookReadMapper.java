package com.book.management.presentation.mapper;

import com.book.management.application.model.BookModel;
import com.book.management.presentation.dto.book.BookDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for converting BookDto to BookModel and vice versa.
 */
@Mapper(componentModel = "spring")
public interface BookReadMapper {

    /**
     * Converts model to dto.
     *
     * @param model - The model {@link BookModel} object.
     * @return The dto {@link BookDto} object.
     */
    BookDto modelToDto(BookModel model);

    /**
     * Converts dto to model.
     *
     * @param dto - The dto {@link BookDto} object.
     * @return The model {@link BookModel} object.
     */
    BookModel dtoToModel(BookDto dto);

    /**
     * Converts models to DTOs.
     *
     * @param models - The list of dto {@link BookModel} objects.
     * @return The list of model {@link BookDto} objects.
     */
    List<BookDto> modelsToDtos(List<BookModel> models);

    /**
     * Converts entities to models.
     *
     * @param dtos - The list of dtos {@link BookDto} objects.
     * @return The list of models {@link BookModel} objects.
     */
    List<BookModel> dtosToModels(List<BookDto> dtos);
}
