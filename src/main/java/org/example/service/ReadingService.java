package org.example.service;

import org.example.model.*;
import org.example.repo.IRepository;

import java.util.ArrayList;
import java.util.List;

public class ReadingService {
    private final IRepository<Reading> readingRepo;

    private final IRepository<Student> studentRepo;

    private final IRepository<Teacher> teacherRepo;

    private final IRepository<Question> questionRepo;

    private final IRepository<Book> bookRepo;

    public ReadingService(IRepository<Reading> readingRepo, IRepository<Student> studentRepo, IRepository<Teacher> teacherRepo, IRepository<Question> questionRepo, IRepository<Book> bookRepo) {
        this.readingRepo = readingRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        this.questionRepo = questionRepo;
        this.bookRepo = bookRepo;
    }

    public Student getStudentById(int studentId){
        for (Student student : studentRepo.getAll()) {
            if (student.getId()==studentId)
                return student;
        }
        return null;
    }

    public Teacher getTeacherById(int teacherId){
        for (Teacher teacher : teacherRepo.getAll()) {
            if (teacher.getId()==teacherId)
                return teacher;
        }
        return null;
    }

    public Reading getReadingById(int readingId){
        for (Reading reading : readingRepo.getAll()) {
            if (reading.getId()==readingId)
                return reading;
        }
        return null;
    }

    public Question getQuestionById(int questionId){
        for (Question question : questionRepo.getAll()) {
            if (question.getId()==questionId)
                return question;
        }
        return null;
    }

    public void enroll(int studentId, int readingCourseId) {
        int alreadyEnrolled=0;

        Student student = getStudentById(studentId);
        Reading course = getReadingById(readingCourseId);

        for (Reading reading:student.getReadingCourses()){
            if (reading.getId()==readingCourseId)
            {
                alreadyEnrolled=1;
                break;
            }

        }

        if (alreadyEnrolled==0){
            studentRepo.delete(studentId);
            readingRepo.delete(readingCourseId);
            if (course.getAvailableSlots() > course.getEnrolledStudents().size()) {
                course.getEnrolledStudents().add(student);
                student.getReadingCourses().add(course);
                readingRepo.create(course);
                studentRepo.create(student);
            }
        }

    }

    public List<Reading> showEnrolledReadingCourses(int studentId){
        Student student=getStudentById(studentId);
        return student.getReadingCourses();
    }

    public List<Question> practiceReading(int studentId, int courseId){
        int isEnrolled=0;
        Reading course=getReadingById(courseId);
        Student student=getStudentById(studentId);
        List<Question> questions=new ArrayList<>();

        for (Reading reading: student.getReadingCourses())
            if (reading.getId()==courseId)
            {
                isEnrolled=1;
                break;
            }
        if (isEnrolled==0)
            return new ArrayList<>();
        else{
            return course.getExercises();
        }
    }

    public String handleAnswer(int studentId, int questionId, String answer){
        Question question=getQuestionById(questionId);
        if (answer.equals(question.getRightAnswer()))
            return "Correct!";
        else{
            Student student=getStudentById(studentId);
            List<Question> pastMistakes=student.getPastReadingMistakes();
            pastMistakes.add(question);
            student.setPastReadingMistakes(pastMistakes);
            studentRepo.update(student);
            return "Wrong!";
        }
    }


    public List<Question> reviewPastReadingMistakes(int studentId){
        Student student= getStudentById(studentId);
        return student.getPastReadingMistakes();
    }

    public List<Reading> getAvailableReadingCourses(){
        return readingRepo.getAll();
    }

    public List<Student> getAllStudents() {
        return studentRepo.getAll();
    }

    public List<Student> getEnrolledStudents(int courseId) {
        Reading course = getReadingById(courseId);
        return course.getEnrolledStudents();
    }

    public List<Student> showStudentsEnrolledInReadingCourses(){
        List<Student> studentList=new ArrayList<>();
        for(Student student:studentRepo.getAll())
            if (!student.getReadingCourses().isEmpty())
                studentList.add(student);
        return studentList;

    }

    public boolean removeCourse(int courseId, int teacherId) {
        Reading course = getReadingById(courseId);
        if (course.getTeacher()==teacherId){
            readingRepo.delete(courseId);
            return true;
        }
        else
            return false;
    }

    public void createOrUpdateReadingCourse(int courseId, int teacherId, String courseName, Integer maxStudents, int exerciseSet){
        int found=0;
        for (Reading course: readingRepo.getAll()){
            if (course.getId()==courseId)
            {
                found=1;
                updateReadingCourse(courseId,teacherId,courseName,maxStudents, exerciseSet);
                return;
            }
        }
        if (found==0){
            createReadingCourse(courseId,teacherId,courseName,maxStudents, exerciseSet);
        }
    }

