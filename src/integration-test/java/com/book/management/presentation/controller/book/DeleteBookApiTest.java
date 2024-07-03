package com.book.management.presentation.controller.book;

import com.book.management.presentation.annotation.ManagementIntegrationTest;
import com.book.management.application.model.BookModel;
import com.book.management.application.service.book.BookServiceImpl;
import com.book.management.presentation.common.BookManagementApiLocations;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ManagementIntegrationTest
public class DeleteBookApiTest {

    private final String BASE_API_URL = "/api" + BookManagementApiLocations.BOOK;

    @Autowired
    private BookServiceImpl service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        service.deleteAllBooks();
    }

    @Test
    public void deleteBook_passBookId_validateDeletedBook() throws Exception {

        //Assign
        BookModel createdModel = service.createBook(this.createBookModel("Title", "Author", "Description"));

        //Act and Assert
        String response = this.mockMvc
                .perform(
                        delete(BASE_API_URL + "/{id}", createdModel.getId())
                )
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    @Test
    public void deleteBook_passInvalidBookId_throwItemNotFoundException() throws Exception {

        //Assign
        Long invalidBookId = -1L;
        service.createBook(this.createBookModel("Title", "Author", "Description"));

        //Act
        String response = this.mockMvc
                .perform(
                        delete(BASE_API_URL + "/{id}", invalidBookId)
                )
                .andExpect(status().isNotFound())
                .andReturn()
                .getResolvedException()
                .getMessage();

        //Assert
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo("Book with given id=" + invalidBookId + " does not exist!");
    }

    private BookModel createBookModel(String title, String author, String description) {
        BookModel model = new BookModel();
        model.setTitle(title);
        model.setAuthor(author);
        model.setDescription(description);

        return model;
    }
}
