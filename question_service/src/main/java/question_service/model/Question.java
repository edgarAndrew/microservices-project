package question_service.model;

import jakarta.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;
    private String options; // You can store options as a JSON string or use a separate entity for options
    private String answer;

    // Constructors, getters, setters, and other methods

    // Default constructor
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