    public void createReadingCourse(int courseId, int teacherId, String courseName, Integer maxStudents, int exerciseSet){
        Reading r1=new Reading(courseId,courseName,teacherId,maxStudents);
        readingRepo.create(r1);
        if(exerciseSet==1)
        {
            int nextId=questionRepo.getAll().size();
            Question q1=new Question(nextId,"Der Diener kann auf alle Fragen des Ich-Erzählers antworten.","falsch");
            questionRepo.create(q1);
            q1.setReadingId(courseId);
            questionRepo.update(q1);
            List<Question> questions=new ArrayList<>();
            questions.add(q1);
            r1.setExercises(questions);
            r1.setText("Ich befahl mein Pferd aus dem Stall zu holen. Der Diener verstand mich nicht.\nIch ging selbst in den Stall, sattelte mein Pferd und bestieg es. In der Ferne hörte ich eine Trompete blasen,\nich fragte ihn, was das bedeute. Er wusste nichts und hatte nichts gehört. Beim Tore hielt er mich auf und fragte:\n\"Wohin reitest du, Herr?\" \"Ich weiß es nicht,\" sagte ich, \"nur weg von hier. Immerfort weg von hier, nur so kann ich\nmein Ziel erreichen.\" \"Du kennst also dein Ziel?\" fragte er. \"Ja,\" antwortete ich, \"ich sagte es doch: »Weg-von-hier«,\ndas ist mein Ziel.\" \"Du hast keinen Essvorrat mit,\" sagte er. \"Ich brauche keinen,\" sagte ich, \"die Reise ist so lang,\ndass ich verhungern muss, wenn ich auf dem Weg nichts bekomme. Kein Essvorrat kann mich retten. Es ist ja zum Glück eine\nwahrhaft ungeheure Reise.\"");

        }
        r1.setTextTitle("Der Aufbruch");
        r1.setTextAuthor("Franz Kafka");
        readingRepo.update(r1);
    }

    public void updateReadingCourse(int courseId, int teacherId,String courseName, Integer maxStudents, int exerciseSet){
        Reading course=getReadingById(courseId);

        course.setCourseName(courseName);
        course.setTeacher(teacherId);
        course.setAvailableSlots(maxStudents);

        if(exerciseSet==1)
        {
            int nextId=questionRepo.getAll().size();
            Question q1=new Question(nextId,"Der Diener kann auf alle Fragen des Ich-Erzählers antworten.","falsch");
            questionRepo.create(q1);
            q1.setReadingId(courseId);
            questionRepo.update(q1);

            List<Question> questions=new ArrayList<>();
            questions.add(q1);
            course.setExercises(questions);
            course.setText("Ich befahl mein Pferd aus dem Stall zu holen. Der Diener verstand mich nicht.\nIch ging selbst in den Stall, sattelte mein Pferd und bestieg es. In der Ferne hörte ich eine Trompete blasen,\nich fragte ihn, was das bedeute. Er wusste nichts und hatte nichts gehört. Beim Tore hielt er mich auf und fragte:\n\"Wohin reitest du, Herr?\" \"Ich weiß es nicht,\" sagte ich, \"nur weg von hier. Immerfort weg von hier, nur so kann ich\nmein Ziel erreichen.\" \"Du kennst also dein Ziel?\" fragte er. \"Ja,\" antwortete ich, \"ich sagte es doch: »Weg-von-hier«,\ndas ist mein Ziel.\" \"Du hast keinen Essvorrat mit,\" sagte er. \"Ich brauche keinen,\" sagte ich, \"die Reise ist so lang,\ndass ich verhungern muss, wenn ich auf dem Weg nichts bekomme. Kein Essvorrat kann mich retten. Es ist ja zum Glück eine\nwahrhaft ungeheure Reise.\"");
            course.setTextTitle("Der Aufbruch");
            course.setTextAuthor("Franz Kafka");
        }
        readingRepo.update(course);
    }

    public List<Reading> viewReadingCoursesTaughtByTeacher(int teacherId){
        List<Reading> taughtCourses=new ArrayList<>();
        for(Reading course:readingRepo.getAll())
            if (course.getTeacher()==teacherId)
                taughtCourses.add(course);
        return taughtCourses;

    }

    public List<Book> viewMandatoryBooks(int courseId){
        Reading course=getReadingById(courseId);
        return course.getMandatoryBooks();
    }

    public boolean addMandatoryBook(Integer teacherId, Integer courseId, String title, String author){
        Reading course=getReadingById(courseId);
        int nextId=bookRepo.getAll().size();
        Book book=new Book(nextId, title, author);
        bookRepo.create(book);

        if(course.getTeacher()==teacherId)
        {
            course.getMandatoryBooks().add(book);
            readingRepo.update(course);
            return true;
        }
        else return false;
    }





    //in view
    //int score
    //read(courseId)
    //read(studentId)
    //List<Question> q=practiceReading(studentId, courseId)
    //if q is empty
    //System out... "You are not enrolled"
    //else
    //for (Question question: q){
    //String answer
    //print(question)
    // read(answer)
    // System out ....handleAnswer()    -> correct score++    ->>wrong
    //System out ... score
    //
    // }

    //void dataCheck(studentId, courseId)
    //if studenid<0
    //raise AttributeError()


}
