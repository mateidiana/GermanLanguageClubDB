package org.example.model;

import java.util.List;


public class Teacher extends Person {
    //private List<Course> teaches;
    //private Map<Student, String> gradeWriting=new HashMap<>();
    //private Map<Student, String> feedbackWriting=new HashMap<>();
    private List<WritingFeedback> gradeWriting;
    private List<WritingFeedback> feedbackWriting;


    public Teacher(String name, int teacherId) {
        super(teacherId, name);
    }

//    public List<Course> getTaughtCourses() {
//        return teaches;
//    }
//
//    public void setTaughtCourses(List<Course> teaches) {
//        this.teaches = teaches;
//    }

//    public Map<Student, String> getGradeWriting() {
//        return gradeWriting;
//    }
//
//    public void setGradeWriting(Map<Student, String> gradeWriting) {
//        this.gradeWriting = gradeWriting;
//
//    }
//    public Map<Student, String> getFeedbackWriting() {
//        return feedbackWriting;
//    }
//
//    public void setFeedbackWriting(Map<Student, String> feedbackWriting) {
//        this.feedbackWriting = feedbackWriting;
//    }

    public List<WritingFeedback> getFeedbackWriting() {
        return feedbackWriting;
    }

    public void setFeedbackWriting(List<WritingFeedback> feedbackWriting) {
        this.feedbackWriting = feedbackWriting;
    }

    public List<WritingFeedback> getGradeWriting() {
        return gradeWriting;
    }

    public void setGradeWriting(List<WritingFeedback> gradeWriting) {
        this.gradeWriting = gradeWriting;

    }

    @Override
    public String toString() {
        return "Teacher{" + super.toString() + '}';
    }
}
