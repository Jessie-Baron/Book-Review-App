import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import './Splash.css';
import books from "/Users/maleiyahbranch-davis/Desktop/Book_Rec_Project/Book-Review-App/react-app/src/components/assests/books.jpg"; 
import Navigation from "../Navigation";
import {FaSearch} from "react-icons/fa"; 
import LoginFormModal from "../LoginFormModal";
import SignupFormModal from "../SignupFormModal";

function Splash() {
  const dispatch = useDispatch();
  const sessionUser = useSelector((state) => state.session.user);
  //const [email, setEmail] = useState("");
  //const [password, setPassword] = useState("");
  //const [errors, setErrors] = useState([]);
  const [loginformmodal,setloginformmodal] = useState(false);
  const [signupformmodal,setsignupformmodal] = useState(false);

function login() {
  setloginformmodal(true);
  setsignupformmodal(false);
}
function signup() {
  setsignupformmodal(true);
  setloginformmodal(false); 
}


  


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
        {/* <button className="home-btn"><LoginFormModal onClick={OpenModalButton} show= {false}>Login</LoginFormModal></button> */}
        {/* <button className="home-btn"><SignupFormModal onClick={OpenModalButton} show= {false}>Register</SignupFormModal></button> */}
        <button className="home-btn" onClick={login}>Login</button>
        <button className="home-btn" onClick={signup}>Register</button>
        {loginformmodal && <LoginFormModal/>} 
        {signupformmodal&& <SignupFormModal/>}
    </div>  
  );
}

export default Splash; 