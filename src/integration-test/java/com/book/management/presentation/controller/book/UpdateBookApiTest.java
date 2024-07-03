package com.book.management.presentation.controller.book;

import com.book.management.presentation.annotation.ManagementIntegrationTest;
import com.book.management.application.model.BookModel;
import com.book.management.application.service.book.BookServiceImpl;
import com.book.management.presentation.common.BookManagementApiLocations;
import com.book.management.presentation.dto.book.BookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ManagementIntegrationTest
public class UpdateBookApiTest {

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
    public void updateBook_changeAuthorName_validateUpdatedBook() throws Exception {

        //Assign
        final String authorNewName = "New Author";
        BookModel createdModel = service.createBook(this.createBookModel("Title", "Author", "Description"));

        BookDto bookDto = new BookDto();
        bookDto.setId(createdModel.getId());
        bookDto.setTitle(createdModel.getTitle());
        bookDto.setAuthor(authorNewName);
        bookDto.setDescription(createdModel.getDescription());

        //Act
        String response = this.mockMvc
                .perform(
                        put(BASE_API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(bookDto))
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //Assert
        BookDto result = objectMapper.readValue(response, BookDto.class);
        assertThat(result).isNotNull();
        assertEquals(result.getId(), bookDto.getId());
        assertEquals(result.getTitle(), bookDto.getTitle());
        assertEquals(result.getAuthor(), authorNewName);
        assertEquals(result.getDescription(), bookDto.getDescription());
    }

    private BookModel createBookModel(String title, String author, String description) {
        BookModel model = new BookModel();
        model.setTitle(title);
        model.setAuthor(author);
        model.setDescription(description);

        return model;
    }
}
