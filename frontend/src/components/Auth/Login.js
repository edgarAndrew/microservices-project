import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import ToastNotification from '../../widget/ToastNotification';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [toastMessage, setToastMessage] = useState('');
  const [showToast, setShowToast] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('/api/v1/users/login', null, {
        params: { email, password }
      });

      if (response.status === 200) {
        // Store the token and user details in localStorage
        localStorage.setItem('token', response.data.token);
        localStorage.setItem('role', response.data.role);
        localStorage.setItem('userId', response.data.userId);

        // Set the token as the default Authorization header for all future axios requests
        axios.defaults.headers.common['Authorization'] = response.data.token;

        // Redirect based on the user role
        navigate(response.data.role === 'TEACHER' ? '/teacher-dashboard' : '/student-dashboard');
      } else {
        setToastMessage(response.data.message || 'Login failed. Please try again.');
        setShowToast(true);
      }
    } catch (error) {
      console.error('Login failed:', error);
      setToastMessage(error.response?.data?.message || 'Login failed. Please try again.');
      setShowToast(true);
    }
  };

  return (
    <div className="container-fluid p-3 my-5">
      <div className="row align-items-center">
        <div className="col-12 col-md-6 d-flex justify-content-center">
          <img
            src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.svg"
            className="img-fluid"
            alt="Phone"
          />
        </div>
        <div className="col-12 col-md-6 d-flex justify-content-center">
          <div className="w-100 d-flex align-items-center" style={{ minHeight: '80vh' }}>
            <form onSubmit={handleSubmit} className="w-75 mx-auto">
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
              <button type="submit" className="btn btn-primary mb-4 w-100" style={{ maxWidth: '400px' }}>Sign in</button>
              <div className="my-4">
                <p className="text-center mb-0">Don't have an account? <Link to="/register">Register here</Link></p>
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

export default Login;
