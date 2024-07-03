package com.book.management.application.service.book;

import com.book.management.application.exception.ItemNotFoundException;
import com.book.management.application.model.BookModel;

import java.util.List;

/**
 * Provides methods for working with users.
 */
public interface BookService {

    /**
     * Creates new book.
     *
     * @param bookModel - The book to be created in database.
     * @return New book created in database.
     */
    BookModel createBook(BookModel bookModel);

    /**
     * Updates the specified book and returns updated model.
     *
     * @param bookModel - The user to be update in database.
     * @return Updated user model.
     * @throws ItemNotFoundException if book with the given id does not exist in database.
     */
    BookModel updateBook(BookModel bookModel);

    /**
     * Removes book with given ID.
     *
     * @param bookId - The book ID.
     */
    void deleteBook(Long bookId);

    /**
     * Deletes all books.
     *
     * @throws ItemNotFoundException if book with the given id does not exist in database.
     */
    public void deleteAllBooks();

    /**
     * Returns book by its ID if it exists.
     *
     * @param bookId - ID of the book to get.
     * @return The book specified by ID or null if no books found.
     * @throws ItemNotFoundException if book with the given id does not exist in database.
     */
    BookModel getBook(Long bookId);

    /**
     * Returns all books.
     *
     * @return The list of book.
     * @throws ItemNotFoundException if book with the given id does not exist in database.
     */
    List<BookModel> getAllBooks();

}
