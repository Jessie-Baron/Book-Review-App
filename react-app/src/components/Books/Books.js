import React, { useState } from "react";
import { login } from "../../store/session";
import { useDispatch, useSelector } from "react-redux";
import { Redirect } from "react-router-dom";
import './Books.css';
import books from "/Users/maleiyahbranch-davis/Desktop/Book_Rec_Project/Book-Review-App/react-app/src/components/assests/books.jpg"; 
import Navigation from "../Navigation";
import {FaSearch} from "react-icons/fa"

function Books() {
  const dispatch = useDispatch();
  const sessionUser = useSelector((state) => state.session.user);
  //const [email, setEmail] = useState("");
  //const [password, setPassword] = useState("");
  //const [errors, setErrors] = useState([]);
  const [books, setBooks] = useState([]); 


  return (
    <div className="book-list">
        {books.map((book) =>(
            <div key= {book.id} className="book"> 
                <div><h2>{book.title}</h2></div>
                <div><h3>{book.author}</h3></div>
                <div><h3>{book.genre}</h3></div>
                <div><img>{book.imag_url}</img></div>
                <div><button>Add to Favorites</button></div>
            </div>
        ))}


    </div>  
  );
}

export default Books; 