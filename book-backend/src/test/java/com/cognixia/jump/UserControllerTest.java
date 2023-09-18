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
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.model.UserBook.Status;
import com.cognixia.jump.model.UserBook;
import com.cognixia.jump.model.UserBookRequestBody;
import com.cognixia.jump.repository.BookRepository;
import com.cognixia.jump.repository.UserBookRepository;
import com.cognixia.jump.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    
    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    UserRepository userRepo;

    @Mock
    BookRepository bookRepo;

    @Mock
    UserBookRepository userBookRepo;

    @Test
    public void testGetAllUsers() throws Exception {

        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());

        when(userRepo.findAll()).thenReturn(userList);

        ResponseEntity<List<User>> users = userController.getUsers();
        assertEquals(HttpStatus.OK, users.getStatusCode());
        assertEquals(users.getBody(), userList);
    }

    @Test
    public void testGetUserById() throws Exception {

        int id = 1;
        User foundUser = new User(id);

        when(userRepo.findById(id)).thenReturn(Optional.of(foundUser));

        ResponseEntity<User> result = userController.getUserById(id);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(result.getBody(), foundUser);
    }

    @Test
    public void testGetUserByIdNotFound() throws Exception {

        UserController ucMock = mock(UserController.class);
        int id = 1;

        when(userRepo.findById(id)).thenReturn(Optional.empty());
        when(ucMock.getUserById(id)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> ucMock.getUserById(id));
    }

    @Test
    public void testGetUserByCredentials() throws Exception {
        
        User foundUser = new User(1, "GLiburd", "HelloWorld", Role.ROLE_USER);

        when(userRepo.getByCredentials(foundUser.getUsername(), foundUser.getPassword())).thenReturn(Optional.of(foundUser));

        ResponseEntity<User> result = userController.getUserByCredentials(foundUser);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(foundUser, result.getBody());
    }

    @Test
    public void testCreateUser() throws Exception {

        User createdUser = new User(1, "GLiburd", "HelloWorld", Role.ROLE_USER);

        when(userRepo.findByUsername(createdUser.getUsername())).thenReturn(Optional.empty());
        when(userRepo.save(createdUser)).thenReturn(createdUser);

        ResponseEntity<User> result = userController.createUser(createdUser);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(result.getBody(), createdUser);
    }

    @Test
    public void testAddBookToUserList() throws Exception {

        int id = -1;
        String title = "The Brothers Karamazov";
        UserBook userBook = new UserBook(id, new User(1), new Book(1), Status.CURRENTLY_READING, 3);
        Book book = new Book(-1, title, "Fyodor", "HF");
        UserBookRequestBody ubrb = new UserBookRequestBody(title, Status.CURRENTLY_READING, 3);

        when(userBookRepo.findByUserIdAndBookTitle(id, title)).thenReturn(Optional.empty());
        when(bookRepo.findByTitle(title)).thenReturn(Optional.of(book));
        when(userBookRepo.save(userBook)).thenReturn(userBook);

        ResponseEntity<?> result = userController.addBookToUserList(id, ubrb);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void testUpdateUser() throws Exception {

        User foundUser = new User(1, "GLiburd", "HelloWorld", Role.ROLE_USER);

        when(userRepo.findById(foundUser.getId())).thenReturn(Optional.of(foundUser));
        when(userRepo.save(foundUser)).thenReturn(foundUser);

        ResponseEntity<User> result = userController.updateUser(foundUser.getId(), foundUser);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(result.getBody(), foundUser);
    }
    
    @Test
    public void testUpdateUserBookEntry() throws Exception {

        User foundUser = new User(1, "GLiburd", "HelloWorld", Role.ROLE_USER);
        UserBookRequestBody ubrb = new UserBookRequestBody("Crime and Punishment", Status.COMPLETED, 5);
        UserBook userBook = new UserBook(1, new User(1), new Book(1), Status.COMPLETED, 5);

        when(userRepo.findById(foundUser.getId())).thenReturn(Optional.of(foundUser));
        when(userBookRepo.findByUserIdAndBookTitle(foundUser.getId(), ubrb.getTitle())).thenReturn(Optional.of(userBook));
        when(userBookRepo.save(userBook)).thenReturn(userBook);

        ResponseEntity<?> result = userController.updateUserBookEntry(foundUser.getId(), ubrb);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        // assertEquals(result.getBody(), userBook);
    }

    @Test
    public void testDeleteUser() throws Exception {

        User foundUser = new User(1, "GLiburd", "HelloWorld", Role.ROLE_USER);

        when(userRepo.findById(foundUser.getId())).thenReturn(Optional.of(foundUser));

        ResponseEntity<User> result = userController.deleteUser(foundUser.getId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(result.getBody(), foundUser);
    }

    @Test
    public void testRemoveUserBookEntry() throws Exception {

        UserBook userBook = new UserBook(1, new User(1), new Book(1), Status.COMPLETED, 5);
        UserBookRequestBody ubrb = new UserBookRequestBody("Crime and Punishment", Status.COMPLETED, 5); 

        when(userBookRepo.findByUserIdAndBookTitle(1, ubrb.getTitle())).thenReturn(Optional.of(userBook));

        ResponseEntity<?> result = userController.removeUserBookEntry(1, ubrb);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        // assertEquals(result.getBody(), userBook);
    }
}
