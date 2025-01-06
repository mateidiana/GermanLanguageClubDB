package org.example.model;

public class PastMistake extends Entity{
    private String question;
    private String rightAnswer;
    private int readingId;
    private int grammarId;
    private int readingExamId;
    private int grammarExamId;
    private int studentId;

    public PastMistake(int id, String question, String rightAnswer, int studentID)
    {
        super(id);
        this.question=question;
        this.rightAnswer=rightAnswer;
        this.studentId=studentId;
    }

    public String getPastMistake(){return question;}
    public void setPastMistake(String question){this.question=question;}

    public String getRightAnswer(){return rightAnswer;}
    public void setRightAnswer(String answer){this.rightAnswer=answer;}

    public int getStudentId(){return studentId;}
    public void setStudentId(int studentId){this.studentId=studentId;}

    public int getReadingId(){return readingId;}
    public void setReadingId(int readingId){this.readingId=readingId;}
    public int getGrammarId(){return grammarId;}
    public void setGrammarId(int grammarId){this.grammarId=grammarId;}

    public int getReadingExamId(){return readingExamId;}
    public void setReadingExamId(int readingId){this.readingExamId=readingId;}
    public int getGrammarExamId(){return grammarExamId;}
    public void setGrammarExamId(int grammarId){this.grammarExamId=grammarId;}

    public String toString(){
        return "\n"+question+"\n";
    }
}
