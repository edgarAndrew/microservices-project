import React, { useEffect, useState, useCallback } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import Navbar from '../../widget/Navbar';
import ToastNotification from '../../widget/ToastNotification';
import Select from 'react-select'; // Import react-select for multi-select dropdown

function QuizDetails() {
    const { id } = useParams();
    const [quiz, setQuiz] = useState(null);
    const [questions, setQuestions] = useState([]);
    const [availableQuestions, setAvailableQuestions] = useState([]);
    const [showToast, setShowToast] = useState(false);
    const [toastMessage, setToastMessage] = useState('');
    const [isEditing, setIsEditing] = useState(false);
    const [editData, setEditData] = useState({
        title: '',
        description: '',
        quizDate: ''
    });
    const [isLoading, setIsLoading] = useState(false);
    const [selectedQuestions, setSelectedQuestions] = useState([]); // State for selected questions
    const [showAddQuestions, setShowAddQuestions] = useState(false); // State to toggle add questions form
    const [isAddingQuestions, setIsAddingQuestions] = useState(false); // State for managing add questions loader

    const fetchQuizDetails = useCallback(async () => {
        try {
            const response = await axios.get(`/api/v1/quiz/${id}`);
            if (response.status === 200) {
                setQuiz(response.data.quiz);
                setQuestions(response.data.questions);
                setEditData({
                    title: response.data.quiz.title,
                    description: response.data.quiz.description,
                    quizDate: formatDate(response.data.quiz.quizDate),  // Convert to dd-mm-yyyy
                });                
                // Fetch available questions
                const questionsResponse = await axios.get('/api/v1/question');
                if (questionsResponse.status === 200) {
                    const allQuestions = questionsResponse.data;
                    // Filter out the questions already added to the quiz
                    const filteredQuestions = allQuestions.filter(q => !response.data.questions.some(quizQ => quizQ.id === q.id));
                    setAvailableQuestions(filteredQuestions.map(q => ({
                        value: q.id,
                        label: q.description
                    })));
                }
            } else {
                setToastMessage(response.data.message || 'Failed to load quiz details');
                setShowToast(true);
            }
        } catch (error) {
            setToastMessage(error.response?.data?.message || 'An error occurred');
            setShowToast(true);
        }
    }, [id]);

    useEffect(() => {
        fetchQuizDetails();
    }, [fetchQuizDetails]);

    const handleEditClick = async () => {
        setIsLoading(true);
        try {
            const formattedData = {
                ...editData,
                quizDate: editData.quizDate  // Convert to yyyy-mm-dd before sending
            };
    
            const response = await axios.put(
                `/api/v1/quiz?id=${id}`,
                formattedData,
                {
                    headers: {
                        Authorization: '+8vJ1NGW6MAmk46t1HTy1GYg09UvT0/HlluCTg7LF0tzKQ5Y1PxwJ1W98VRC7U86'
                    }
                }
            );
            if (response.status === 200) {
                setQuiz({ ...quiz, ...editData });
                setIsEditing(false);
                setToastMessage('Quiz updated successfully');
            } else {
                setToastMessage(response.data.message || 'Failed to update quiz');
            }
        } catch (error) {
            setToastMessage(error.response?.data?.message || 'An error occurred');
        } finally {
            setIsLoading(false);
            setShowToast(true);
        }
    };

    const formatDate = (dateStr) => {
        const [year, month, day] = dateStr.split('-');
        return `${day}-${month}-${year}`;
    };
    

    

    const handleAddQuestions = async () => {
        setIsAddingQuestions(true);  // Show add questions loader
        try {
            const selectedIds = selectedQuestions.map(q => q.value);
            await axios.post('/api/v1/quiz/add-questions', {
                quizId: id,
                questionId: selectedIds
            },
                {
                    headers: {
                        Authorization: '+8vJ1NGW6MAmk46t1HTy1GYg09UvT0/HlluCTg7LF0tzKQ5Y1PxwJ1W98VRC7U86',
                    },
                });
            setToastMessage('Questions added successfully');
            setShowToast(true);
            setShowAddQuestions(false); // Hide the dropdown after adding
            setSelectedQuestions([]); // Clear selected questions
            fetchQuizDetails(); // Refetch quiz details after adding questions
        } catch (error) {
            setToastMessage(error.response?.data?.message || 'Failed to add questions');
            setShowToast(true);
        } finally {
            setIsAddingQuestions(false);  // Hide add questions loader
        }
    };

    const highlightCorrectAnswer = (answer) => (key) => ({
        backgroundColor: key === answer ? 'lightgreen' : 'transparent'
    });

    if (!quiz) {
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
            <Navbar title="Teacher Dashboard" />
            <div className="container my-5">
                <h2>Quiz Details - {quiz.title}</h2>
                <p>{quiz.description}</p>
                <p><strong>Quiz Date:</strong> {quiz.quizDate}</p>

                {/* Edit Button */}
                <button
                    className="btn btn-primary mb-3"
                    onClick={() => setIsEditing(!isEditing)}
                >
                    {isEditing ? 'Cancel' : 'Edit'}
                </button>

                {/* Edit Form */}
                {isEditing && (
                    <form onSubmit={(e) => { e.preventDefault(); handleEditClick(); }}>
                        <div className="mb-3">
                            <label htmlFor="titleInput" className="form-label">Title</label>
                            <input
                                type="text"
                                id="titleInput"
                                className="form-control"
                                value={editData.title}
                                onChange={(e) => setEditData({ ...editData, title: e.target.value })}
                                required
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="descriptionInput" className="form-label">Description</label>
                            <textarea
                                id="descriptionInput"
                                className="form-control"
                                value={editData.description}
                                onChange={(e) => setEditData({ ...editData, description: e.target.value })}
                                required
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="quizDateInput" className="form-label">Quiz Date</label>
                            <input
                                type="date"
                                id="quizDateInput"
                                className="form-control"
                                value={editData.quizDate}
                                onChange={(e) => setEditData({ ...editData, quizDate: e.target.value })}
                                required
                            />
                        </div>
                        <button type="submit" className="btn btn-success" disabled={isLoading}>
                            {isLoading ? 'Updating...' : 'Save Changes'}
                        </button>
                    </form>
                )}

                {/* Loader for Editing */}
                {isLoading && (
                    <div className="d-flex justify-content-center align-items-center mt-4">
                        <div className="spinner-border" role="status">
                            <span className="visually-hidden">Loading...</span>
                        </div>
                    </div>
                )}

                <h3>Questions:</h3>
                <ul className="list-group">
                    {questions.length > 0 ? (
                        questions.map((question) => (
                            <li key={question.id} className="list-group-item">
                                <p><strong>Question:</strong> {question.description}</p>
                                <p><strong>Options:</strong></p>
                                <ul>
                                    {Object.entries(JSON.parse(question.options)).map(([key, value]) => (
                                        <li key={key} style={highlightCorrectAnswer(question.answer)(key)}>
                                            {`${key}: ${value}`}
                                        </li>
                                    ))}
                                </ul>
                            </li>
                        ))
                    ) : (
                        <p>No questions added</p>
                    )}
                </ul>

                {/* Add Questions Button */}
                <div className="text-end mt-4">
                    <button
                        className="btn btn-success"
                        onClick={() => setShowAddQuestions(!showAddQuestions)}
                    >
                        {showAddQuestions ? 'Cancel' : 'Add Questions'}
                    </button>
                </div>

                {/* Add Questions Dropdown */}
                {showAddQuestions && (
                    <div className="mt-4">
                        <Select
                            isMulti
                            options={availableQuestions}
                            onChange={setSelectedQuestions}
                            value={selectedQuestions}
                            placeholder="Select questions to add..."
                        />
                        <button
                            className="btn btn-primary mt-2"
                            onClick={handleAddQuestions}
                            disabled={selectedQuestions.length === 0 || isAddingQuestions}
                        >
                            {isAddingQuestions ? 'Adding...' : 'Add Selected Questions'}
                        </button>
                        {/* Loader for Adding Questions */}
                        {isAddingQuestions && (
                            <div className="d-flex justify-content-center align-items-center mt-2">
                                <div className="spinner-border" role="status">
                                    <span className="visually-hidden">Loading...</span>
                                </div>
                            </div>
                        )}
                    </div>
                )}

                <ToastNotification
                    message={toastMessage}
                    show={showToast}
                    onClose={() => setShowToast(false)}
                />
            </div>
        </div>
    );
}

export default QuizDetails;
