package com.cognixia.jump.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class UserBook {

    public static enum Status {
        PLAN_TO_READ, CURRENTLY_READING, COMPLETED
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book bookId;

    @Column
    private Status status;

    @Column
    private Integer rating;


    public UserBook() {
    }

    public UserBook(Integer id, User userId, Book bookId, Status status, Integer rating) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.status = status;
        this.rating = rating;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserId() {
        return this.userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Book getBookId() {
        return this.bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getRating() {
        return this.rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public UserBook id(Integer id) {
        setId(id);
        return this;
    }

    public UserBook userId(User userId) {
        setUserId(userId);
        return this;
    }

    public UserBook bookId(Book bookId) {
        setBookId(bookId);
        return this;
    }

    public UserBook status(Status status) {
        setStatus(status);
        return this;
    }

    public UserBook rating(Integer rating) {
        setRating(rating);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserBook)) {
            return false;
        }
        UserBook userBook = (UserBook) o;
        return Objects.equals(id, userBook.id) && Objects.equals(userId, userBook.userId) && Objects.equals(bookId, userBook.bookId) && Objects.equals(status, userBook.status) && Objects.equals(rating, userBook.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, bookId, status, rating);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", userId='" + getUserId() + "'" +
            ", bookId='" + getBookId() + "'" +
            ", status='" + getStatus() + "'" +
            ", rating='" + getRating() + "'" +
            "}";
    }

}
