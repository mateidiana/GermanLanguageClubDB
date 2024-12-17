package org.example.service;
import java.util.*;

import org.example.model.*;
import org.example.model.Exceptions.EntityNotFoundException;
import org.example.model.Exceptions.ValidationException;
import org.example.repo.IRepository;

public class ReadingExamService {
    private final IRepository<ReadingExam> readingExamRepo;

    private final IRepository<Student> studentRepo;

    private final IRepository<Teacher> teacherRepo;

    private final IRepository<Question> questionRepo;

    private final IRepository<ExamResult> examResultRepo;

    public ReadingExamService(IRepository<ReadingExam> readingExamRepo, IRepository<Student> studentRepo, IRepository<Teacher> teacherRepo, IRepository<Question> questionRepo, IRepository<ExamResult> examResultRepo) {
        this.readingExamRepo = readingExamRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        this.questionRepo = questionRepo;
        this.examResultRepo=examResultRepo;
    }

    public Student getStudentById(int studentId){
        idDataCheck(studentId);
        for (Student student : studentRepo.getAll()) {
            if (student.getId()==studentId)
                return student;
        }
        throw new EntityNotFoundException("Student not found!");
    }

    public Teacher getTeacherById(int teacherId){
        idDataCheck(teacherId);
        for (Teacher teacher : teacherRepo.getAll()) {
            if (teacher.getId()==teacherId)
                return teacher;
        }
        throw new EntityNotFoundException("Teacher not found!");
    }

    public ReadingExam getReadingExamById(int readingId){
        idDataCheck(readingId);
        for (ReadingExam readingExam : readingExamRepo.getAll()) {
            if (readingExam.getId()==readingId)
                return readingExam;
        }
        throw new EntityNotFoundException("Reading exam not found!");
    }

    public Question getQuestionById(int questionId){
        idDataCheck(questionId);
        for (Question question : questionRepo.getAll()) {
            if (question.getId()==questionId)
                return question;
        }
        throw new EntityNotFoundException("Question not found!");
    }

    public ExamResult getExamResultById(int resultId){
        idDataCheck(resultId);
        for (ExamResult result:examResultRepo.getAll())
        {
            if (result.getId()==resultId)
                return result;
        }
        throw new EntityNotFoundException("Exam Result not found!");
    }

    public List<Question> getExercises(int examId){
        List<Question> questions=new ArrayList<>();
        for (Question q:questionRepo.getAll())
            if (q.getReadingExamId()==examId)
                questions.add(q);
        return questions;
    }

    public List<ExamResult> getResults(int studentId){
        List<ExamResult> results=new ArrayList<>();
        for (ExamResult result:examResultRepo.getAll())
            if (result.getStudent()==studentId)
                results.add(result);
        return results;
    }

    public List<Question> takeReadingExam(int studentId, int examId){
        idDataCheck(studentId);
        idDataCheck(examId);
//        if (getStudentById(studentId).getReadingCourses().isEmpty())
//            return new ArrayList<>();
        //return getReadingExamById(examId).getExercises();
        return getExercises(examId);
    }

    public String handleAnswer(int studentId, int questionId, String answer){
        idDataCheck(studentId);
        idDataCheck(questionId);
        stringDataCheck(answer);
        answerDataCheck(answer);
        Question question=getQuestionById(questionId);
        if (answer.equals(question.getRightAnswer()))
            return "Correct!";
        else
            return "Wrong!";
    }

    public List<ExamResult> showReadingExamResults(int studentId){
        idDataCheck(studentId);
        List<ExamResult> allResults=getResults(studentId);
        List<ExamResult> allReadingResults=new ArrayList<>();
        for (ExamResult result:allResults)
            if (getReadingExamById(result.getExam())!=null)
                allReadingResults.add(result);
        return allReadingResults;
    }

    public void addResult(int studentId, int examId, Float result){
        idDataCheck(studentId);
        idDataCheck(examId);
        int nextId=examResultRepo.getAll().size();
        ExamResult examResult=new ExamResult(nextId, examId, result, studentId);
        examResultRepo.create(examResult);

//        Student student=getStudentById(studentId);
//        student.getResults().add(examResult);
//        studentRepo.update(student);
    }

    public List<ReadingExam> showAllReadingExams(){
        return readingExamRepo.getAll();
    }

    public List<ReadingExam> examsOfATeacher(int teacherId){
        idDataCheck(teacherId);
        List<ReadingExam> exams=new ArrayList<>();
        for (ReadingExam exam:readingExamRepo.getAll())
            if (exam.getTeacher()==teacherId)
                exams.add(exam);
        return exams;
    }

    //show results of all students on a reading exam of a teacher
    public List<ExamResult> showAllResultsOfTeacherExam(int teacherId, int examId){
        idDataCheck(teacherId);
        idDataCheck(examId);
        List<ExamResult> allReadingResults=new ArrayList<>();
        if (getReadingExamById(examId).getTeacher()==teacherId){
            for (Student student:studentRepo.getAll())
                for (ExamResult result:getResults(student.getId()))
                    if(result.getExam()==examId)
                        allReadingResults.add(result);
        }
        return allReadingResults;

    }

    public boolean removeReadingExam(int examId, int teacherId) {
        idDataCheck(teacherId);
        idDataCheck(examId);
        ReadingExam exam = getReadingExamById(examId);
        if (exam.getTeacher()==teacherId){
            readingExamRepo.delete(examId);
            return true;
        }
        else
            return false;
    }

