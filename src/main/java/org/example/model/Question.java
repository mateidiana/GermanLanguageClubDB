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

        public String toString(){
            return "\n"+question+"\n";
        }
}
