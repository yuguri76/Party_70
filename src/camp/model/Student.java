package camp.model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String studentId;
    private String studentName;

    private List<Subject> enrolledSubjects; // 수강과목 저장할 리스트

    public Student(String seq, String studentName) {
        this.studentId = seq;
        this.studentName = studentName;
        this.enrolledSubjects = new ArrayList<>(); //초기화
    }

    public void enrollSubject(Subject subject) { // 수강과목 목록 추가
        this.enrolledSubjects.add(subject);
    }

    // Getter
    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public List<Subject> getEnrolledSubjects() { //수강과목 getter
        return enrolledSubjects;
    }
}
