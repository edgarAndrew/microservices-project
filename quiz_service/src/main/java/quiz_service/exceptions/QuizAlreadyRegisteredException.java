package quiz_service.exceptions;

import quiz_service.repository.QuizRegistrationRepository;

public class QuizAlreadyRegisteredException extends RuntimeException{
    public QuizAlreadyRegisteredException(String message){
        super(message);
    }
}
