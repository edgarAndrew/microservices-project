import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import ToastNotification from '../../widget/ToastNotification';
import Navbar from '../../widget/Navbar';
import QuizTable from '../../widget/QuizTable';
import AddQuizForm from '../../widget/AddQuizForm';

function TeacherDashboard() {
  const [quizzes, setQuizzes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showToast, setShowToast] = useState(false);
  const [toastMessage, setToastMessage] = useState('');
  const [showAddForm, setShowAddForm] = useState(false);
  const [newQuiz, setNewQuiz] = useState({
    title: '',
    description: '',
    quizDate: '',
  });
  const [addingQuiz, setAddingQuiz] = useState(false);
  const [deletingQuiz, setDeletingQuiz] = useState(false);
  const [hoveredRow, setHoveredRow] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    // Check localStorage for cached quizzes
    const cachedQuizzes = localStorage.getItem('quizzes');
    if (cachedQuizzes) {
      setQuizzes(JSON.parse(cachedQuizzes));
      setLoading(false);
    } else {
      // Fetch quizzes if not found in cache
      fetchQuizzes();
    }
  }, []);

  // Fetch quizzes from API
  const fetchQuizzes = async () => {
    setLoading(true); // Start loader
    try {
      const response = await axios.get('/api/v1/quiz');
      if (response.status === 200) {
        setQuizzes(response.data);
        localStorage.setItem('quizzes', JSON.stringify(response.data)); // Cache data in localStorage
      } else {
        setToastMessage(response.data.message || 'Failed to load quizzes');
        setShowToast(true);
      }
    } catch (error) {
      setToastMessage(error.response?.data?.message || 'An error occurred');
      setShowToast(true);
    } finally {
      setLoading(false); // Stop loader
    }
  };

  // Refresh the quiz data after adding/deleting a quiz
  const refreshQuizzes = async () => {
    try {
      const response = await axios.get('/api/v1/quiz');
      if (response.status === 200) {
        setQuizzes(response.data);
        localStorage.setItem('quizzes', JSON.stringify(response.data)); // Update localStorage cache
      }
    } catch (error) {
      setToastMessage('Failed to refresh quizzes');
      setShowToast(true);
    }
  };

  // Handle quiz click (navigate to details page)
  const handleQuizClick = (quizId) => {
    navigate(`/quiz/${quizId}`);
  };
  
  const formatDate = (date) => {
    const [year, month, day] = date.split('-');
    return `${day}-${month}-${year}`;
  };
  

  // Handle delete quiz
  const handleDeleteClick = async (quizId) => {
    setDeletingQuiz(true);
    try {
      await axios.delete(`/api/v1/quiz`, {
        params: { id: quizId },
        headers: {
          Authorization: '+8vJ1NGW6MAmk46t1HTy1GYg09UvT0/HlluCTg7LF0tzKQ5Y1PxwJ1W98VRC7U86',
        },
      });
      setToastMessage('Quiz deleted successfully!');
      setShowToast(true);
      refreshQuizzes(); // Refresh quizzes after deletion
    } catch (error) {
      setToastMessage(error.response?.data?.message || 'Failed to delete quiz');
      setShowToast(true);
    } finally {
      setDeletingQuiz(false);
    }
  };

  const handleAddQuiz = async (e) => {
    e.preventDefault();
    setAddingQuiz(true);
    
    // Format the quizDate before sending it
    const formattedQuizDate = formatDate(newQuiz.quizDate);
  
    try {
      await axios.post('/api/v1/quiz', { ...newQuiz, quizDate: formattedQuizDate }, {
        headers: {
          Authorization: '+8vJ1NGW6MAmk46t1HTy1GYg09UvT0/HlluCTg7LF0tzKQ5Y1PxwJ1W98VRC7U86',
        },
      });
      setNewQuiz({ title: '', description: '', quizDate: '' });
      setShowAddForm(false);
      setToastMessage('Quiz added successfully!');
      setShowToast(true);
      refreshQuizzes(); // Refresh quizzes after adding new quiz
    } catch (error) {
      setToastMessage(error.response?.data?.message || 'Failed to add quiz');
      setShowToast(true);
    } finally {
      setAddingQuiz(false);
    }
  };
  

  const routes = [
    { path: '/teacher-dashboard', label: 'Quizzes' },
    { path: '/questions', label: 'Questions' },
  ];

  return (
    <div>
      <Navbar title="Teacher Dashboard" routes={routes} />
      <div className="container my-5">
        <h2>Teacher Dashboard - Quizzes</h2>

        {/* Loading spinner */}
        {(loading || addingQuiz || deletingQuiz) && (
          <div
            className="d-flex justify-content-center align-items-center"
            style={{
              minHeight: '100vh',
              position: 'absolute',
              top: 0,
              left: 0,
              width: '100%',
              backgroundColor: 'rgba(255, 255, 255, 0.8)',
              zIndex: 9999,
            }}
          >
            <div className="spinner-border" role="status">
              <span className="visually-hidden">Loading...</span>
            </div>
          </div>
        )}

        {/* Quiz table and form */}
        {!loading && !addingQuiz && !deletingQuiz && (
          <>
            <QuizTable
              quizzes={quizzes}
              handleQuizClick={handleQuizClick}
              handleDeleteClick={handleDeleteClick}
              hoveredRow={hoveredRow} // If needed
              setHoveredRow={setHoveredRow} // If needed
            />


            {/* Add Quiz Button */}
            <div className="text-end mb-3">
              <button
                className="btn btn-primary"
                onClick={() => setShowAddForm(!showAddForm)}
              >
                {showAddForm ? 'Cancel' : 'Add Quiz'}
              </button>
            </div>

            {/* Add Quiz Form */}
            {showAddForm && (
              <AddQuizForm
                newQuiz={newQuiz}
                setNewQuiz={setNewQuiz}
                handleAddQuiz={handleAddQuiz}
              />
            )}
          </>
        )}

        {/* Toast Notification */}
        <ToastNotification
          message={toastMessage}
          show={showToast}
          onClose={() => setShowToast(false)}
        />
      </div>
    </div>
  );
}

export default TeacherDashboard;
