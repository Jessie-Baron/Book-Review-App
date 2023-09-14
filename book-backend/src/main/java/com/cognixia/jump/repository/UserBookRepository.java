package com.cognixia.jump.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.UserBook;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Integer> {
    
    @Query(nativeQuery = true, value = "SELECT * FROM user_book WHERE user_id = ?1 AND book_id = ?2")
    public Optional<UserBook> findByUserAndBookId(int userId, int bookId);

    @Query(nativeQuery = true, value = "SELECT * FROM user_book WHERE user_id = ?1 AND book_id = (SELECT id FROM book WHERE title = ?2)")
    public Optional<UserBook> findByUserIdAndBookTitle(int userId, String title);
}
