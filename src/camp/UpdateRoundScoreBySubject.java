package camp;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.List;
import java.util.Scanner;

import static camp.CampManagementApplication.*;

public class UpdateRoundScoreBySubject {
    private static Scanner sc = new Scanner(System.in);
    public static void updateRoundScoreBySubject() {
        System.out.println("점수를 수정합니다...");
        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
        String studentId;
        String id = sc.next();
        if (getStudentStore().stream().noneMatch((Student s) -> s.getStudentId().equals(id))) {
             studentId = null;
        } else {
            studentId = id;
        }
        sc.nextLine();

        // 학생의 수강과목 조회 및 출력
        Student targetStudent = getStudentStore().stream().filter((Student s) -> s.getStudentId().equals(studentId)).findFirst().get();
        System.out.println("\n다음은 " + targetStudent.getStudentName() + " 학생의 수강 과목입니다.");
        System.out.printf("%-10s%-10s%-20s%n", "과목ID", "과목타입", "과목이름");
        System.out.println("----------------------------------");
        for (Subject subject : targetStudent.getEnrolledSubjects()) {
            String subjectType = subject.getSubjectType();
            String subjectName = subject.getSubjectName();
            String subjectId = subject.getSubjectId();
            System.out.printf("%-10s%-15s%-20s%n", subjectId, subjectType, subjectName);
        }

        // 점수 저장소에서 수강생으로 필터링 : 수강생
        List<Score> studentScoreList = getScoreStore().stream()
                .filter(score -> score.getStudent().getStudentId().equals(studentId)).toList();

        // 기능 구현 (수정할 과목 및 회차, 점수)
        System.out.println("수정할 과목을 입력해주세요.");
        String subjectId = sc.nextLine();

        // 존재하는 과목인지 검증
        if (getSubjectStore().stream().noneMatch((Subject s) -> s.getSubjectId().equals(subjectId))) {
            System.out.println("존재하지 않는 과목입니다.");
            return;
        } // 수강생이 수강하는 과목인지 검증
        else if (targetStudent.getEnrolledSubjects().stream().noneMatch((Subject s) -> s.getSubjectId().equals(subjectId))) {
            System.out.println("학생이 수강하지 않은 과목입니다.");
            return;
        }

        // 지정된 과목 이름 할당
        String subjectName = getSubjectStore().stream().filter((Subject s) ->
                s.getSubjectId().equals(subjectId)).findFirst().get().getSubjectName();

        // 수강생의 점수 중 해당 과목으로 필터링 : 수강생, 과목
        List<Score> studentSubjectScoreList = studentScoreList.stream()
                .filter(score -> score.getSubject().getSubjectId().equals(subjectId)).toList();
        if (studentSubjectScoreList.isEmpty()) {
            System.out.println("수강생이 이 과목의 시험을 응시하지 않았습니다.");
            return;
        }
        System.out.println(subjectName + " 과목의 점수 내역입니다.");
        studentSubjectScoreList.forEach(score -> {
                    int _round = score.getRound();
                    int _score = score.getScore();
                    System.out.println(_round+"회차 점수 : "+_score);
                }
        );

        System.out.println("수정할 회차를 입력해주세요.");
        int targetRound = sc.nextInt();
        sc.nextLine();
        // 입력이 1부터 10인지 검증
        if (targetRound > 10 || targetRound < 0) {
            System.out.println("1부터 10까지의 회차를 입력하세요");
            return;
        } // 수강생이 응시한 회차인지 검증
        else if (studentSubjectScoreList.stream().noneMatch((Score s) -> s.getRound()==targetRound)) {
            System.out.println("학생이 아직 응시하지 않은 회차입니다.");
            return;
        }
        // 목표로 하는 수강생의 과목 회차 점수 score 인스턴스
        Score targetScore = studentSubjectScoreList.stream()
                .filter(score -> score.getRound() == targetRound).findAny().get();
        System.out.println("현재 점수는 "+targetScore.getScore()+"점 입니다.");
        int beforeScore = targetScore.getScore();
        System.out.println("수정할 점수를 입력해 주세요.");
        int score = sc.nextInt();
        sc.nextLine();
        if (score > 100 || score < 0) {
            System.out.println("1부터 100 까지의 점수를 입력하세요");
            return;
        }

        targetScore.setScore(score);
        int afterScore = targetScore.getScore();
        System.out.println("이전 점수 : "+beforeScore+" | 수정 점수 : "+ afterScore);
        // 기능 구현
        System.out.println("\n점수 수정 성공!");
//
//            scoreStore.forEach(score1 -> {
//                System.out.println(score1.getScoreId());
//                System.out.println(score1.getStudent().getStudentName());
//                System.out.println(score1.getSubject().getSubjectName());
//                System.out.println(score1.getRound());
//                System.out.println(score1.getScore());
//                System.out.println(score1.getGrade());
//            });
    }

}
