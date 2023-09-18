import React, { useState } from "react";
import { login } from "../../store/session";
import { useDispatch, useSelector } from "react-redux";
import { Redirect } from "react-router-dom";
import './Splash.css';
import books from "/Users/maleiyahbranch-davis/Desktop/Book_Rec_Project/Book-Review-App/react-app/src/components/assests/books.jpg"; 
import Navigation from "../Navigation";
import {FaSearch} from "react-icons/fa"

function Splash() {
  const dispatch = useDispatch();
  const sessionUser = useSelector((state) => state.session.user);
  //const [email, setEmail] = useState("");
  //const [password, setPassword] = useState("");
  //const [errors, setErrors] = useState([]);


  return (
    <div className="splash">
      <Navigation />

      <img src={books}/>
      <div className="bg-overlay"></div>

      <div className="splash-text">
        <h1>Welcome to Bookish</h1>
        <p>Let us help you find your next read.</p>
        <form className="form">
            <div>
              <input type="text" placeholder="Search Books"></input>
            </div>
            <div>
              <button><FaSearch className="icon"/></button>
            </div>
        </form>
      </div>
        <button className="home-btn">Login</button>
        <button className="home-btn">Register</button>
    </div>  
  );
}

export default Splash; 