package question_service.DTO;

import java.time.LocalDateTime;

public class GenericResponse {
    private String message;
    private LocalDateTime timestamp;

    public GenericResponse() {
    }

    public GenericResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
