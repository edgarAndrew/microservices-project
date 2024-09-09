package question_service.DTO;

public class UpdateQuestionRequest {
    private String description;
    private String options; // You can store options as a JSON string or use a separate entity for options
    private String answer;

    public UpdateQuestionRequest(String description, String options, String answer) {
        this.description = description;
        this.options = options;
        this.answer = answer;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
