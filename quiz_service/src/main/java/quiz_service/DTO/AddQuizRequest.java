package quiz_service.DTO;

public class AddQuizRequest {
    private String title;
    private String description;
    private String quizDate;

    public AddQuizRequest(String title, String description, String quizDate) {
        this.title = title;
        this.description = description;
        this.quizDate = quizDate;
    }

    // Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuizDate() {
        return quizDate;
    }

    public void setQuizDate(String quizDate) {
        this.quizDate = quizDate;
    }
}
