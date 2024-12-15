package org.example.model;

public class WritingFeedback extends Entity{
    //private Student student;
    private int studentId;
    //private Teacher teacher;
    private int teacherId;
    private Float feedback;
    private String writing;
    //private Exam exam;
    private int examId;

    public WritingFeedback(int id, int student, int teacher)
    {
        super(id);
        this.studentId=student;
        this.teacherId=teacher;
    }

    public int getStudent(){return studentId;}
    public void setStudent(int student){this.studentId=student;}
    public int getTeacher(){return teacherId;}
    public void setTeacher(int teacher){this.teacherId=teacher;}
    public Float getFeedback(){return feedback;}
    public void setFeedback(Float feedback){this.feedback=feedback;}
    private String getWriting(){return this.writing;}
    public void setWriting(String writing){this.writing=writing;}
    public int getExam(){return examId;}
    public void setExam(int exam){this.examId=exam;}
}
