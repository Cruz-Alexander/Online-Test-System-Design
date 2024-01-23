Here is the code for the `Exam` class without line numbers:

```java
package onlineTest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Exam implements Serializable {
    private int examId;
    private String title;
    private List<Question> questions;

    public Exam(int examId, String title) {
        this.examId = examId;
        this.title = title;
        this.questions = new ArrayList<>();
    }

    public Exam() {
        super();
    }

    public int getExamId() {
        return examId;
    }

    public String getTitle() {
        return title;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Question getQuestion(int questionNumber) {
        return questions.get(questionNumber);
    }
}
```
