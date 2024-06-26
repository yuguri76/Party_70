package camp.model;

public class Score {
    private String scoreId;
    private Student student;
    private Subject subject;
    private int round;
    private int score;
    private char grade;


    public Score (String scoreId, Student student, Subject subject, int round, int score) {
        this.scoreId = scoreId;
        this.student = student;
        this.subject = subject;
        this.round = round;
        this.score = score;

        setGrade(score);
    }

    // Getter
    public String getScoreId() {
        return scoreId;
    }
    public int getScore() {
        return score;
    }
    public int getRound() {
        return round;
    }
    public char getGrade() {
        return grade;
    }

    public Student getStudent() {
        return student;
    }
    public Subject getSubject() {
        return subject;
    }

    // Setter
    public void setScore(int score) {
        this.score = score;
        this.setGrade(score);
    }

    public void setGrade(int score) {
        String type = subject.getSubjectType();
        switch (type) {
            case "MANDATORY" :
                if (score <= 100) {
                    grade = 'A';
                }
                if (score < 95) {
                    grade = 'B';
                }
                if (score < 90) {
                    grade = 'C';
                }
                if (score < 80) {
                    grade = 'D';
                }
                if (score < 70) {
                    grade = 'F';
                }
                if (score < 60) {
                    grade = 'N';
                }
                break;
            case "CHOICE" :
                if (score <= 100) {
                    grade = 'A';
                }
                if (score < 90) {
                    grade = 'B';
                }
                if (score < 80) {
                    grade = 'C';
                }
                if (score < 70) {
                    grade = 'D';
                }
                if (score < 60) {
                    grade = 'F';
                }
                if (score < 50) {
                    grade = 'N';
                }
                break;
        }

    }
}

