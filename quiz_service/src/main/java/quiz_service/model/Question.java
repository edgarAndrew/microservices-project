package quiz_service.model;


public class Question {
    private Long id;
    private String description;
    private String options;
    private String answer;

    public Question() {
    }

    // Parameterized constructor
    public Question(String description, String options, String answer) {
        this.description = description;
        this.options = options;
        this.answer = answer;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

