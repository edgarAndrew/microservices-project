package quiz_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // Global error handler
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Unauthorized user exception handler
    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<ErrorDetails> handleUnauthorizedException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    // Unauthorized user exception handler
    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleNotFoundException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ErrorDetails> IllegalArgumentException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QuizAlreadyRegisteredException.class)
    public final ResponseEntity<ErrorDetails> yoloException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


}
