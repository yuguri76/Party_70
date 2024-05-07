package camp;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Notification
 * Java, 객체지향이 아직 익숙하지 않은 분들은 위한 소스코드 틀입니다.
 * main 메서드를 실행하면 프로그램이 실행됩니다.
 * model 의 클래스들과 아래 (// 기능 구현...) 주석 부분을 완성해주세요!
 * 프로젝트 구조를 변경하거나 기능을 추가해도 괜찮습니다!
 * 구현에 도움을 주기위한 Base 프로젝트입니다. 자유롭게 이용해주세요!
 */
public class CampManagementApplication {
    // 데이터 저장소
    private static List<Student> studentStore;
    private static List<Subject> subjectStore;
    private static List<Score> scoreStore;

    // 과목 타입
    private static String SUBJECT_TYPE_MANDATORY = "MANDATORY";
    private static String SUBJECT_TYPE_CHOICE = "CHOICE";

    // index 관리 필드
    private static int studentIndex;
    private static final String INDEX_TYPE_STUDENT = "ST";
    private static int subjectIndex;
    private static final String INDEX_TYPE_SUBJECT = "SU";
    private static int scoreIndex;
    private static final String INDEX_TYPE_SCORE = "SC";

    // 스캐너
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        setInitData();
        try {
            displayMainView();
        } catch (Exception e) {
            System.out.println("\n오류 발생!\n프로그램을 종료합니다.");
        }
    }

    // 초기 데이터 생성
    private static void setInitData() {
        studentStore = new ArrayList<>();
        subjectStore = List.of(
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Java",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "객체지향",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Spring",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "JPA",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "MySQL",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "디자인 패턴",
                        SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Spring Security",
                        SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Redis",
                        SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "MongoDB",
                        SUBJECT_TYPE_CHOICE
                )
        );
        scoreStore = new ArrayList<>();
    }

    // index 자동 증가
    private static String sequence(String type) {
        switch (type) {
            case INDEX_TYPE_STUDENT -> {
                studentIndex++;
                return INDEX_TYPE_STUDENT + studentIndex;
            }
            case INDEX_TYPE_SUBJECT -> {
                subjectIndex++;
                return INDEX_TYPE_SUBJECT + subjectIndex;
            }
            default -> {
                scoreIndex++;
                return INDEX_TYPE_SCORE + scoreIndex;
            }
        }
    }

    private static void displayMainView() throws InterruptedException {
        boolean flag = true;
        while (flag) {
            System.out.println("\n==================================");
            System.out.println("내일배움캠프 수강생 관리 프로그램 실행 중...");
            System.out.println("1. 수강생 관리");
            System.out.println("2. 점수 관리");
            System.out.println("3. 프로그램 종료");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> displayStudentView(); // 수강생 관리
                case 2 -> displayScoreView(); // 점수 관리
                case 3 -> flag = false; // 프로그램 종료
                default -> {
                    System.out.println("잘못된 입력입니다.\n되돌아갑니다!");
                    Thread.sleep(2000);
                }
            }
        }
        System.out.println("프로그램을 종료합니다.");
    }

    private static void displayStudentView() {
        boolean flag = true;
        while (flag) {
            System.out.println("==================================");
            System.out.println("수강생 관리 실행 중...");
            System.out.println("1. 수강생 등록");
            System.out.println("2. 수강생 목록 조회");
            System.out.println("3. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createStudent(); // 수강생 등록
                case 2 -> inquireStudent(); // 수강생 목록 조회
                case 3 -> flag = false; // 메인 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    flag = false;
                }
            }
        }
    }

    // 수강생 등록
    private static void createStudent() {
        System.out.println("수강생을 등록합니다...");
        String studentName = "";

        // 이름 입력받기(입력하기 전까지 다음 프롬프트로 안넘어감)
        while (studentName.isEmpty()) {
            System.out.println("수강생의 이름을 입력해주세요.");
            studentName = sc.next();

        }

        // 기능 구현 (필수 과목, 선택 과목)

        Student student = new Student(sequence(INDEX_TYPE_STUDENT), studentName); // 수강생 인스턴스 생성 예시 코드

        // 기능 구현

        //필수 과목은 자동으로 등록되어야함.
        List<Subject> mandatorySubjects = subjectStore.stream()
                        .filter(s -> s.getSubjectType().equals(SUBJECT_TYPE_MANDATORY))
                                .collect(Collectors.toList());
        System.out.println("필수 과목을 선택하세요. 최소 3개 이상 선택하여야 합니다.");
        selectSubject(mandatorySubjects, student, 3, SUBJECT_TYPE_MANDATORY);

        //선택 과목 등록
        List<Subject> choiceSubjects = subjectStore.stream()
                .filter(s -> s.getSubjectType().equals(SUBJECT_TYPE_CHOICE))
                .collect(Collectors.toList());
        System.out.println("선택 과목을 선택하세요. 최소 2개 이상 선택하여야 합니다.");
        selectSubject(choiceSubjects, student, 2, SUBJECT_TYPE_CHOICE);

        studentStore.add(student);
        System.out.println("수강생 등록 성공! 수강생 관리 화면으로 돌아갑니다.\n");
    }

    private static void selectSubject(List<Subject> subjects, Student student, int minRequired, String subjectType) {
        int selectedCount = 0;
        while (selectedCount < minRequired) {
            for (int i = 0; i < subjects.size(); i++) {
                System.out.println((i + 1) + ". " + subjects.get(i).getSubjectName());
            }
            System.out.println("선택할 과목의 번호를 입력해 주세요.");
            if (!sc.hasNextInt()) {
                System.out.println("숫자를 입력해 주세요.");
                sc.next(); // 버퍼 비우기
                continue;
            }
            int choice = sc.nextInt() - 1;
            if (choice >= 0 && choice < subjects.size()) {
                Subject selectedSubject = subjects.get(choice);
                if (!student.getEnrolledSubjects().contains(selectedSubject)) {
                    student.enrollSubject(selectedSubject);
                    selectedCount++;
                    System.out.println(selectedSubject.getSubjectName() + " 선택 완료!");
                } else {
                    System.out.println("이미 선택한 과목입니다. 다시 선택해 주세요.");
                }
            } else {
                System.out.println("잘못된 입력입니다. 다시 선택해 주세요.");
            }

            if (selectedCount < minRequired) {
                System.out.println("최소 " + minRequired + "개 이상 선택해주세요.");
            }
        }
    }


    // 수강생 목록 조회
    private static void inquireStudent() {
        System.out.println("\n수강생 목록을 조회합니다...");
        // 기능 구현
        if(studentStore.isEmpty()) {
            System.out.println("\n등록된 수강생이 없습니다.");
        }else {
            for (Student student : studentStore) {
                System.out.println("수강생 성명: " + student.getStudentName() + " || " + "수강생 ID: " + student.getStudentId());
            }
            System.out.println("\n수강생 목록 조회 성공!");
        }
    }

    private static void displayScoreView() {
        boolean flag = true;
        while (flag) {
            System.out.println("==================================");
            System.out.println("점수 관리 실행 중...");
            System.out.println("1. 수강생의 과목별 시험 회차 및 점수 등록");
            System.out.println("2. 수강생의 과목별 회차 점수 수정");
            System.out.println("3. 수강생의 특정 과목 회차별 등급 조회");
            System.out.println("4. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createScore(); // 수강생의 과목별 시험 회차 및 점수 등록
                case 2 -> updateRoundScoreBySubject(); // 수강생의 과목별 회차 점수 수정
                case 3 -> inquireRoundGradeBySubject(); // 수강생의 특정 과목 회차별 등급 조회
                case 4 -> flag = false; // 메인 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    flag = false;
                }
            }
        }
    }

    private static String getStudentId() {
        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
        return sc.next();
    }

    // 수강생의 과목별 시험 회차 및 점수 등록
    private static void createScore() {
        try {
            System.out.println("시험 점수를 등록합니다...");
            // 기능 구현
            String studentId = getStudentId(); // 관리할 수강생 고유 번호
            sc.nextLine();
            Student student = studentStore.stream().filter((Student s) -> s.getStudentId().equals(studentId)).toList().get(0);
            List<Subject> enrolledSubject = student.getEnrolledSubjects();
            System.out.println(student.getStudentName() + " 수강생의 수강 과목입니다.");
            enrolledSubject.forEach(subject -> {
                System.out.println(subject.getSubjectId() + ". " + subject.getSubjectName());
            });
            System.out.println("");
            System.out.println("과목의 번호를 입력하시오");
            String subjectId = sc.nextLine();
            if(subjectStore.stream().noneMatch((Subject s) -> s.getSubjectId().equals(subjectId))) {
                throw new CreateScoreException("존재하지 않는 과목입니다.");
            }

            System.out.println("점수를 입력하시오");
            int score = sc.nextInt();
            if(score > 100 || score < 0) {
                throw new CreateScoreException("1부터 100 까지의 점수를 입력하세요");
            }

            System.out.println("회차를 입력하시오");
            int round = sc.nextInt();
            if(round > 10 || round < 0) {
                throw new CreateScoreException("1부터 10까지의 회차를 입력하세요");
            }

            Student resultStudent = studentStore.stream().filter((Student s) -> s.getStudentId().equals(studentId)).toList().get(0);
            Subject resultSubject = subjectStore.stream().filter((Subject s) -> s.getSubjectId().equals(subjectId)).toList().get(0);
            if(scoreStore.stream().anyMatch((Score s) -> { return
                    s.getStudent().getStudentId().equals(studentId) &&
                            s.getSubject().getSubjectId().equals(subjectId) &&
                            s.getRound() == round;})
            ) {
                throw new CreateScoreException("중복된 회차가 있습니다.");
            }

            Score scoreObject = new Score(sequence(INDEX_TYPE_SCORE), resultStudent, resultSubject, round, score);
            scoreStore.add(scoreObject);
            System.out.println("\n점수 등록 성공!");
        } catch (CreateScoreException e) {
            System.out.println(e.getMessage());
        }

//        scoreStore.forEach(score1 -> {
//            System.out.println(score1.getScoreId());
//            System.out.println(score1.getStudent().getStudentName());
//            System.out.println(score1.getSubject().getSubjectName());
//            System.out.println(score1.getRound());
//            System.out.println(score1.getScore());
//            System.out.println(score1.getGrade());
//        });
    }

    // 수강생의 과목별 회차 점수 수정
    private static void updateRoundScoreBySubject() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        // 기능 구현 (수정할 과목 및 회차, 점수)
        System.out.println("시험 점수를 수정합니다...");
        // 기능 구현
        System.out.println("\n점수 수정 성공!");
    }

    // 수강생의 특정 과목 회차별 등급 조회
    private static void inquireRoundGradeBySubject() {
        String studentId = getStudentId();
        //아이디로 학생찾기
        Student resultStudent = studentStore.stream().filter((Student s) -> s.getStudentId().equals(studentId)).toList().get(0);

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
        Subject resultSubject = subjectStore.stream().filter((Subject s) -> s.getSubjectId().equals(searchID)).toList().get(0);
        System.out.println("\n" + resultSubject.getSubjectName() + " 과목의 회차별 등급을 조회합니다...");
        List<Score> resultScore = scoreStore.stream().filter(s -> s.getStudent().equals(resultStudent) && s.getSubject().equals(resultSubject)).toList();
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
                String averageGrade = null; // 과목별 평균 등급
                List<Score> subjectScore = scoreStore.stream().filter(s -> s.getStudent().equals(resultStudent) && s.getSubject().equals(subject)).toList();

                for (Score score : subjectScore) {
                    average += score.getScore();
                }
                average /= subjectScore.size();

                switch (subject.getSubjectType()) {
                    case "MANDATORY":
                        if (average <= 100) {
                            averageGrade = "A";
                        }
                        if (average < 95) {
                            averageGrade = "B";
                        }
                        if (average < 90) {
                            averageGrade = "C";
                        }
                        if (average < 80) {
                            averageGrade = "D";
                        }
                        if (average < 70) {
                            averageGrade = "F";
                        }
                        if (average < 60) {
                            averageGrade = "N";
                        }
                        break;
                    case "CHOICE":
                        if (average <= 100) {
                            averageGrade = "A";
                        }
                        if (average < 90) {
                            averageGrade = "B";
                        }
                        if (average < 80) {
                            averageGrade = "C";
                        }
                        if (average < 70) {
                            averageGrade = "D";
                        }
                        if (average < 60) {
                            averageGrade = "F";
                        }
                        if (average < 50) {
                            averageGrade = "N";
                        }
                        break;
                }
                if (averageGrade == null) {
                    averageGrade = "점수 미등록";
                }
                System.out.println(subject.getSubjectName() + "(" + subject.getSubjectType() + ")  :  " + averageGrade);
            }
        }

        /*다시 메인으로 돌아가기 전 출력문구*/
        System.out.println("\n등급 조회 종료");
    }

}
