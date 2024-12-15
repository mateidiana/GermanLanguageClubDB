package org.example.model;

public class Writing extends Course{

    private String textRequirement;

    public Writing(int id, String courseName, int teacher, Integer maxStudents)
    {
        super(id, courseName, teacher, maxStudents);
    }

    @Override
    public String toString() {
        return "Writing course{" +
                "id=" + this.getId() +
                ", name='" + this.getCourseName() + '\'' +
                '}';
    }
    public String getRequirement(){ return textRequirement;}
    public void setRequirement(String text) {this.textRequirement=text;}
}
