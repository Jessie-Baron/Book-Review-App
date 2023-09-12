package com.cognixia.jump.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.UserBook;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Integer> {
    
}
