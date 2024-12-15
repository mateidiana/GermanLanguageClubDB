package org.example.model;

public class Question extends Entity{
        private String question;
        private String rightAnswer;
        private int readingId;
        private int grammarId;
        private int readingExamId;
        private int grammarExamId;

        public Question(int id, String question, String rightAnswer)
        {
            super(id);
            this.question=question;
            this.rightAnswer=rightAnswer;
        }

        public String getQuestion(){return question;}
        public void setQuestion(String question){this.question=question;}

        public String getRightAnswer(){return rightAnswer;}
        public void setRightAnswer(String answer){this.rightAnswer=answer;}

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
