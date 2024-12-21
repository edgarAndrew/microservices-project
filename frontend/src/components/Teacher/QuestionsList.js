import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Navbar from '../../widget/Navbar'; // Adjust the import path as necessary
import ToastNotification from '../../widget/ToastNotification'; // Import your existing toast component
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js'; // Ensure this is included

const cache = {
  questions: null, // In-memory cache
};

const QuestionsList = () => {
  const [questions, setQuestions] = useState([]);
  const [filteredQuestions, setFilteredQuestions] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [currentPage, setCurrentPage] = useState(1);
  const [searchQuery, setSearchQuery] = useState('');
  const [newQuestion, setNewQuestion] = useState({
    description: '',
    options: { A: '', B: '', C: '', D: '' },
    answer: '',
  });
  const [toast, setToast] = useState({ message: '', type: '' }); // State for toast

  const routes = [
    { path: '/teacher-dashboard', label: 'Quizzes' },
    { path: '/questions', label: 'Questions' }
  ];

  const questionsPerPage = 5;

  useEffect(() => {
    const fetchQuestions = async () => {
      if (cache.questions) {
        setQuestions(cache.questions);
        setFilteredQuestions(cache.questions);
        setIsLoading(false);
        return;
      }

      try {
        const response = await axios.get('/api/v1/question');
        if (response.status === 200) {
          cache.questions = response.data; // Update cache
          setQuestions(response.data);
          setFilteredQuestions(response.data);
        }
      } catch (error) {
        console.error('Failed to fetch questions', error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchQuestions();
  }, []);

  const handleSearch = (event) => {
    const query = event.target.value.toLowerCase();
    setSearchQuery(query);
    const filtered = questions.filter(q =>
      q.description.toLowerCase().includes(query)
    );
    setFilteredQuestions(filtered);
  };

  const handleAddQuestion = async () => {
    try {
      const newQuestionData = [
        {
          ...newQuestion,
          options: JSON.stringify(newQuestion.options),
        }
      ];

      const response = await axios.post('/api/v1/question', newQuestionData);
      setToast({ message: response.data.message || 'Question added successfully!', type: 'success' });

      // Clear form
      setNewQuestion({ description: '', options: { A: '', B: '', C: '', D: '' }, answer: '' });

      // Fetch updated questions list
      const updatedResponse = await axios.get('/api/v1/question');
      cache.questions = updatedResponse.data; // Update cache
      setQuestions(updatedResponse.data);
      setFilteredQuestions(updatedResponse.data);

    } catch (error) {
      setToast({ message: `Failed to add question: ${error.response?.data?.message || error.message}`, type: 'error' });
    }
  };

  const indexOfLastQuestion = currentPage * questionsPerPage;
  const indexOfFirstQuestion = indexOfLastQuestion - questionsPerPage;
  const currentQuestions = filteredQuestions.slice(indexOfFirstQuestion, indexOfLastQuestion);

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const renderPagination = () => {
    const totalPages = Math.ceil(filteredQuestions.length / questionsPerPage);
    const pageNumbers = [];
    for (let i = 1; i <= totalPages; i++) {
      pageNumbers.push(i);
    }
    return (
      <nav aria-label="Page navigation">
        <ul className="pagination justify-content-center">
          {pageNumbers.map((number) => (
            <li key={number} className={`page-item ${currentPage === number ? 'active' : ''}`}>
              <button
                type="button"
                className="page-link"
                onClick={() => handlePageChange(number)}
              >
                {number}
              </button>
            </li>
          ))}
        </ul>
      </nav>
    );
  };

  const highlightCorrectAnswer = (question, optionKey) => {
    return optionKey === question.answer ? 'bg-success text-white' : '';
  };

  return (
    <div>
      <Navbar title="Questions List" routes={routes} />
      <div className="container my-5">
        <h2>Questions List</h2>

        {/* Search Bar */}
        <div className="my-3">
          <input
            type="text"
            className="form-control"
            placeholder="Search questions..."
            value={searchQuery}
            onChange={handleSearch}
          />
        </div>

        {/* Floating Add Question Button */}
        <div className="position-fixed bottom-0 end-0 m-4">
          <button
            className="btn btn-primary rounded-circle"
            style={{ width: '60px', height: '60px' }}
            data-bs-toggle="modal"
            data-bs-target="#addQuestionModal"
          >
            +
          </button>
        </div>

        {/* Add Question Modal */}
        <div className="modal fade" id="addQuestionModal" tabIndex="-1" aria-labelledby="addQuestionModalLabel" aria-hidden="true">
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title" id="addQuestionModalLabel">Add Question</h5>
                <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div className="modal-body">
                <form>
                  <div className="mb-3">
                    <label htmlFor="questionDescription" className="form-label">Description</label>
                    <input
                      type="text"
                      className="form-control"
                      id="questionDescription"
                      value={newQuestion.description}
                      onChange={(e) => setNewQuestion({ ...newQuestion, description: e.target.value })}
                    />
                  </div>
                  <div className="mb-3">
                    <label htmlFor="questionOptions" className="form-label">Options</label>
                    <input
                      type="text"
                      className="form-control"
                      placeholder="Option A"
                      value={newQuestion.options.A}
                      onChange={(e) => setNewQuestion({ ...newQuestion, options: { ...newQuestion.options, A: e.target.value } })}
                    />
                    <input
                      type="text"
                      className="form-control my-2"
                      placeholder="Option B"
                      value={newQuestion.options.B}
                      onChange={(e) => setNewQuestion({ ...newQuestion, options: { ...newQuestion.options, B: e.target.value } })}
                    />
                    <input
                      type="text"
                      className="form-control my-2"
                      placeholder="Option C"
                      value={newQuestion.options.C}
                      onChange={(e) => setNewQuestion({ ...newQuestion, options: { ...newQuestion.options, C: e.target.value } })}
                    />
                    <input
                      type="text"
                      className="form-control my-2"
                      placeholder="Option D"
                      value={newQuestion.options.D}
                      onChange={(e) => setNewQuestion({ ...newQuestion, options: { ...newQuestion.options, D: e.target.value } })}
                    />
                  </div>
                  <div className="mb-3">
                    <label htmlFor="questionAnswer" className="form-label">Correct Answer (key)</label>
                    <select
                      className="form-control"
                      id="questionAnswer"
                      value={newQuestion.answer}
                      onChange={(e) => setNewQuestion({ ...newQuestion, answer: e.target.value })}
                    >
                      <option value="">Select correct answer</option>
                      <option value="A">A</option>
                      <option value="B">B</option>
                      <option value="C">C</option>
                      <option value="D">D</option>
                    </select>
                  </div>
                </form>
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" className="btn btn-primary" onClick={handleAddQuestion} data-bs-dismiss="modal">Add Question</button>
              </div>
            </div>
          </div>
        </div>

        {/* Toast Notification */}
        {toast.message && <ToastNotification message={toast.message} type={toast.type} />}

        {/* Loading Spinner */}
        {isLoading ? (
          <div className="d-flex justify-content-center align-items-center" style={{ minHeight: '100vh' }}>
            <div className="spinner-border" role="status">
              <span className="visually-hidden">Loading...</span>
            </div>
          </div>
        ) : (
          <>
            {/* Questions List */}
            <ul className="list-group">
              {currentQuestions.map((question) => (
                <li key={question.id} className="list-group-item">
                  <p><strong>Description:</strong> {question.description}</p>
                  <ul className="list-unstyled">
                    {Object.entries(JSON.parse(question.options)).map(([key, value]) => (
                      <li key={key} className={highlightCorrectAnswer(question, key)}>
                        {`${key}: ${value}`}
                      </li>
                    ))}
                  </ul>
                </li>
              ))}
            </ul>

            {/* Pagination */}
            {renderPagination()}
          </>
        )}
      </div>
    </div>
  );
};

export default QuestionsList;