    public void createOrUpdateReadingExam(int examId, int teacherId, String examName){
        idDataCheck(teacherId);
        idDataCheck(examId);
        stringDataCheck(examName);
        int found=0;
        for (ReadingExam exam: readingExamRepo.getAll()){
            if (exam.getId()==examId)
            {
                found=1;
                updateReadingExam(examId,teacherId,examName);
                return;
            }
        }
        if (found==0){
            createReadingExam(examId,teacherId,examName);
        }
    }

    public void createReadingExam(int examId, int teacherId, String examName){
        ReadingExam e1=new ReadingExam(examId,examName,teacherId);
        readingExamRepo.create(e1);

        int nextId=questionRepo.getAll().size();
        Question q1=new Question(nextId,"Der Diener kann auf alle Fragen des Ich-Erzählers antworten.","falsch");
        questionRepo.create(q1);
        q1.setReadingExamId(examId);
        questionRepo.update(q1);
        //List<Question> questions=new ArrayList<>();
        //questions.add(q1);
        //e1.setExercises(questions);
        e1.setText("Ich befahl mein Pferd aus dem Stall zu holen. Der Diener verstand mich nicht.\nIch ging selbst in den Stall, sattelte mein Pferd und bestieg es. In der Ferne hörte ich eine Trompete blasen,\nich fragte ihn, was das bedeute. Er wusste nichts und hatte nichts gehört. Beim Tore hielt er mich auf und fragte:\n\"Wohin reitest du, Herr?\" \"Ich weiß es nicht,\" sagte ich, \"nur weg von hier. Immerfort weg von hier, nur so kann ich\nmein Ziel erreichen.\" \"Du kennst also dein Ziel?\" fragte er. \"Ja,\" antwortete ich, \"ich sagte es doch: »Weg-von-hier«,\ndas ist mein Ziel.\" \"Du hast keinen Essvorrat mit,\" sagte er. \"Ich brauche keinen,\" sagte ich, \"die Reise ist so lang,\ndass ich verhungern muss, wenn ich auf dem Weg nichts bekomme. Kein Essvorrat kann mich retten. Es ist ja zum Glück eine\nwahrhaft ungeheure Reise.\"");


        e1.setTextTitle("Der Aufbruch");
        e1.setTextAuthor("Franz Kafka");
        readingExamRepo.update(e1);
    }

    public void updateReadingExam(int examId, int teacherId,String courseName){
        ReadingExam exam=getReadingExamById(examId);

        exam.setExamName(courseName);
        exam.setTeacher(teacherId);

        int nextId=questionRepo.getAll().size();
        Question q1=new Question(nextId,"Der Diener kann auf alle Fragen des Ich-Erzählers antworten.","falsch");
        questionRepo.create(q1);
        q1.setReadingId(examId);
        questionRepo.update(q1);

        //List<Question> questions=new ArrayList<>();
        //questions.add(q1);
        //exam.setExercises(questions);
        exam.setText("Ich befahl mein Pferd aus dem Stall zu holen. Der Diener verstand mich nicht.\nIch ging selbst in den Stall, sattelte mein Pferd und bestieg es. In der Ferne hörte ich eine Trompete blasen,\nich fragte ihn, was das bedeute. Er wusste nichts und hatte nichts gehört. Beim Tore hielt er mich auf und fragte:\n\"Wohin reitest du, Herr?\" \"Ich weiß es nicht,\" sagte ich, \"nur weg von hier. Immerfort weg von hier, nur so kann ich\nmein Ziel erreichen.\" \"Du kennst also dein Ziel?\" fragte er. \"Ja,\" antwortete ich, \"ich sagte es doch: »Weg-von-hier«,\ndas ist mein Ziel.\" \"Du hast keinen Essvorrat mit,\" sagte er. \"Ich brauche keinen,\" sagte ich, \"die Reise ist so lang,\ndass ich verhungern muss, wenn ich auf dem Weg nichts bekomme. Kein Essvorrat kann mich retten. Es ist ja zum Glück eine\nwahrhaft ungeheure Reise.\"");
        exam.setTextTitle("Der Aufbruch");
        exam.setTextAuthor("Franz Kafka");

        readingExamRepo.update(exam);
    }


    public List<Student> filterStudentsByPassingGradeOnReadingExam(int examId){
        idDataCheck(examId);
        List<Student> filteredStud=new ArrayList<>();
        for (Student stud:studentRepo.getAll())
            for (ExamResult result:getResults(stud.getId()))
                if (result.getExam()==examId)
                    if (result.getResult()>=5.0)
                        filteredStud.add(stud);
        return filteredStud;
    }

    public String getText(int examId){
        idDataCheck(examId);
        return getReadingExamById(examId).getText();
    }

    public void idDataCheck(int id){
        if (id<1)
            throw new ValidationException("Id cannot be less than 1!");
    }

    public void stringDataCheck(String string){
        if (string.isEmpty())
            throw new ValidationException("Name cannot be an empty string!");
    }

    public void answerDataCheck(String string){
        if (!string.equals("wahr") && !string.equals("falsch"))
            throw new ValidationException("Invalid answer!");
    }

    public void intDataCheck(int number){
        if (number<1)
            throw new ValidationException("Number cannot be null!");
    }

}
