Here is the code for the `ExamManager` class without line numbers:

```java
package onlineTest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

class ExamManager implements Serializable {
    private Map<Integer, Exam> exams;
    private static ExamManager instance = new ExamManager();

    public ExamManager() {
        this.exams = new HashMap<>();
    }

    public void addExam(int examId, String title) {
        exams.put(examId, new Exam(examId, title));
    }

    public Exam getExam(int examId) {
        return exams.get(examId);
    }

    public static ExamManager getInstance() {
        return instance;
    }

    public Question getQuestion(int examId, int questionNumber) {
        Exam exam = getExam(examId);
        if (exam != null) {
            return exam.getQuestion(questionNumber);
        } else {
            return null; // Handle case where exam with the given ID is not found
        }
    }
}
```
