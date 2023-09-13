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

    @Test
    public void testGetBookById() throws Exception {
        int id = 1;
        Book foundBook = new Book(id, "Little Bow Peep", "Little Suzie", "Horror");

        when(bookRepo.findById(id)).thenReturn(Optional.of(foundBook));

        ResponseEntity<Book> result = bookController.getBook(id);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(foundBook, result.getBody());
    }

    @Test
    public void testGetBookByIdNotFound() throws Exception {
        BooksController ucMock = mock(BooksController.class);
        int id = 1;

        when(bookRepo.findById(id)).thenReturn(Optional.empty());
        when(ucMock.getBook(id)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> ucMock.getBook(id));
    }

    @Test
    public void testGetBookByGenre() throws Exception {
        String genre = "Horror";
        Book foundbook = new Book(1, "Little Bow Peep", "Little Suzie", "Horror");

         List<Book> bookList = new ArrayList<>();
         bookList.add(foundbook);

        
        when(bookRepo.sameGenre(genre)).thenReturn(bookList);

        List<Book> result = bookController.bookInSameGenre(genre);
        assertEquals(bookList, result);
    }


    @Test
    public void testCreateBook() throws Exception {
        Book createdBook = new Book(1, "Little Bow Peep", "Little Suzie", "Horror"); 

        when(bookRepo.save(createdBook)).thenReturn(createdBook);

        ResponseEntity<?> result = bookController.createBook(createdBook);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(result.getBody(), createdBook);

    }

    @Test
    public void testDeleteBook() throws Exception {
        Book deletedBook = new Book(1, "Little Bow Peep", "Little Suzie", "Horror");
    

        when(bookRepo.findById(deletedBook.getId())).thenReturn(Optional.of(deletedBook));

        ResponseEntity<?> result = bookController.deleteBook(1);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(result.getBody(), deletedBook);
    }

}

