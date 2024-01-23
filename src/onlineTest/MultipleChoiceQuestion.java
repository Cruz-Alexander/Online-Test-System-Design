Here is the `MultipleChoiceQuestion` class code without line numbers:

```java
package onlineTest;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class MultipleChoiceQuestion extends Question implements Serializable {
    private String[] answer;

    public MultipleChoiceQuestion(int questionNumber, String text, double points, String[] answer) {
        super(questionNumber, text, points);
        this.answer = answer;
    }

    public MultipleChoiceQuestion() {
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
        for (String selectedAnswer : answer) {
            for (String correctAnswer : getCorrectAnswer().split(",")) {
                if (selectedAnswer.equals(correctAnswer)) {
                    return getPoints();
                }
            }
        }
        return 0;
    }

    @Override
    public String getCorrectAnswer() {
        StringBuilder b = new StringBuilder();
        b.append("[");
        for (int i = 0; i < answer.length; i++) {
            b.append(answer[i]);
            if (i == answer.length - 1) {

            } else {
                b.append(", ");
            }
        }
        b.append("]");
        return b.toString();
    }

    public static double calculateQuestionEarnedPoints(MultipleChoiceQuestion question, String[] answer) {
        String[] studentAnswer = answer; // Assuming answer contains an array of selected choices
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
