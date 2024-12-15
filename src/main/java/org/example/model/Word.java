package org.example.model;

public class Word extends Entity{
    private String word;
    private String meaning;
    private int vocabId;
    private int vocabExamId;
    public Word(int id, String word, String meaning)
    {
        super(id);
        this.word=word;
        this.meaning=meaning;
    }

    public String getWord(){return word;}
    public void setWord(String word){this.word=word;}
    public String getMeaning(){return meaning;}
    public void setMeaning(String meaning){this.meaning=meaning;}

    public String toString(){
        return "\n"+word+":"+"\n";
    }
}
