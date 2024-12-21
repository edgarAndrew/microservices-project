import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Navbar from '../../widget/Navbar';
import ToastNotification from '../../widget/ToastNotification';

const TakeQuiz = () => {
  const { quizId } = useParams();
  const [quiz, setQuiz] = useState(null);
  const [selectedAnswers, setSelectedAnswers] = useState({});
  const [loading, setLoading] = useState(true);
  const [isSubmitting, setIsSubmitting] = useState(false); // New state for submission loader
  const [toastMessage, setToastMessage] = useState('');
  const [showToast, setShowToast] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchQuizDetails = async () => {
      try {
        const response = await axios.get(`/api/v1/quiz/${quizId}`);
        setQuiz(response.data); // Store quiz details in state
      } catch (error) {
        setToastMessage(error.response?.data?.message || 'Failed to load quiz. Please try again.');
        setShowToast(true);
      } finally {
        setLoading(false);
      }
    };

    fetchQuizDetails();
  }, [quizId]);

  const handleOptionChange = (questionId, selectedOption) => {
    setSelectedAnswers({
      ...selectedAnswers,
      [questionId]: selectedOption,
    });
  };

  const handleSubmit = async () => {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');
    setIsSubmitting(true); // Set isSubmitting to true when submission starts
    try {
      const quizQuestions = quiz.questions;
      let score = 0;

      // Calculate score by comparing selected answers to correct answers
      quizQuestions.forEach((question) => {
        const correctAnswer = question.answer;
        const userAnswer = selectedAnswers[question.id];

        if (userAnswer === correctAnswer) {
          score += 1; // Increment score for each correct answer
        }
      });

      const scorePercentage = (score / quizQuestions.length) * 100;
      await axios.put(
        `/api/v1/results?userId=${userId}&quizId=${quizId}&score=${scorePercentage}`,
        {},
        {
          headers: {
            Authorization: token,
          },
        }
      );

      setToastMessage(`Quiz submitted successfully! Your score: ${scorePercentage}%`);
      setShowToast(true);

      // Redirect back to student dashboard after 2 seconds
      setTimeout(() => {
        navigate('/student-dashboard');
      }, 2000);
    } catch (error) {
      setToastMessage(error.response?.data?.message || 'Failed to submit quiz. Please try again.');
      setShowToast(true);
    } finally {
      setIsSubmitting(false); // Set isSubmitting to false after submission is done
    }
  };

  // Display a loader while the quiz is loading or submitting
  if (loading || isSubmitting) {
    return (
      <div className="d-flex justify-content-center align-items-center" style={{ minHeight: '100vh' }}>
        <div className="spinner-border" role="status">
          <span className="visually-hidden">Loading...</span>
        </div>
      </div>
    );
  }

  return (
    <div>
      <Navbar title="Take Quiz" routes={[{ path: '/student-dashboard', label: 'Quizzes' }]} />
      <div className="container my-5">
        <h2>{quiz?.quiz?.title || 'Quiz'}</h2>
        <p>{quiz?.quiz?.description}</p>
        <p><strong>Quiz Date: </strong>{quiz?.quiz?.quizDate}</p>

        {quiz?.questions?.map((question) => (
          <div
            key={question.id}
            className="mb-4 p-3 border rounded"
            style={{ backgroundColor: '#f8f9fa', borderColor: '#dee2e6' }}
          >
            <h5>{question.description}</h5>
            {Object.entries(JSON.parse(question.options)).map(([optionKey, optionValue]) => (
              <div className="form-check" key={optionKey}>
                <input
                  className="form-check-input"
                  type="radio"
                  name={`question-${question.id}`}
                  id={`question-${question.id}-${optionKey}`}
                  value={optionKey}
                  onChange={() => handleOptionChange(question.id, optionKey)}
                  checked={selectedAnswers[question.id] === optionKey}
                />
                <label className="form-check-label" htmlFor={`question-${question.id}-${optionKey}`}>
                  {optionValue}
                </label>
              </div>
            ))}
          </div>
        ))}

        {/* Submit Button */}
        <button className="btn btn-primary" onClick={handleSubmit} disabled={isSubmitting}>
          {isSubmitting ? (
            <span className="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
          ) : (
            'Submit Quiz'
          )}
        </button>
      </div>

      {/* Toast Notification */}
      <ToastNotification
        message={toastMessage}
        show={showToast}
        onClose={() => setShowToast(false)}
      />
    </div>
  );
};

export default TakeQuiz;
