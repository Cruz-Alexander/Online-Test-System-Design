Here is the `Student` class code without line numbers:

```java
package onlineTest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

class Student implements Serializable {
    private String name;
    private Map<Integer, Map<Integer, String[]>> examAnswers;

    public Student(String name) {
        this.name = name;
        this.examAnswers = new HashMap<>();
    }

    public Student() {
        super();
    }

    public String getName() {
        return name;
    }

    public void addAnswer(int examId, int questionNumber, String[] answer) {
        examAnswers.computeIfAbsent(examId, key -> new HashMap<>()).put(questionNumber, answer);
    }

    public Map<Integer, Map<Integer, String[]>> getExamAnswers() {
        return examAnswers;
    }

    public Map<Integer, Double> getExamScores() {
        Map<Integer, Double> examScores = new HashMap<>();

        ExamManager examManager = new ExamManager(); // Assuming ExamManager is a singleton or accessible globally

        for (Map.Entry<Integer, Map<Integer, String[]>> examEntry : examAnswers.entrySet()) {
            int examId = examEntry.getKey();
            Map<Integer, String[]> examAnswers = examEntry.getValue();

            double totalPoints = 0.0;
            double earnedPoints = 0.0;

            for (Map.Entry<Integer, String[]> entry : examAnswers.entrySet()) {
                int questionNumber = entry.getKey();
                String[] answer = entry.getValue();

                // Retrieve the question from the ExamManager using the examId and questionNumber
                Question question = examManager.getQuestion(examId, questionNumber);
                if (question != null) {
                    double questionPoints = question.getPoints();

                    // Compare the answer with the expected answer for the question
                    // and assign points accordingly
                    double questionEarnedPoints = calculateQuestionEarnedPoints(question, answer);

                    earnedPoints += questionEarnedPoints;
                    totalPoints += questionPoints;
                }
            }

            // Calculate the exam score as a percentage
            double examScore = (earnedPoints / totalPoints) * 100;

            // Add the exam score to the map
            examScores.put(examId, examScore);
        }

        return examScores;
    }

    public double calculateQuestionEarnedPoints(Question question, String[] answer) {
        if (question instanceof TrueFalseQuestion) {
            return TrueFalseQuestion.calculateQuestionEarnedPoints((TrueFalseQuestion) question, answer);
        } else if (question instanceof MultipleChoiceQuestion) {
            return MultipleChoiceQuestion.calculateQuestionEarnedPoints((MultipleChoiceQuestion) question, answer);
        } else if (question instanceof FillInTheBlanksQuestion) {
            return FillInTheBlanksQuestion.calculateQuestionEarnedPoints((FillInTheBlanksQuestion) question, answer);
        } else {
            return 0.0; // Handle unsupported question types or other cases
        }
    }
}
```
