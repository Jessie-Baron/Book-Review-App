package com.cognixia.jump;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.controller.UserController;
import com.cognixia.jump.exception.ResourceNotFoundException;

import com.cognixia.jump.model.Book;
import com.cognixia.jump.controller.BooksController;
import com.cognixia.jump.repository.BookRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private BooksController bookController;

    @Mock
    BookRepository bookRepo;

    @Test
    public void testGetAllBook() throws Exception {

        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book());
        bookList.add(new Book());

        when(bookRepo.findAll()).thenReturn(bookList);

        List<Book> books = bookController.getBooks();
        assertEquals(books, bookList);
    }
}
