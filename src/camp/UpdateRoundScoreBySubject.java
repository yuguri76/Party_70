package camp;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.List;
import java.util.Scanner;

import static camp.CampManagementApplication.*;
import static camp.Print.*;

public class UpdateRoundScoreBySubject {
    private static Scanner sc = new Scanner(System.in);
    private static final int MAX_ROUND = 10;
    private static final int MIN_ROUND = 1;
    private static final int MAX_SCORE = 100;
    private static final int MIN_SCORE = 0;

    public static void updateRoundScoreBySubject() {
        System.out.println("점수를 수정합니다...");
        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
        String studentId = sc.next().toUpperCase();
        if (getStudentStore().stream().noneMatch((Student s) -> s.getStudentId().equals(studentId))) {
            System.out.println("존재하지 않는 수강생 번호 입니다.");
            return;
        }
        sc.nextLine();

        Student targetStudent = targetingStudent(studentId);
        printStudentSubject(studentId);
        List<Score> list = filteringStudent(getScoreStore(), studentId);

        System.out.println("수정할 과목을 입력해주세요.");
        String subjectId;
        subjectId = sc.nextLine().toUpperCase();
        // 존재하는 과목인지 검증
        if (getSubjectStore().stream().noneMatch((Subject s) -> s.getSubjectId().equals(subjectId))) {
            System.out.println("존재하지 않는 과목입니다.");
            return;
        } // 수강생이 수강하는 과목인지 검증
        else if (targetStudent.getEnrolledSubjects().stream().noneMatch((Subject s) -> s.getSubjectId().equals(subjectId))) {
            System.out.println("학생이 수강하지 않은 과목입니다.");
            return;
        }

        list = filteringSubject(list, subjectId);
        if (list.isEmpty()) {
            System.out.println("수강생이 이 과목의 시험을 응시하지 않았습니다.");
            return;
        }

        printSubjectScore(list, subjectId);

        System.out.println("수정할 회차를 입력해주세요.");
        int targetRound = sc.nextInt();
        sc.nextLine();
        // 입력이 1부터 10인지 검증
        if (targetRound > MAX_ROUND || targetRound < MIN_ROUND) {
            System.out.println("1부터 10까지의 회차를 입력하세요");
            return;
        } // 수강생이 응시한 회차인지 검증
        else if (list.stream().noneMatch((Score s) -> s.getRound() == targetRound)) {
            System.out.println("학생이 아직 응시하지 않은 회차입니다.");
            return;
        }

        Score score = filteringRound(list, targetRound);
        int beforeScore = score.getScore();

        System.out.println("현재 점수는 " + beforeScore + "점 입니다.");
        System.out.println();
        System.out.println("수정할 점수를 입력해 주세요.");

        int inputScore = sc.nextInt();
        sc.nextLine();
        if (inputScore > MAX_SCORE || inputScore < MIN_SCORE) {
            System.out.println("1부터 100 까지의 점수를 입력하세요");
            return;
        }

        score.setScore(inputScore);
        int afterScore = score.getScore();

        System.out.println("이전 점수 : " + beforeScore + " | 수정 점수 : " + afterScore);
        System.out.println("\n점수 수정 성공!");
    }

    // studentStore --ID--> Student
    public static Student targetingStudent(String studentId) {
        return getStudentStore().stream().
                filter((Student s) -> s.getStudentId().equals(studentId)).findFirst().get();
    }

    // List<Score> --수강생--> List<Score>
    public static List<Score> filteringStudent(List<Score> list, String studentId) {
        return list.stream().
                filter(score -> score.getStudent().getStudentId().equals(studentId)).toList();
    }

    // List<Score> --과목--> List<Score>
    public static List<Score> filteringSubject(List<Score> list, String subjectId) {
        return list.stream()
                .filter(score -> score.getSubject().getSubjectId().equals(subjectId)).toList();
    }

    // List<Score> --특정회차--> Score
    public static Score filteringRound(List<Score> list, int targetRound) {
        return list.stream()
                .filter(score -> score.getRound() == targetRound).findAny().get();
    }
}


