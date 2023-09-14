package com.cognixia.jump.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    @Query(nativeQuery = true, value = "SELECT * FROM user WHERE username = 1? AND password = 2?")
    public Optional<User> getByCredentials(String username, String password);

    @Query(nativeQuery = true, value = "SELECT * FROM user WHERE username = 1?")
    public Optional<User> findByUsername(String username);
}
