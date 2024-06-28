package com.book.management.presentation.controller;

import com.book.management.application.service.book.BookService;
import com.book.management.presentation.common.UserManagementApiLocations;
import com.book.management.presentation.dto.book.BookCreateDto;
import com.book.management.presentation.dto.book.BookDto;
import com.book.management.presentation.mapper.BookCreateMapper;
import com.book.management.presentation.mapper.BookReadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Books REST API controller with representing methods.
 */
@RestController
@RequestMapping(
        path = "${management.api.prefix:}" + UserManagementApiLocations.BOOK,
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
     * @return The created user instance.
     */
    @RequestMapping(
            path = "",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDto> createUser(
            @RequestBody BookCreateDto bookCreateDto
    ) {
        final BookDto bookDto = bookReadMapper.modelToDto(bookService.createBook(bookCreateMapper.dtoToModel(bookCreateDto)));
        return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
    }

    /**
     * REST API method to modify a book.
     *
     * @param bookDto - The book {@link BookDto} to be modified.
     * @return The modified user instance.
     */
    @RequestMapping(
            path = "",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookDto> updateUser(
            @RequestBody BookDto bookDto
    ) {
        final BookDto updated = this.bookReadMapper.modelToDto(this.bookService.updateBook(this.bookReadMapper.dtoToModel(bookDto)));
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * REST API method to delete the specified user.
     *
     * @param id The ID of the user to delete.
     */
    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id
    ) {
        this.bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * REST API method to retrieve user by its ID.
     *
     * @param id - The user ID.
     * @return the user instance.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookDto> getUser(
            @PathVariable Long id
    ) {
        final BookDto bookDto = this.bookReadMapper.modelToDto(bookService.getBook(id));
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    /**
     * REST API method to retrieve list of users.
     *
     * @return The list of user instances.
     */
    @RequestMapping(
            path = "/all",
            method = RequestMethod.GET
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookDto>> getAllUsers() {
        List<BookDto> users = this.bookReadMapper.modelsToDtos(this.bookService.getAllBooks());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
