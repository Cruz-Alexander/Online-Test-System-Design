Here is the `Question` class code without line numbers:

```java
package onlineTest;

import java.io.Serializable;

abstract class Question implements Serializable {
    private int questionNumber;
    private String text;
    private double points;

    public Question(int questionNumber, String text, double points) {
        this.questionNumber = questionNumber;
        this.text = text;
        this.points = points;
    }

    public Question() {
        super();
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public String getText() {
        return text;
    }

    public double getPoints() {
        return points;
    }

    // public abstract double calculateQuestionEarnedPoints(Question question, String[] string);

    public abstract void setAnswer(String[] answer);

    public abstract String[] getAnswer();

    public abstract double gradeQuestion();

    public abstract String getCorrectAnswer();
}
```
