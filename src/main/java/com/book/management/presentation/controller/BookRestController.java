package com.book.management.presentation.controller;

import com.book.management.application.model.BookModel;
import com.book.management.application.service.book.BookService;
import com.book.management.presentation.common.BookManagementApiLocations;
import com.book.management.presentation.dto.book.BookCreateDto;
import com.book.management.presentation.dto.book.BookDto;
import com.book.management.presentation.dto.book.FilteredBookDto;
import com.book.management.presentation.mapper.BookCreateMapper;
import com.book.management.presentation.mapper.BookReadMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Books REST API controller with representing methods.
 */
@Tag(name = "Book", description = "The Book API")
@RestController
@RequestMapping(
        path = "${management.api.prefix:}" + BookManagementApiLocations.BOOK,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class BookRestController {

    private final BookService bookService;
    private final BookReadMapper bookReadMapper;
    private final BookCreateMapper bookCreateMapper;

    /**
     * Initializes a new {@link BookRestController} instance.
     *
     * @param bookService - {@link BookService} instance.
     * @param bookReadMapper - {@link BookReadMapper} instance.
     * @param bookCreateMapper - {@link BookCreateMapper} instance.
     */
    @Autowired
    public BookRestController(
            BookService bookService,
            BookReadMapper bookReadMapper,
            BookCreateMapper bookCreateMapper
    ) {
        this.bookService = bookService;
        this.bookReadMapper = bookReadMapper;
        this.bookCreateMapper = bookCreateMapper;
    }

    /**
     * REST API method to create a new book.
     *
     * @param bookCreateDto - The book {@link BookCreateDto} to create.
     * @return The created book instance.
     */
    @Operation(
            summary = "Add new book",
            description = "Allows to create new book.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully created")
    })
    @RequestMapping(
            path = "",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDto> createBook(
            @RequestBody BookCreateDto bookCreateDto
    ) {
        final BookDto bookDto = bookReadMapper.modelToDto(bookService.createBook(bookCreateMapper.dtoToModel(bookCreateDto)));
        return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
    }

    /**
     * REST API method to modify a book.
     *
     * @param bookDto - The book {@link BookDto} to be modified.
     * @return The modified book instance.
     */
    @Operation(
            summary = "Modify existing book",
            description = "Allows to modify existing book.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully updated"),
            @ApiResponse(responseCode = "404", description = "Book with given ID was not found")
    })
    @RequestMapping(
            path = "",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookDto> updateBook(
            @RequestBody BookDto bookDto
    ) {
        final BookDto updated = this.bookReadMapper.modelToDto(this.bookService.updateBook(this.bookReadMapper.dtoToModel(bookDto)));
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * REST API method to delete the specified book.
     *
     * @param id The ID of the book to delete.
     */
    @Operation(
            summary = "Delete existing book",
            description = "Allows to delete/remove existing book by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Book with given ID was not found")
    })
    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteBook(
            @PathVariable Long id
    ) {
        this.bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * REST API method to retrieve book by its ID.
     *
     * @param id - The book ID.
     * @return the book instance.
     */
    @Operation(
            summary = "Retrieve a book by ID",
            description = "Allows to retrieve existing book by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully found"),
            @ApiResponse(responseCode = "404", description = "Book with given ID was not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookDto> getBook(
            @PathVariable Long id
    ) {
        final BookDto bookDto = this.bookReadMapper.modelToDto(bookService.getBook(id));
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    /**
     * REST API method to retrieve list of books.
     *
     * @return The list of book instances.
     */
    @Operation(
            summary = "Retrieve all existing books",
            description = "Allows to retrieve all existing books.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully retrieved existing books."),
    })
    @RequestMapping(
            path = "/all",
            method = RequestMethod.GET
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = this.bookReadMapper.modelsToDtos(this.bookService.getAllBooks());
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    /**
     * REST API method to retrieve list of books by title name.
     *
     * @param title - The book's title name to find from database.
     * @return The list of books{@link List<FilteredBookDto>} instances.
     */
    @Operation(
            summary = "Filter book by title name",
            description = "Filters book by title name. Then, sorts by count of book for each author.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully found")
    })
    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<FilteredBookDto>> filterByTitle(
            @RequestParam String title
    ) {
        final List<FilteredBookDto> filteredBookDtos = bookService.filterByTitle(title)
                .stream()
                .collect(Collectors.groupingBy(BookModel::getAuthor, Collectors.counting()))
                .entrySet()
                .stream()
                .map(entry -> new FilteredBookDto(entry.getKey(), entry.getValue()))
                .sorted((f1, f2) -> Long.compare(f2.getCount(), f1.getCount()))
                .limit(10)
                .collect(Collectors.toList());

        return new ResponseEntity<>(filteredBookDtos, HttpStatus.OK);
    }
}
