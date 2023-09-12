package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepo;

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
}
