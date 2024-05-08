package camp;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static camp.CampManagementApplication.*;


public class GradeInquiry {
    public static void inquireRoundGradeBySubject() {
        Scanner sc = new Scanner(System.in);

        //입력된 수강생 찾기
        String studentId;
        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
        String id = sc.next();
        if (getStudentStore().stream().noneMatch((Student s) -> s.getStudentId().equals(id))) {
            studentId = null;
        } else {
            studentId = id;
        }

        //미등록 수강생 처리
        if (studentId == null) {
            System.out.println("\n해당 수강생이 없습니다.");
            System.out.println("\n등급 조회 종료\n");
            return;
        }

        //아이디로 학생찾기
        Student resultStudent = getStudentStore().stream().filter((Student s) -> s.getStudentId().equals(studentId)).toList().get(0);

        /*조회할 과목 선택 후 회차별 등급 조회*/
        System.out.println("\n다음은 " + resultStudent.getStudentName() + " 학생의 수강 과목입니다.");
        System.out.printf("%-10s%-10s%-20s%n", "과목ID", "과목타입", "과목이름");
        System.out.println("----------------------------------");
        for (Subject subject : resultStudent.getEnrolledSubjects()) {
            String subjectType = subject.getSubjectType();
            String subjectName = subject.getSubjectName();
            String subjectId = subject.getSubjectId();
            System.out.printf("%-10s%-15s%-20s%n", subjectId, subjectType, subjectName);
        }

        /*입력한 과목 회차별 등급 조회*/
        System.out.print("\n조회할 과목ID 입력해주세요. :");
        String searchID = sc.next().toUpperCase();
        Subject resultSubject = getSubjectStore().stream().filter((Subject s) -> s.getSubjectId().equals(searchID)).toList().get(0);
        System.out.println("\n" + resultSubject.getSubjectName() + " 과목의 회차별 등급을 조회합니다...");
        List<Score> resultScore = getScoreStore().stream().filter(s -> s.getStudent().equals(resultStudent) && s.getSubject().equals(resultSubject)).toList();
        if (!resultScore.isEmpty()) {
            System.out.printf("%-8s%-10s%n", "회차", "등급");
            System.out.println("------------");
            for (Score score : resultScore) {
                int round = score.getRound();
                char grade = score.getGrade();
                System.out.printf("%-10s%-10s%n", round, grade);
            }
        } else {
            System.out.println("점수가 등록되어 있지 않습니다.");
        }

        /*과목별 평균 등급을 조회*/
        System.out.print("\n과목별 평균 등급을 조회하시겠습니까? (yes 입력 시, 조회):");
        String input = sc.next();
        if (input.equals("yes")) {
            System.out.println("\n과목이름(과목타입)  :  평균등급");
            System.out.println("----------------------------------");
            for (Subject subject : resultStudent.getEnrolledSubjects()) {
                double average = 0; // 과목별 평균 점수
                char averageGrade = 0; // 과목별 평균 등급
                List<Score> subjectScore = getScoreStore().stream().filter(s -> s.getStudent().equals(resultStudent) && s.getSubject().equals(subject)).toList();

                for (Score score : subjectScore) {
                    average += score.getScore();
                }
                average /= subjectScore.size();

                Score averageScore = new Score(null, null, subject, 0, (int) Math.round(average));
                averageGrade = averageScore.getGrade();

                System.out.print(subject.getSubjectName() + "(" + subject.getSubjectType() + ")  :  ");
                if (Double.isNaN(average)) {
                    System.out.println("점수 미등록");
                } else {
                    System.out.println(averageGrade);
                }
            }
        }
        /*다시 메인으로 돌아가기 전 출력문구*/
        System.out.println("\n등급 조회 종료");
    }
}
