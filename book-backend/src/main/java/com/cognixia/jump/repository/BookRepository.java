package com.cognixia.jump.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Book;

@Repository
public interface BookRepository extends JpaRepository <Book, Integer> {
    @Query("select b from Book b where b.genre = ?1")
	public List<Book> sameGenre(String genre);

    @Query(nativeQuery = true, value = "SELECT * FROM book WHERE title = ?1")
    public Optional<Book> findByTitle(String title);
}
