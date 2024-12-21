// src/components/Register.js

import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import ToastNotification from '../../widget/ToastNotification';

function Register() {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('STUDENT');
  const [toastMessage, setToastMessage] = useState('');
  const [showToast, setShowToast] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('/api/v1/users/register', {
        name,
        email,
        password,
        role
      });

      if (response.status === 200) {
        console.log('Registration successful:', response.data);
        navigate('/login');
      } else {
        setToastMessage(response.data.message || 'Registration failed. Please try again.');
        setShowToast(true);
      }
    } catch (error) {
      console.error('Registration failed:', error);
      setToastMessage(error.response?.data?.message || 'Registration failed. Please try again.');
      setShowToast(true);
    }
  };

  return (
    <div className="container-fluid p-3 my-5">
      <div className="row align-items-center">
        <div className="col-12 col-md-6 d-flex justify-content-center">
          <img
            src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.svg" // Replace with appropriate image if needed
            className="img-fluid"
            alt="Register"
          />
        </div>
        <div className="col-12 col-md-6 d-flex justify-content-center">
          <div className="w-100 d-flex align-items-center" style={{ minHeight: '80vh' }}>
            <form onSubmit={handleSubmit} className="w-75 mx-auto">
              <div className="mb-4">
                <label htmlFor="nameInput" className="form-label">Name</label>
                <input
                  type="text"
                  className="form-control form-control-sm"
                  id="nameInput"
                  placeholder="Enter your name"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  style={{ maxWidth: '400px' }}
                />
              </div>
              <div className="mb-4">
                <label htmlFor="emailInput" className="form-label">Email address</label>
                <input
                  type="email"
                  className="form-control form-control-sm"
                  id="emailInput"
                  placeholder="Enter your email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  style={{ maxWidth: '400px' }}
                />
              </div>
              <div className="mb-4">
                <label htmlFor="passwordInput" className="form-label">Password</label>
                <input
                  type="password"
                  className="form-control form-control-sm"
                  id="passwordInput"
                  placeholder="Enter your password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  style={{ maxWidth: '400px' }}
                />
              </div>
              <div className="mb-4">
                <label htmlFor="roleSelect" className="form-label">Role</label>
                <select
                  id="roleSelect"
                  className="form-select form-select-sm"
                  value={role}
                  onChange={(e) => setRole(e.target.value)}
                  style={{ maxWidth: '400px' }}
                >
                  <option value="STUDENT">Student</option>
                  <option value="TEACHER">Teacher</option>
                </select>
              </div>

              <button type="submit" className="btn btn-primary mb-4 w-100" style={{ maxWidth: '400px' }}>Register</button>

              <div className="my-4">
                <p className="text-center mb-0">Already have an account? <Link to="/login">Login here</Link></p>
              </div>
            </form>
          </div>
        </div>
      </div>
      <ToastNotification
        message={toastMessage}
        show={showToast}
        onClose={() => setShowToast(false)}
      />
    </div>
  );
}

export default Register;
