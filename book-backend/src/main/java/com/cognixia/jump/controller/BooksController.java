package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Book;
import com.cognixia.jump.repository.BookRepository;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api")
public class BooksController {

    @Autowired
	BookRepository repo;

    @GetMapping("/book")
	public List<Book> getBooks() {
		return repo.findAll();
	}
    
    @GetMapping("/book/{id}")
	public ResponseEntity<?> getBook(@PathVariable int id) throws ResourceNotFoundException{
		
		Optional<Book> found = repo.findById(id);
		
		if(found.isEmpty()) {
			throw new ResourceNotFoundException("Book not found");
		}
		
		return ResponseEntity.status(200).body(found.get());
	}

    @GetMapping("/book/genre")
	public List<Book> bookInSameGenre(@PathParam(value = "genre") String genre) {
	
		return repo.sameGenre(genre);
	}

    @PostMapping("/book")
	public ResponseEntity<?> createBook(@RequestBody Book book) {
		
		book.setId(null);
		
		Book created = repo.save(book);
		
		
		return ResponseEntity.status(201).body(created);
	}

    @DeleteMapping("/book/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable int id) throws ResourceNotFoundException {
		
		boolean exists = repo.existsById(id);
		
		if(exists) {

			Book deleted = repo.findById(id).get();
					
			repo.deleteById(id);
			
			return ResponseEntity.status(200).body(deleted);
		}
		
		throw new ResourceNotFoundException("Book not found. Unable to delete");
	}
}
