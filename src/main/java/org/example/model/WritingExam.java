package org.example.model;

public class WritingExam extends Exam{
    private String textRequirement;

    public WritingExam(int id, String courseName, int teacher)
    {
        super(id, courseName, teacher);
    }

    public String getRequirement(){ return textRequirement;}
    public void setRequirement(String text) {this.textRequirement=text;}

    @Override
    public String toString() {
        return "Writing course{" +
                "id=" + this.getId() +
                ", name='" + this.getExamName() + '\'' +
                '}';
    }
}
