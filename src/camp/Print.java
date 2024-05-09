package camp;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.List;

import static camp.CampManagementApplication.getStudentStore;
import static camp.CampManagementApplication.getSubjectStore;

public class Print {
    public static void printStudentSubject(String id) {
        Student student = getStudentStore().stream().filter((Student s) -> s.getStudentId().equals(id)).findFirst().get();
        System.out.println("\n다음은 " + student.getStudentName() + " 학생의 수강 과목입니다.");
        System.out.printf("%-10s%-10s%-20s%n", "과목ID", "과목타입", "과목이름");
        System.out.println("----------------------------------");
        for (Subject subject : student.getEnrolledSubjects()) {
            String subjectType = subject.getSubjectType();
            String subjectName = subject.getSubjectName();
            String subjectId = subject.getSubjectId();
            System.out.printf("%-10s%-15s%-20s%n", subjectId, subjectType, subjectName);
        }
    }

    public static void printSubjectScore(List<Score> list, String id) {
        String subjectName = getSubjectStore().stream().filter((Subject s) ->
                s.getSubjectId().equals(id)).findFirst().get().getSubjectName();
        System.out.println(subjectName + " 과목의 점수 내역입니다.");
        for (Score score1 : list) {
            int _round = score1.getRound();
            int _score = score1.getScore();
            System.out.println(_round + "회차 점수 : " + _score);
        }
    }
}
