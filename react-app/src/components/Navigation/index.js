import React from 'react';
import { NavLink } from 'react-router-dom';
import { useSelector } from 'react-redux';
import ProfileButton from './ProfileButton';
import './Navigation.css';

function Navigation({ isLoaded }){
	const sessionUser = useSelector(state => state.session.user);

	return (
		/*<ul>
			<li>
				<NavLink exact to="/">Home</NavLink>
			</li>
			{isLoaded && (
				<li>
					<ProfileButton user={sessionUser} />
				</li>
			)}
		</ul>*/
		<div className='navbar'> 
			<div className='logo'>
				<h2>Bookish.</h2>
			</div>
			<ul className='nav-menu'>
				<li>Home</li>
				<li>Books</li>
			</ul>

		</div>
	);
}

export default Navigation;