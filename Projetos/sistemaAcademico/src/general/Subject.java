package general;

import utils.Global;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Subject {
    private String code;
    private String name;
    private int hours;

    public Subject(String code, String name, int hours) {
        this.code = code;
        this.name = name;
        this.hours = hours;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public static String getCurrentPeriod() {
        LocalDate currentDate = LocalDate.now(ZoneId.systemDefault());
        String currentPeriod = currentDate.getMonthValue() > 6 ? "2" : "1";

        return String.format("%d.%s", currentDate.getYear(), currentPeriod);
    }

    public static SubjectStudent createSubjectStudent(Subject subject, Student student, ClassRoom classRoom) {
        return new SubjectStudent(
                subject.getCode(),
                subject.getName(),
                subject.getHours(),
                student.getId(),
                classRoom.getId()
        );
    }

    public static List<SubjectStudent> mapStudentsToSubjectStudent(List<Student> list, Subject subject, ClassRoom classRoom) {
        return list
                .stream()
                .map(student -> createSubjectStudent(subject, student, classRoom))
                .collect(Collectors.toList());
    }

    public static CollegeClass createCollegeClass(Subject subject, Teacher teacher, ClassRoom classRoom) {
        return new CollegeClass(
            subject.getCode(),
            subject.getName(),
            subject.getHours(),
            teacher.getId(),
            classRoom.getId()
        );
    }

    public static List<CollegeClass> mapAllToCollegeClass(List<Subject> list, Teacher teacher, ClassRoom classRoom) {
        return list
                .stream()
                .map(subject -> createCollegeClass(subject, teacher, classRoom))
                .collect(Collectors.toList());
    }




}