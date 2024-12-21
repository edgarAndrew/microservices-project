import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate hook
import axios from 'axios';
import Navbar from '../../widget/Navbar';
import QuizTable from '../../widget/QuizTable';
import ToastNotification from '../../widget/ToastNotification'; // Import your existing toast component

const StudentDashboard = () => {
  const [quizzes, setQuizzes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [toastMessage, setToastMessage] = useState('');
  const [showToast, setShowToast] = useState(false);
  const [userId] = useState(localStorage.getItem('userId'));
  const [loadingQuizId, setLoadingQuizId] = useState(null); // To track the loading state of the quiz being registered
  const navigate = useNavigate(); // Initialize useNavigate

  const fetchQuizzes = async () => {
    try {
      const response = await axios.get('/api/v1/quiz');
      setQuizzes(response.data);
    } catch (error) {
      setToastMessage(error.response?.data?.message || 'Failed to fetch quizzes. Please try again.');
        setShowToast(true);
    } finally {
      setLoading(false);
    }
  };

  const handleQuizClick = (quizId) => {
    // Navigate to the TakeQuiz page with the quiz ID
    navigate(`/take-quiz/${quizId}`);
  };

  const handleRegister = async (quizId) => {
    const integerUserId = parseInt(userId, 10);
    const token = localStorage.getItem('token');
    setLoadingQuizId(quizId); // Set the loader state for the clicked quiz
    try {
      const response = await axios.post(
        '/api/v1/registration',
        {
          userId: integerUserId,
          quizId: quizId,
        },
        {
          headers: {
            Authorization: token,
          },
        }
      );
  
      // Check if the status is 200 for success
      if (response.status === 200) {
        setToastMessage(response.data.message || 'Registration successful.');
        setShowToast(true);
      } else {
        // Handle non-200 statuses
        const errorMessage = response.data.message || 'Registration failed. Please try again.';
        ToastNotification(errorMessage, 'error');
      }
    } catch (error) {
      setToastMessage(error.response?.data?.message || 'Registration failed. Please try again.');
      setShowToast(true);
    } finally {
      setLoadingQuizId(null); // Reset the loader state after the response
    }
  };

  useEffect(() => {
    fetchQuizzes();
  }, []);

  return (
    <div>
      <Navbar title="Student Dashboard" routes={[{ path: '/student-dashboard', label: 'Quizzes' }]} />
      <div className="container my-5">
        <h2>Student Dashboard - Quizzes</h2>

        {/* Quiz Table */}
        <QuizTable
          quizzes={quizzes}
          handleQuizClick={handleQuizClick}
          hoveredRow={null} // Optional if not needed
          setHoveredRow={() => { }} // Optional if not needed
          userId={userId}
          isStudent={true}
          handleRegister={handleRegister}
          loadingQuizId={loadingQuizId} // Pass the loading state to the QuizTable component
        />

        {/* Loading Spinner */}
        {loading && (
          <div className="d-flex justify-content-center align-items-center" style={{ minHeight: '100vh' }}>
            <div className="spinner-border" role="status">
              <span className="visually-hidden">Loading...</span>
            </div>
          </div>
        )}
      </div>
      <ToastNotification
        message={toastMessage}
        show={showToast}
        onClose={() => setShowToast(false)}
      />
    </div>
  );
};

export default StudentDashboard;
