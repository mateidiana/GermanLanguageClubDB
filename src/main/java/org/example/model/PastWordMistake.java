package org.example.model;

public class PastWordMistake extends Entity{
    private String word;
    private String meaning;
    private int vocabId;
    private int vocabExamId;
    private int studentId;
    public PastWordMistake(int id, String word, String meaning, int studentId)
    {
        super(id);
        this.word=word;
        this.meaning=meaning;
        this.studentId=studentId;
    }

    public String getWord(){return word;}
    public void setWord(String word){this.word=word;}
    public String getMeaning(){return meaning;}
    public void setMeaning(String meaning){this.meaning=meaning;}

    public int getStudentId(){return studentId;}
    public void setStudentId(int studentId){this.studentId=studentId;}

    public int getVocabId(){return vocabId;}
    public int getVocabExamId(){return vocabExamId;}
    public void setVocabId(int vocabId){this.vocabId=vocabId;}
    public void setVocabExamId(int vocabExamId){this.vocabExamId=vocabExamId;}

    public String toString(){
        return "\n"+word+":"+"\n";
    }
}
