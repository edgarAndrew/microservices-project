import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './Navbar.css'; // Import custom styles for blur effect

const Navbar = ({ title, routes }) => {
  const navigate = useNavigate();
  const location = useLocation(); // Get current path

  const handleLogout = () => {
    // Perform logout logic
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('userId');
    navigate('/login');
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-primary navbar-blur">
      <div className="container-fluid d-flex justify-content-between align-items-center">
        <div className="navbar-title-wrapper" style={{ flexBasis: '300px' }}>
          {/* Fixed width for the title */}
          <a
            className="navbar-brand text-white fw-bold" // Make title bold
            href="1"
            style={{ fontSize: '1.5rem' }} // Optional: Increase title size
          >
            {title}
          </a>
        </div>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse justify-content-between" id="navbarNav">
          <ul className="navbar-nav me-auto">
            {routes &&
              routes.map((route, index) => (
                <li className="nav-item" key={index}>
                  <button
                    className={`nav-link btn btn-link text-white ${
                      location.pathname === route.path ? 'active' : ''
                    }`} // Highlight active route
                    onClick={() => navigate(route.path)}
                    style={{
                      textDecoration: 'none',
                      fontWeight: location.pathname === route.path ? 'bold' : 'normal',
                      borderBottom: location.pathname === route.path ? '2px solid white' : 'none',
                    }}
                  >
                    {route.label}
                  </button>
                </li>
              ))}
          </ul>
          <button onClick={handleLogout} className="btn btn-outline-danger">
            Logout
          </button>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
