package onlineTest;

import java.io.Serializable;

class TrueFalseQuestion extends Question implements Serializable {
    private boolean answer;

    public TrueFalseQuestion(int questionNumber, String text, double points, boolean answer) {
        super(questionNumber, text, points);
        this.answer = answer;
    }

    public TrueFalseQuestion() {
        super();
    }

    @Override
    public void setAnswer(String[] answer) {
        if (answer[0].equals("true")) {
            this.answer = true;
        } else {
            this.answer = false;
        }
    }

    @Override
    public String[] getAnswer() {
        String[] string = new String[1];
        if (answer == true) {
            string[0] = "true";
            return string;
        } else {
            string[0] = "false";
            return string;
        }
    }

    @Override
    public double gradeQuestion() {
        return answer ? getPoints() : 0;
    }

    @Override
    public String getCorrectAnswer() {
        String s = "";
        if (answer == true) {
            s = "True";
        } else {
            s = "False";
        }
        return s;
    }

    public static double calculateQuestionEarnedPoints(TrueFalseQuestion question, String[] answer) {
        boolean studentAnswer = Boolean.parseBoolean(answer[0]); // Assuming answer contains a single element
        // representing the student's true/false response
        boolean correctAnswer = Boolean.parseBoolean(question.getCorrectAnswer());

        if (studentAnswer == correctAnswer) {
            return question.getPoints();
        } else {
            return 0.0;
        }
    }
}
