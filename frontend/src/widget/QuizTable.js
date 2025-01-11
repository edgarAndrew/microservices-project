import React from 'react';


function QuizTable({
  quizzes,
  handleQuizClick,
  hoveredRow = null,
  setHoveredRow = () => {},
  userId,
  isStudent = false,
  handleDeleteClick = () => {},
  handleRegister = () => {},
  loadingQuizId,
}) {
  

  return (
    <table className="table table-striped">
      <thead>
        <tr>
          <th>ID</th>
          <th>Title</th>
          <th>Description</th>
          <th>Quiz Date</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {quizzes.map((quiz) => (
          <tr
            key={quiz.id}
            onClick={() => handleQuizClick(quiz.id)}
            onMouseEnter={() => setHoveredRow(quiz.id)} // Highlight row on hover
            onMouseLeave={() => setHoveredRow(null)} // Remove highlight on mouse leave
            style={{
              cursor: 'pointer',
              backgroundColor: hoveredRow === quiz.id ? '#e9ecef' : '#f9f9f9', // Dynamic style for hover
              transition: 'background-color 0.3s',
            }}
          >
            <td>{quiz.id}</td>
            <td>{quiz.title}</td>
            <td>{quiz.description}</td>
            <td>{quiz.quizDate}</td>
            <td>
              {isStudent ? (
                <button
                  className="btn btn-outline-primary btn-sm"
                  onClick={(e) => {
                    e.stopPropagation();
                    handleRegister(quiz.id);
                    
                  }}
                disabled={loadingQuizId === quiz.id} // Disable the button if this quiz is loading
               
                >
                  {loadingQuizId === quiz.id ? (
                    <span className="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                  ) : (
                    'Register'
                  )}
                </button>
              ) : (
                <button
                  className="btn btn-outline-danger btn-sm"
                  onClick={(e) => {
                    e.stopPropagation();
                    handleDeleteClick(quiz.id);
                  }}
                >
                  Delete
                </button>
              )}
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default QuizTable;

