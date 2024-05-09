package camp;

        import camp.model.Student;
        import camp.model.Subject;

        import java.util.List;
        import java.util.Scanner;
        import java.util.stream.Collectors;

        import static camp.CampManagementApplication.*;

public class StudentManager {
    public static Student createStudent(String studentId, String studentName, List<Subject> subjects, Scanner sc) {
        System.out.println("수강생을 등록합니다...");
        Student student = new Student(studentId, studentName);

        // 필수 과목 선택
        System.out.println("필수 과목을 선택하세요. 최소 3개 이상 선택하여야 합니다.");
        selectSubject(subjects.stream().filter(subject -> subject.getSubjectType().equals(SUBJECT_TYPE_MANDATORY)).collect(Collectors.toList()), student, 3, sc);


        // 선택 과목 선택
        System.out.println("선택 과목을 선택하세요. 최소 2개 이상 선택하여야 합니다.");
        selectSubject(subjects.stream().filter(subject -> subject.getSubjectType().equals(SUBJECT_TYPE_CHOICE)).collect(Collectors.toList()), student, 2, sc);

        System.out.println("수강생 등록 성공!");
        return student;
    }

    public static void selectSubject(List<Subject> subjects, Student student, int minRequired, Scanner sc) {
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
    public static void checkStudent(List<Student> studentStore){
        System.out.println("\n수강생 목록을 조회합니다...");

        if(studentStore.isEmpty()) {
            System.out.println("\n등록된 수강생이 없습니다.");
        }else {
            for (Student student : studentStore) {
                System.out.println("수강생 성명: " + student.getStudentName() + " || " + "수강생 ID: " + student.getStudentId());
            }
            System.out.println("\n수강생 목록 조회 성공!");
        }
    }
}