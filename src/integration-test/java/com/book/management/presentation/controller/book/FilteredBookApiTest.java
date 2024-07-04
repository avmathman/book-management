package com.book.management.presentation.controller.book;

import com.book.management.application.model.BookModel;
import com.book.management.application.service.book.BookServiceImpl;
import com.book.management.presentation.annotation.ManagementIntegrationTest;
import com.book.management.presentation.common.BookManagementApiLocations;
import com.book.management.presentation.dto.book.FilteredBookDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ManagementIntegrationTest
public class FilteredBookApiTest {

    private final String BASE_API_URL = "/api" + BookManagementApiLocations.BOOK + "/filter";

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
    public void filterByAuthor_passFilterText_validateRetrievedCount() throws Exception {

        //Assign
        String filterText = "o";
        String firstAuthorName = "Ernest Hemingway";
        String secondAuthorName = "Johny Cash";

        service.createBook(createBookModel("The Old Man and the Sea", firstAuthorName, "Description"));
        service.createBook(createBookModel("Some good men", secondAuthorName, "Description"));
        service.createBook(createBookModel("A Farewell to arms", firstAuthorName, "Description"));
        service.createBook(createBookModel("The road to success", secondAuthorName, "Description"));
        service.createBook(createBookModel("The Sun Also Rises", firstAuthorName, "Description"));

        //Act
        String response = this.mockMvc
                .perform(
                        get(BASE_API_URL + "?title={id}", filterText)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //Assert
        List<FilteredBookDto> result = objectMapper.readValue(response, new TypeReference<List<FilteredBookDto>>(){});

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertEquals(result.get(0).getTitle(), firstAuthorName);
        assertEquals(result.get(0).getCount(), 3);
        assertEquals(result.get(1).getTitle(), secondAuthorName);
        assertEquals(result.get(1).getCount(), 2);
    }

    private BookModel createBookModel(String title, String author, String description) {
        BookModel model = new BookModel();
        model.setTitle(title);
        model.setAuthor(author);
        model.setDescription(description);

        return model;
    }
}
