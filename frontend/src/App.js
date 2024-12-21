// src/App.js
import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Register from './components/Auth/Register';
import Login from './components/Auth/Login';
import TeacherDashboard from './components/Teacher/TeacherDashboard';
import StudentDashboard from './components/Student/StudentDashboard';
import QuizDetails from './components/Teacher/QuizDetails';
import QuestionsList from './components/Teacher/QuestionsList';
import TakeQuiz from './components/Student/TakeQuiz';

const App = () => {
  return (
    <Router>
      <div className="App">
        <Routes>
          {/* Redirect root path to /login */}
          <Route path="/" element={<Navigate to="/login" replace />} />
          
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
          <Route path="/teacher-dashboard" element={<TeacherDashboard />} />
          <Route path="/student-dashboard" element={<StudentDashboard />} />
          <Route path="/quiz/:id" element={<QuizDetails />} />
          <Route path="/take-quiz/:quizId" element={<TakeQuiz />} />
          <Route path="/questions/:pageNumber" element={<QuestionsList />} />
          <Route path="/questions" element={<QuestionsList />} 
          />
          {/* Add more routes as needed */}

          {/* Catch all route - redirect to login if no other route matches */}
          <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;