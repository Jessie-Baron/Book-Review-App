package com.cognixia.jump.model;

import com.cognixia.jump.model.UserBook.Status;
import java.util.Objects;

public class UserBookRequestBody {
    
    private String title;
    private Status status;
    private Integer rating;


    public UserBookRequestBody() {
    }

    public UserBookRequestBody(String title, Status status, Integer rating) {
        this.title = title;
        this.status = status;
        this.rating = rating;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public UserBookRequestBody title(String title) {
        setTitle(title);
        return this;
    }

    public UserBookRequestBody status(Status status) {
        setStatus(status);
        return this;
    }

    public UserBookRequestBody rating(Integer rating) {
        setRating(rating);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserBookRequestBody)) {
            return false;
        }
        UserBookRequestBody userBookEntry = (UserBookRequestBody) o;
        return Objects.equals(title, userBookEntry.title) && Objects.equals(status, userBookEntry.status) && Objects.equals(rating, userBookEntry.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, status, rating);
    }

    @Override
    public String toString() {
        return "{" +
            " title='" + getTitle() + "'" +
            ", status='" + getStatus() + "'" +
            ", rating='" + getRating() + "'" +
            "}";
    }

}
