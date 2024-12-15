package org.example.model;
import java.util.ArrayList;
import java.util.List;

public class Exam extends Entity{

    private String examName;
    //private Teacher teacher;
    private int teacherId;
    private List<Student> examinedStudents;


    public Exam(int id, String examName, int teacherId) {
        super(id);
        this.examName = examName;
        this.teacherId = teacherId;
        this.examinedStudents = new ArrayList<>();

    }

    public String getExamName() {
        return examName;
    }
    public void setExamName(String examName) {
        this.examName = examName;
    }
    public int getTeacher() {
        return teacherId;
    }
    public void setTeacher(int teacherId) {
        this.teacherId = teacherId;
    }
    public List<Student> getExaminedStudents() {
        return examinedStudents;
    }
    public void setExaminedStudents(List<Student> examinedStudents) {
        this.examinedStudents = examinedStudents;
    }

    @Override
    public String toString() {
        return "Exam{" +
                super.toString() +
                ", courseName='" + examName + '\'' +
                ", teacher=" + teacherId +
                '}';
    }
}
