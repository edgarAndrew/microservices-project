import React from 'react';

const AddQuizForm = ({ newQuiz, setNewQuiz, handleAddQuiz }) => {
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewQuiz((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  return (
    <form onSubmit={handleAddQuiz}>
      <div className="mb-3">
        <label htmlFor="title" className="form-label">
          Quiz Title
        </label>
        <input
          type="text"
          className="form-control"
          id="title"
          name="title"
          value={newQuiz.title}
          onChange={handleInputChange}
          required
        />
      </div>

      <div className="mb-3">
        <label htmlFor="description" className="form-label">
          Description
        </label>
        <textarea
          className="form-control"
          id="description"
          name="description"
          rows="3"
          value={newQuiz.description}
          onChange={handleInputChange}
          required
        />
      </div>

      <div className="mb-3">
        <label htmlFor="quizDate" className="form-label">
          Quiz Date
        </label>
        <input
          type="date"
          className="form-control"
          id="quizDate"
          name="quizDate"
          value={newQuiz.quizDate}
          onChange={handleInputChange}
          required
        />
      </div>

      <button type="submit" className="btn btn-primary">
        Add Quiz
      </button>
    </form>
  );
};

export default AddQuizForm;
