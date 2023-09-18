package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.exception.UserExistsException;
import com.cognixia.jump.model.Book;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.model.UserBook;
import com.cognixia.jump.model.UserBookRequestBody;
import com.cognixia.jump.repository.BookRepository;
import com.cognixia.jump.repository.UserBookRepository;
import com.cognixia.jump.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserBookRepository userBookRepo;

    @Autowired
    BookRepository bookRepo;

    /********************
	 GET OPERATIONS
	 ********************/
    
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {

        List<User> users = userRepo.findAll();

        return ResponseEntity.status(200).body(users);
    } 
    
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) throws Exception {

        Optional<User> foundUser = userRepo.findById(id);

        if (foundUser.isEmpty()) {
            throw new ResourceNotFoundException("User");
        }

        return ResponseEntity.status(200).body(foundUser.get());
    }

    /********************
		POST OPERATIONS
	 ********************/
    @PostMapping("/user/auth")
    public ResponseEntity<User> getUserByCredentials(@RequestBody User user) throws Exception {
        
        Optional<User> validUser = userRepo.getByCredentials(user.getUsername(), user.getPassword());

        if (validUser.isEmpty()) {
            throw new ResourceNotFoundException("user");
        }

        return ResponseEntity.status(200).body(validUser.get());
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) throws Exception {

        Optional<User> existingUser = userRepo.findByUsername(user.getUsername());

        if (existingUser.isPresent()) {
            throw new UserExistsException("User");
        }

        user.setRole(Role.ROLE_USER);
        user.setId(-1);

        User createdUser = userRepo.save(user);

        return ResponseEntity.status(201).body(createdUser);
    }

    // You only need to inlcude the book title, status, and rating in the request body and the method will create the actual UserBook object with the correct book id before adding it to the database
    @PostMapping("/user/{userId}")
    public ResponseEntity<UserBook> addBookToUserList(@PathVariable int userId, @RequestBody UserBookRequestBody userBookRB) throws Exception {

        Optional<UserBook> existingUserBook = userBookRepo.findByUserIdAndBookTitle(userId, userBookRB.getTitle());

        if (existingUserBook.isPresent()) {
            throw new UserExistsException("UserBook");
        }

        Optional<Book> foundBook = bookRepo.findByTitle(userBookRB.getTitle());

        UserBook newUserBook = new UserBook(
            -1,
            new User(userId),
            new Book(foundBook.get().getId()),
            userBookRB.getStatus(),
            userBookRB.getRating()
        );

        UserBook createdUserBook = userBookRepo.save(newUserBook);

        return ResponseEntity.status(201).body(createdUserBook);
    }

    

    /********************
	 UPDATE OPERATIONS
	 ********************/

    @PatchMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) throws Exception {

        Optional<User> foundUser = userRepo.findById(id);

        if (foundUser.isEmpty()) {
            throw new ResourceNotFoundException("User");
        }

        User existingUser = foundUser.get();

        if (user.getUsername() != null)
            existingUser.setUsername(user.getUsername());
        if (user.getPassword() != null)
            existingUser.setPassword(user.getPassword());

        User updatedUser = userRepo.save(existingUser);

        return ResponseEntity.status(200).body(updatedUser);
    }


    // UserBookRequestBody has 3 variables. Book title, status, and rating. Because this method is just to update the UserBook entry, either status or rating can be left blank in the request body if you don't plan on changing the values for both of those variables
    @PatchMapping("/user/{userId}/{bookId}")
    public ResponseEntity<UserBook> updateUserBookEntry(@PathVariable int userId, @PathVariable int bookId, @RequestBody UserBookRequestBody userBookRB) throws Exception {

        Optional<User> foundUser = userRepo.findById(userId);

        if (foundUser.isEmpty()) {
            throw new ResourceNotFoundException("User");
        }

        Optional<UserBook> foundEntry = userBookRepo.findByUserIdAndBookTitle(userId, userBookRB.getTitle());

        if (foundEntry.isEmpty()) {
            throw new ResourceNotFoundException("Book title");
        }

        UserBook existingUserBook = foundEntry.get();

        if (userBookRB.getRating() != null)
            existingUserBook.setRating(userBookRB.getRating());
        if (userBookRB.getStatus() != null)
            existingUserBook.setStatus(userBookRB.getStatus());

        UserBook updated = userBookRepo.save(existingUserBook);

        return ResponseEntity.status(200).body(updated);
    }

    /********************
		DELETE OPERATIONS
	 ********************/

    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) throws Exception {

        Optional<User> foundUser = userRepo.findById(id);

        if (foundUser.isEmpty()) {
            throw new ResourceNotFoundException("User");
        }

        userRepo.delete(foundUser.get());

        return ResponseEntity.status(200).body(foundUser.get());
    }

    // The only thing you need to include in the request body is the title of the book
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<UserBook> removeUserBookEntry(@PathVariable int userId, @PathVariable UserBookRequestBody userBookRB) throws Exception {

        Optional<UserBook> foundUserBook = userBookRepo.findByUserIdAndBookTitle(userId, userBookRB.getTitle());

        if (foundUserBook.isEmpty()) {
            throw new ResourceNotFoundException("Book title");
        }

        userBookRepo.delete(foundUserBook.get());

        return ResponseEntity.status(200).body(foundUserBook.get());
    }
}
