Here is the code for the `FillInTheBlanksQuestion` class without line numbers:

```java
package onlineTest;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class FillInTheBlanksQuestion extends Question implements Serializable {
    private String[] answer;

    public FillInTheBlanksQuestion(int questionNumber, String text, double points, String[] answer) {
        super(questionNumber, text, points);
        this.answer = answer;
    }

    public FillInTheBlanksQuestion() {
        super();
    }

    @Override
    public void setAnswer(String[] answer) {
        this.answer = answer;
    }

    @Override
    public String[] getAnswer() {
        return answer;
    }

    @Override
    public double gradeQuestion() {
        int correctAnswers = 0;
        for (String selectedAnswer : answer) {
            for (String correctAnswer : getCorrectAnswer().split(",")) {
                if (selectedAnswer.equals(correctAnswer)) {
                    correctAnswers++;
                    break;
                }
            }
        }
        return (getPoints() / answer.length) * correctAnswers;
    }

    @Override
    public String getCorrectAnswer() {
        StringBuffer b = new StringBuffer();
        b.append("[");
        for (int i = answer.length - 1; i > -1; i--) {
            b.append(answer[i]);
            if (i - 1 == -1) {

            } else {
                b.append(", ");
            }
        }
        b.append("]");
        return b.toString();
    }

    public static double calculateQuestionEarnedPoints(FillInTheBlanksQuestion question, String[] answer) {
        String[] studentAnswer = answer; // Assuming answer contains an array of filled blanks
        String[] correctAnswer = question.getAnswer();

        // Convert the student's answer and the correct answer to sets for easy comparison
        Set<String> studentAnswerSet = new HashSet<>(Arrays.asList(studentAnswer));
        Set<String> correctAnswerSet = new HashSet<>(Arrays.asList(correctAnswer));

        if (studentAnswerSet.equals(correctAnswerSet)) {
            return question.getPoints();
        } else {
            return 0.0;
        }
    }
}
```
