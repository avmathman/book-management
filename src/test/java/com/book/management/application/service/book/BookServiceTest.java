package com.book.management.application.service.book;

import com.book.management.ManagementApplication;
import com.book.management.application.model.BookModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ManagementApplication.class})
public class BookServiceTest {

    @Autowired
    private BookServiceImpl bookService;

    @Test
    public void createBook_passValidData_returnCreatedBook() {

        // Assign
        BookModel current = this.createBookModel("Title", "Author", "Description");

        // Act
        BookModel created = bookService.createBook(current);

        // Assert
        assertNotNull(created);
        assertNotNull(created.getId());
        assertFalse(created.getTitle().isEmpty());
        assertFalse(created.getAuthor().isEmpty());
        assertFalse(created.getDescription().isEmpty());
        assertEquals(current.getTitle(), created.getTitle());
        assertEquals(current.getAuthor(), created.getAuthor());
        assertEquals(current.getDescription(), created.getDescription());
    }

    @Test
    public void updateBook_passValidData_returnUpdatedBook() {

        // Assign
        final String authorNewName = "New Author";
        BookModel created = bookService.createBook(this.createBookModel("Title", "Author", "Description"));
        created.setAuthor(authorNewName);

        // Act
        BookModel updated = bookService.updateBook(created);

        // Assert
        assertNotNull(updated);
        assertFalse(updated.getAuthor().isEmpty());
        assertEquals(updated.getAuthor(), authorNewName);
    }

    @Test
    public void deleteBook_passBookId_validateBookIsDeleted() {

        // Assign
        BookModel created = bookService.createBook(this.createBookModel("Title", "Author", "Description"));

        // Act
        bookService.deleteBook(created.getId());

        // Assert
        try {
            bookService.getBook(created.getId());
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Book with given id=" + created.getId() + " does not exist!");
        }
    }

    @Test
    public void getBook_passBookId_returnExistingBook() {

        // Assign
        BookModel created = bookService.createBook(this.createBookModel("Title", "Author", "Description"));

        // Act
        BookModel current = bookService.getBook(created.getId());

        // Assert
        assertNotNull(current);
        assertEquals(created.getTitle(), current.getTitle());
        assertEquals(created.getAuthor(), current.getAuthor());
        assertEquals(created.getDescription(), current.getDescription());
    }

    @Test
    public void getAllBooks_returnAllBooks() {

        // Assign
        bookService.deleteAllBooks();
        BookModel first = bookService.createBook(this.createBookModel("Title", "Author", "Description"));
        BookModel second = bookService.createBook(this.createBookModel("Title2", "Author2", "Description2"));
        BookModel third = bookService.createBook(this.createBookModel("Title3", "Author3", "Description3"));

        // Act
        List<BookModel> books = bookService.getAllBooks();

        // Assert
        assertEquals(books.size(), 3);
        assertEquals(first.getTitle(), books.get(0).getTitle());
        assertEquals(first.getAuthor(), books.get(0).getAuthor());
        assertEquals(first.getDescription(), books.get(0).getDescription());
    }

    private BookModel createBookModel(String title, String author, String description) {
        BookModel bookModel = new BookModel();
        bookModel.setTitle(title);
        bookModel.setAuthor(author);
        bookModel.setDescription(description);

        return bookModel;
    }
}
