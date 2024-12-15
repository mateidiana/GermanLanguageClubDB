package org.example.model;

public class ExamResult extends Entity{
    //private Exam exam;
    private int examId;
    private int studentId;
    private Float result;

    public ExamResult(int id, int examId, Float result, int studentId)
    {
        super(id);
        this.examId=examId;
        this.result=result;
        this.studentId=studentId;
    }

    public int getExam(){return examId;}
    public void setExam(int examId){this.examId=examId;}

    public int getStudent(){return studentId;}
    public void setStudent(int studentId){this.studentId=studentId;}

    public Float getResult(){return result;}
    public void setResult(Float result){this.result=result;}

    public String toString(){
        return examId+": "+result;
    }
}
