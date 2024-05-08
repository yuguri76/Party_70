package camp;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.List;
import java.util.Scanner;

import static camp.CampManagementApplication.*;

public class CreateScore {
    private static Scanner sc = new Scanner(System.in);
    public static void createScore() {
        System.out.println("시험 점수를 등록합니다...");
        // 기능 구현
        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
        String studentId;
        String id = sc.next();
        if (getStudentStore().stream().noneMatch((Student s) -> s.getStudentId().equals(id))) {
            System.out.println("존재하지 않는 수강생입니다.");
            studentId = null;
        } else {
            studentId = id;
        }
        sc.nextLine();

        Student student = getStudentStore().stream().filter((Student s) -> s.getStudentId().equals(studentId)).toList().get(0); // 수강중인 과목 필터링
        List<Subject> enrolledSubject = student.getEnrolledSubjects();
        System.out.println("\n다음은 " + student.getStudentName() + " 학생의 수강 과목입니다.");
        System.out.printf("%-9s%-20s%n", "과목ID", "과목이름");
        System.out.println("----------------------------");
        enrolledSubject.forEach(subject -> {
            System.out.printf("%-10s%-20s%n", subject.getSubjectId(), subject.getSubjectName());
        });
        System.out.println();
        System.out.println("과목의 번호를 입력하시오");
        String subjectId = sc.nextLine();
        if(getSubjectStore().stream().noneMatch((Subject s) -> s.getSubjectId().equals(subjectId))) {
            System.out.println("존재하지 않는 과목입니다.");
            return;
        }

        System.out.println("점수를 입력하시오");
        int score = sc.nextInt();
        if(score > 100 || score < 0) {
            System.out.println("1부터 100 까지의 점수를 입력하세요");
            return;
        }

        System.out.println("회차를 입력하시오");
        int round = sc.nextInt();
        if(round > 10 || round < 0) {
            System.out.println("1부터 10까지의 회차를 입력하세요");
            return;
        }

        Student resultStudent = getStudentStore().stream().filter((Student s) -> s.getStudentId().equals(studentId)).toList().get(0); // score객체에 저장할 student 객체 생성
        Subject resultSubject = getSubjectStore().stream().filter((Subject s) -> s.getSubjectId().equals(subjectId)).toList().get(0); // score객체에 저장할 subject 객체 생성
        if(getScoreStore().stream().anyMatch((Score s) -> { return
                s.getStudent().getStudentId().equals(studentId) &&
                        s.getSubject().getSubjectId().equals(subjectId) &&
                        s.getRound() == round;}) // 이름, 과목, 회차 셋 모두 검사해서 중복여부 확인
        ) {
            System.out.println("중복된 회차가 있습니다.");
            return;
        }

        Score scoreObject = new Score(sequence(getIndexTypeScore()), resultStudent, resultSubject, round, score);
        getScoreStore().add(scoreObject);
        System.out.println("\n점수 등록 성공!");
    }
}
