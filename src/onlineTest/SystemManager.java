Here is the `SystemManager` class code without line numbers:

```java
package onlineTest;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SystemManager implements Manager, Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Student> students;
    private ExamManager examManager;
    private TreeMap<Double, String> letterGradeCutoffs = new TreeMap<>();

    public SystemManager() {
        this.students = new HashMap<>();
        this.examManager = new ExamManager();
        this.letterGradeCutoffs = new TreeMap<>();
    }

    @Override
    public boolean addExam(int examId, String title) {
        if (examManager.getExam(examId) == null) {
            examManager.addExam(examId, title);
            return true;
        }
        return false;
    }

    @Override
    public void addTrueFalseQuestion(int examId, int questionNumber, String text, double points, boolean answer) {
        Exam exam = examManager.getExam(examId);
        if (exam != null) {
            TrueFalseQuestion question = new TrueFalseQuestion(questionNumber, text, points, answer);
            exam.addQuestion(question);
        }
    }

    @Override
    public void addMultipleChoiceQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
        Exam exam = examManager.getExam(examId);
        if (exam != null) {
            MultipleChoiceQuestion question = new MultipleChoiceQuestion(questionNumber, text, points, answer);
            exam.addQuestion(question);
        }
    }

    @Override
    public void addFillInTheBlanksQuestion(int examId, int questionNumber, String text, double points,
                                           String[] answer) {
        Exam exam = examManager.getExam(examId);
        if (exam != null) {
            FillInTheBlanksQuestion question = new FillInTheBlanksQuestion(questionNumber, text, points, answer);
            exam.addQuestion(question);
        }
    }

    @Override
    public String getKey(int examId) {
        Exam exam = examManager.getExam(examId);
        if (exam != null) {
            StringBuilder builder = new StringBuilder();
            for (Question question : exam.getQuestions()) {
                builder.append("Question Text: ").append(question.getText()).append("\n");
                builder.append("Points: ").append(question.getPoints()).append("\n");
                builder.append("Correct Answer: ").append(question.getCorrectAnswer()).append("\n");
            }
            return builder.toString();
        }
        return "Exam not found";
    }

    @Override
    public boolean addStudent(String name) {
        if (!students.containsKey(name)) {
            students.put(name, new Student(name));
            return true;
        }
        return false;
    }

    @Override
    public void answerTrueFalseQuestion(String studentName, int examId, int questionNumber, boolean answer) {
        Student student = students.get(studentName);
        Exam exam = examManager.getExam(examId);
        if (student != null && exam != null) {
            TrueFalseQuestion question = findTrueFalseQuestion(exam, questionNumber);
            if (question != null) {
                String[] answerArray = { String.valueOf(answer) };
                student.addAnswer(examId, questionNumber, answerArray);
            }
        }
    }

    @Override
    public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer) {
        Student student = students.get(studentName);
        Exam exam = examManager.getExam(examId);
        if (student != null && exam != null) {
            MultipleChoiceQuestion question = findMultipleChoiceQuestion(exam, questionNumber);
            if (question != null) {
                student.addAnswer(examId, questionNumber, answer);
            }
        }
    }

    @Override
    public void answerFillInTheBlanksQuestion(String studentName, int examId, int questionNumber, String[] answer) {
        Student student = students.get(studentName);
        Exam exam = examManager.getExam(examId);
        if (student != null && exam != null) {
            FillInTheBlanksQuestion question = findFillInTheBlanksQuestion(exam, questionNumber);
            if (question != null) {
                student.addAnswer(examId, questionNumber, answer);
            }
        }
    }

    @Override
    public double getExamScore(String studentName, int examId) {
        Student student = students.get(studentName);
        Exam exam = examManager.getExam(examId);
        if (student != null && exam != null) {
            double totalScore = 0.0;
            Map<Integer, String[]> answers = student.getExamAnswers().getOrDefault(examId, new HashMap<>());
            for (Question question : exam.getQuestions()) {
                String[] studentAnswer = answers.get(question.getQuestionNumber());
                if (studentAnswer != null) {
                    if (question.getClass().equals(MultipleChoiceQuestion.class)) {
                        boolean b = true;
                        if (

question.getAnswer().length != studentAnswer.length) {
                            b = false;
                        } else {
                            for (int i = 0; i < question.getAnswer().length; i++) {
                                if (studentAnswer[i] != question.getAnswer()[i]) {
                                    b = false;
                                }
                            }
                        }
                        if (b == true) {
                            totalScore += question.getPoints();
                        }
                    } else if (question.getClass().equals(FillInTheBlanksQuestion.class)) {
                        for (int i = 0; i < question.getAnswer().length; i++) {
                            for (int j = 0; j < answers.get(question.getQuestionNumber()).length; j++) {
                                if (answers.get(question.getQuestionNumber())[j].equals(question.getAnswer()[i])) {
                                    totalScore += question.getPoints() / question.getAnswer().length;
                                }
                            }
                        }
                    } else {
                        if (studentAnswer[0].equals(question.getAnswer()[0])) {
                            totalScore += question.getPoints();
                        }
                    }
                }
            }
            return totalScore;
        }
        return 0.0;
    }

    @Override
    public String getGradingReport(String studentName, int examId) {
        Student student = students.get(studentName);
        Exam exam = examManager.getExam(examId);
        if (student != null && exam != null) {
            StringBuilder builder = new StringBuilder();
            Map<Integer, String[]> answers = student.getExamAnswers().getOrDefault(examId, new HashMap<>());
            double totalPoints = 0.0;
            double totalScore = 0.0;
            for (Question question : exam.getQuestions()) {
                builder.append("Question #").append(question.getQuestionNumber()).append(" ");
                if (question.getClass().equals(TrueFalseQuestion.class)) {
                    answers.get(question.getQuestionNumber());
                    if (answers.get(question.getQuestionNumber())[0].equals(question.getAnswer()[0])) {
                        builder.append(question.getPoints()).append(" points out of ").append(question.getPoints())
                                .append("\n");
                        totalScore += question.getPoints();
                    } else {
                        builder.append("0.0").append(" points out of ").append(question.getPoints()).append("\n");
                    }
                } else if (question.getClass().equals(MultipleChoiceQuestion.class)) {
                    if (question.getAnswer().length != answers.get(question.getQuestionNumber()).length) {
                        builder.append("0.0").append(" points out of ").append(question.getPoints()).append("\n");
                    }
                    if (question.getAnswer().length > 1 && answers.get(question.getQuestionNumber()).length > 1) {
                        boolean b = true;
                        for (int i = 0; i < question.getAnswer().length; i++) {
                            if (answers.get(question.getQuestionNumber())[i].equals(question.getAnswer()[i])) {
                            } else {
                                b = false;
                                break;
                            }
                        }
                        if (b == true) {
                            builder.append(question.getPoints()).append(" points out of ").append(question.getPoints())
                                    .append("\n");
                            totalScore += question.getPoints();
                        } else {
                            builder.append("0.0").append(" points out of ").append(question.getPoints()).append("\n");
                        }
                    } else {
                        if (question.getAnswer().length != answers.get(question.getQuestionNumber()).length) {
                        } else if (answers.get(question.getQuestionNumber())[0].equals(question.getAnswer()[0])) {
                            builder.append(question.getPoints()).append(" points out of ").append(question.getPoints())
                                    .append("\n");
                            totalScore += question.getPoints();
                        } else {
                            builder.append("0.0").append(" points out of ").append(question.getPoints()).append("\n");
                        }
                    }
                } else {
                    double current = 0.0;
                    for (int i = 0; i < question.getAnswer().length; i++) {
                        for (int j = 0; j < answers.get(question.getQuestionNumber()).length; j++) {
                            if (answers.get(question.getQuestionNumber())[j].equals(question.getAnswer()[i])) {
                                totalScore += question.getPoints() / question.getAnswer().length;
                                current += question.getPoints() / question.getAnswer().length;
                            }
                        }
                    }
                    builder.append(current).append(" points out of ").append(question.getPoints()).append("\n");
                }
                totalPoints += question.getPoints();
            }
            builder.append("Final Score: ").append(totalScore).append(" out of ").append(totalPoints);
            return builder.toString();
        }
        return "";
    }
  
    @Override
    public void setLetterGradesCutoffs(String[] letterGrades, double[] cutoffs) {
        if (letterGrades.length != cutoffs.length) {
            throw new IllegalArgumentException("Number of letter grades must match the number of cutoffs.");
        }
    
        int numCutoffs = cutoffs.length;
    
        for (int i = 1; i < numCutoffs; i++) {
            if (cutoffs[i] >= cutoffs[i - 1]) {
                throw new IllegalArgumentException("Cutoffs must be in descending order.");
            }
        }
    
        for (int i = 0; i < numCutoffs; i++) {
            letterGradeCutoffs.put(cutoffs[i], letterGrades[i]);
        }
    }
    
    // Implement letter grade cutoffs logic
    // You can store the letter grades and cutoffs in an appropriate data structure
    // for later use in grade calculations
    
    // @Override
    // public double getCourseNumericGrade(String studentName) {
    // Student student = students.get(studentName);
    // if (student != null) {
    // double totalScore = 0.0;
    // int totalExams = student.getExamScores().size();
    //// for (int i = 0; i < student.getExamScores().size(); i++) {
    //// totalScore += student.getExamScores().get(i);
    //// }
    // for (Map.Entry<Integer, Double> entry : student.getExamScores().entrySet()) {
    // totalScore += entry.getValue();
    // }
    // return totalScore / totalExams;
    // }
    // return 0.0;
    // }
    
    public double getExamPoints(String studentName, int examId) {
        Student student = students.get(studentName);
        Exam exam = examManager.getExam(examId);
        if (student != null && exam != null) {
            StringBuilder builder = new StringBuilder();
            Map<Integer, String[]> answers = student.getExamAnswers().getOrDefault(examId, new HashMap<>());
            double totalPoints = 0.0;
            double totalScore = 0.0;
            for (Question question : exam.getQuestions()) {
                builder.append("Question #").append(question.getQuestionNumber()).append(" ");
                if (question.getClass().equals(TrueFalseQuestion.class)) {
                    answers.get(question.getQuestionNumber());
                    if (answers.get(question.getQuestionNumber())[0].equals(question.getAnswer()[0])) {
                        builder.append(question.getPoints()).append(" points out of ").append(question.getPoints())
                                .append("\n");
                        totalScore += question.getPoints();
                    } else {
                        builder.append("0.0").append(" points out of ").append(question.getPoints()).append("\n");
                    }
                } else if (question.getClass().equals(MultipleChoiceQuestion.class)) {
                    if (question.getAnswer().length != answers.get(question.getQuestionNumber()).length) {
                        builder.append("0.0").append(" points out of ").append(question.getPoints()).append("\n");
                    }
                    if (question.getAnswer().length > 1 && answers.get(question.getQuestionNumber()).length > 1) {
                        boolean b = true;
                        for (int i = 0; i < question.getAnswer().length; i++) {
                            if (answers.get(question.getQuestionNumber())[i].equals(question.getAnswer()[i])) {
                            } else {
                                b = false;
                                break;
                            }
                        }
                        if (b == true) {
                            builder.append(question.getPoints()).append(" points out of ").append(question.getPoints())
                                    .append("\n");
                            totalScore += question.getPoints();
                        } else {
                            builder.append("0.0").append(" points out of ").append(question.getPoints()).append("\n");
                        }
                    } else {
                        if (question.getAnswer().length != answers.get(question.getQuestionNumber()).length) {
                        } else if (answers.get(question.getQuestionNumber())[0].equals(question.getAnswer()[0])) {
                            builder.append(question.getPoints()).append(" points out of ").append(question.getPoints())
                                    .append("\n");
                            totalScore += question.getPoints();
                        } else {
                            builder.append("0.0").append(" points out of ").append(question.getPoints()).append("\n");
                        }
                    }
                } else {
                    double current = 0.0;
                    for (int i = 0; question.getAnswer().length; i++) {
                        for (int j = 0; answers.get(question.getQuestionNumber()).length; j++) {
                            if (answers.get(question.getQuestionNumber())[j].equals(question.getAnswer()[i])) {
                                totalScore += question.getPoints() / question.getAnswer().length;
                                current += question.getPoints() / question.getAnswer().length;
                            }
                        }
                    }
                    builder.append(current).append(" points out of ").append(question.getPoints()).append("\n");
                }
                totalPoints += question.getPoints();
            }
            builder.append("Final Score: ").append(totalScore).append(" out of ").append(totalPoints);
            return totalPoints;
        }
        return 0.0;
    }
    
        @Override
        public double getCourseNumericGrade(String studentName) {
            Student student = students.get(studentName);
            if (student != null) {
                double totalScore = 0.0;
                int totalExams = student.getExamScores().size();
                int examId = 1; // Initial exam ID
        
                for (Map.Entry<Integer, Map<Integer, String[]>> examEntry : student.getExamAnswers().entrySet()) {
                    double examScore = getExamScore(studentName, examId); // Calculate exam score using the existing method
                    double examPoints = getExamPoints(studentName, examId);
                    totalScore += examScore / examPoints * 100;
                    examId++; // Increment exam ID for the next iteration
                }
        
                return totalScore / totalExams;
            }
            return 0.0;
        }
        
        private double getExamScored(String studentName, int examId) {
            Student student = students.get(studentName);
            Exam exam = examManager.getExam(examId);
            if (student != null && exam != null) {
                double totalScore = 0.0;
                Map<Integer, String[]> answers = student.getExamAnswers().getOrDefault(examId, new HashMap<>());
                for (Question question : exam.getQuestions()) {
                    String[] studentAnswer = answers.get(question.getQuestionNumber());
                    if (studentAnswer != null) {
                        if (studentAnswer[0] == question.getAnswer()[0]) {
                            totalScore += question.getPoints();
                        }
                    }
                }
                return totalScore;
            }
            return 0.0;
        }
        
        @Override
        public String getCourseLetterGrade(String studentName) {
            double numericGrade = getCourseNumericGrade(studentName);
            String letterGrade = "";
            for (Map.Entry<Double, String> entry : letterGradeCutoffs.entrySet()) {
                double cutoff = entry.getKey();
                String grade = entry.getValue();
        
                if (numericGrade >= cutoff) {
                    letterGrade = grade;
                }
            }
        
            return letterGrade;
        }
        
        @Override
        public String getCourseGrades() {
            StringBuilder builder = new StringBuilder();
            // Retrieve the grades for each student
            for (Map.Entry<String, Student> entry : students.entrySet()) {
                String student
        
        Name = entry.getKey();
                double numericGrade = getCourseNumericGrade(studentName);
                String letterGrade = getCourseLetterGrade(studentName);
                // builder.insert(0, studentName).insert(0, " ").insert(0, numericGrade).insert(0, " ")
                // .insert(0, letterGrade).insert(0, "\n");
                builder.insert(0, letterGrade).insert(0, " ").insert(0, numericGrade).insert(0, " ").insert(0, studentName)
                        .insert(0, "\n");
                // builder.append(studentName).append(" ").append(numericGrade).append(" ").append(letterGrade).append("\n");
            }
            // builder.delete(0, 1);
            return builder.toString();
        }
        
        @Override
        public double getMaxScore(int examId) {
            Exam exam = examManager.getExam(examId);
            if (exam != null) {
                double maxScore = Double.MIN_VALUE;
                for (Map.Entry<String, Student> entry : students.entrySet()) {
                    Student student = entry.getValue();
                    double examScore = getExamScore(student.getName(), examId);
                    maxScore = Math.max(maxScore, examScore);
                }
                return maxScore;
            }
            return 0.0;
        }
        
        @Override
        public double getMinScore(int examId) {
            Exam exam = examManager.getExam(examId);
            if (exam != null) {
                double minScore = Double.MAX_VALUE;
                for (Map.Entry<String, Student> entry : students.entrySet()) {
                    Student student = entry.getValue();
                    double examScore = getExamScore(student.getName(), examId);
                    minScore = Math.min(minScore, examScore);
                }
                return minScore;
            }
            return 0.0;
        }
        
        @Override
        public double getAverageScore(int examId) {
            Exam exam = examManager.getExam(examId);
            if (exam != null) {
                double totalScore = 0.0;
                int studentCount = 0;
                for (Map.Entry<String, Student> entry : students.entrySet()) {
                    Student student = entry.getValue();
                    double examScore = getExamScore(student.getName(), examId);
                    totalScore += examScore;
                    studentCount++;
                }
                if (studentCount > 0) {
                    return totalScore / studentCount;
                }
            }
            return 0.0;
        }
        
        @Override
        public void saveManager(Manager manager, String fileName) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
                outputStream.writeObject(manager);
                // System.out.println("Manager object saved successfully.");
            } catch (IOException e) {
                // System.out.println("Failed to save Manager object: " + e.getMessage());
            }
        }
        
        @Override
        public Manager restoreManager(String fileName) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
                Manager manager = (Manager) inputStream.readObject();
                // System.out.println("Manager object restored successfully.");
                return manager;
            } catch (IOException | ClassNotFoundException e) {
                // System.out.println("Failed to restore Manager object: " + e.getMessage());
            }
            return null;
        }
        
        private TrueFalseQuestion findTrueFalseQuestion(Exam exam, int questionNumber) {
            for (Question question : exam.getQuestions()) {
                if (question instanceof TrueFalseQuestion && question.getQuestionNumber() == questionNumber) {
                    return (TrueFalseQuestion) question;
                }
            }
            return null;
        }
        
        private MultipleChoiceQuestion findMultipleChoiceQuestion(Exam exam, int questionNumber) {
            for (Question question : exam.getQuestions()) {
                if (question instanceof MultipleChoiceQuestion && question.getQuestionNumber() == questionNumber) {
                    return (MultipleChoiceQuestion) question;
                }
            }
            return null;
        }
        
        private FillInTheBlanksQuestion findFillInTheBlanksQuestion(Exam exam, int questionNumber) {
            for (Question question : exam.getQuestions()) {
                if (question instanceof FillInTheBlanksQuestion && question.getQuestionNumber() == questionNumber) {
                    return (FillInTheBlanksQuestion) question;
                }
            }
            return null;
        }
}
