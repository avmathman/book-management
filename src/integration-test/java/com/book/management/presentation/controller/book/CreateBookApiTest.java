package com.book.management.presentation.controller.book;

import com.book.management.presentation.annotation.ManagementIntegrationTest;
import com.book.management.application.service.book.BookServiceImpl;
import com.book.management.presentation.common.BookManagementApiLocations;
import com.book.management.presentation.dto.book.BookCreateDto;
import com.book.management.presentation.dto.book.BookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ManagementIntegrationTest
public class CreateBookApiTest {

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
    public void createBook_passTitleAuthorDescription_returnsCreatedBook() throws Exception {

        //Assign
        BookCreateDto createDto = createCreateBookDto("Title", "Author", "Description");

        //Act
        String response = this.mockMvc
                .perform(
                        post(BASE_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(createDto))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //Assert
        BookDto result = objectMapper.readValue(response, BookDto.class);
        assertThat(result).isNotNull();
        assertNotNull(result.getId());
        assertEquals(result.getTitle(), createDto.getTitle());
        assertEquals(result.getAuthor(), createDto.getAuthor());
        assertEquals(result.getDescription(), createDto.getDescription());
    }

    private BookCreateDto createCreateBookDto(String title, String author, String description) {
        BookCreateDto dto = new BookCreateDto();
        dto.setTitle(title);
        dto.setAuthor(author);
        dto.setDescription(description);

        return dto;
    }
}
