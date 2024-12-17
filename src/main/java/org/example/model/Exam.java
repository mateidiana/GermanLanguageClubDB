package org.example.model;
import java.util.ArrayList;
import java.util.List;

public class Exam extends Entity{

    private String examName;

    private int teacherId;


    public Exam(int id, String examName, int teacherId) {
        super(id);
        this.examName = examName;
        this.teacherId = teacherId;

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


    @Override
    public String toString() {
        return "Exam{" +
                super.toString() +
                ", courseName='" + examName + '\'' +
                ", teacher=" + teacherId +
                '}';
    }
}
