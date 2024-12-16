package org.example.service;
import java.util.*;

import org.example.model.*;
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

    public ReadingExam getReadingExamById(int readingId){
        for (ReadingExam readingExam : readingExamRepo.getAll()) {
            if (readingExam.getId()==readingId)
                return readingExam;
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

    public ExamResult getExamResultById(int resultId){
        for (ExamResult result:examResultRepo.getAll())
        {
            if (result.getId()==resultId)
                return result;
        }
        return null;
    }

    public List<Question> takeReadingExam(int studentId, int examId){
        if (getStudentById(studentId).getReadingCourses().isEmpty())
            return new ArrayList<>();
        else return getReadingExamById(examId).getExercises();
    }

    public String handleAnswer(int studentId, int questionId, String answer){
        Question question=getQuestionById(questionId);
        if (answer.equals(question.getRightAnswer()))
            return "Correct!";
        else
            return "Wrong!";
    }

    public List<ExamResult> showReadingExamResults(int studentId){
        List<ExamResult> allResults=getStudentById(studentId).getResults();
        List<ExamResult> allReadingResults=new ArrayList<>();
        for (ExamResult result:allResults)
            if (getReadingExamById(result.getExam())!=null)
                allReadingResults.add(result);
        return allReadingResults;
    }

    public void addResult(int studentId, int examId, Float result){
        int nextId=examResultRepo.getAll().size();
        ExamResult examResult=new ExamResult(nextId, examId, result, studentId);
        examResultRepo.create(examResult);

        Student student=getStudentById(studentId);
        student.getResults().add(examResult);
        studentRepo.update(student);
    }

    public List<ReadingExam> showAllReadingExams(){
        return readingExamRepo.getAll();
    }

    public List<ReadingExam> examsOfATeacher(int teacherId){
        List<ReadingExam> exams=new ArrayList<>();
        for (ReadingExam exam:readingExamRepo.getAll())
            if (exam.getTeacher()==teacherId)
                exams.add(exam);
        return exams;
    }

    //show results of all students on a reading exam of a teacher
    public List<ExamResult> showAllResultsOfTeacherExam(int teacherId, int examId){
        List<ExamResult> allReadingResults=new ArrayList<>();
        if (getReadingExamById(examId).getTeacher()==teacherId){
            for (Student student:studentRepo.getAll())
                for (ExamResult result:student.getResults())
                    if(result.getExam()==examId)
                        allReadingResults.add(result);
        }
        return allReadingResults;

    }

    public boolean removeReadingExam(int examId, int teacherId) {
        ReadingExam exam = getReadingExamById(examId);
        if (exam.getTeacher()==teacherId){
            readingExamRepo.delete(examId);
            return true;
        }
        else
            return false;
    }

    public void createOrUpdateReadingExam(int examId, int teacherId, String examName, int exerciseSet){
        int found=0;
        for (ReadingExam exam: readingExamRepo.getAll()){
            if (exam.getId()==examId)
            {
                found=1;
                updateReadingExam(examId,teacherId,examName,exerciseSet);
                return;
            }
        }
        if (found==0){
            createReadingExam(examId,teacherId,examName,exerciseSet);
        }
    }

    public void createReadingExam(int examId, int teacherId, String examName, int exerciseSet){
        ReadingExam e1=new ReadingExam(examId,examName,teacherId);
        readingExamRepo.create(e1);
        if(exerciseSet==1)
        {
            int nextId=questionRepo.getAll().size();
            Question q1=new Question(nextId,"Der Diener kann auf alle Fragen des Ich-Erzählers antworten.","falsch");
            questionRepo.create(q1);
            q1.setReadingExamId(examId);
            questionRepo.update(q1);
            List<Question> questions=new ArrayList<>();
            questions.add(q1);
            e1.setExercises(questions);
            e1.setText("Ich befahl mein Pferd aus dem Stall zu holen. Der Diener verstand mich nicht.\nIch ging selbst in den Stall, sattelte mein Pferd und bestieg es. In der Ferne hörte ich eine Trompete blasen,\nich fragte ihn, was das bedeute. Er wusste nichts und hatte nichts gehört. Beim Tore hielt er mich auf und fragte:\n\"Wohin reitest du, Herr?\" \"Ich weiß es nicht,\" sagte ich, \"nur weg von hier. Immerfort weg von hier, nur so kann ich\nmein Ziel erreichen.\" \"Du kennst also dein Ziel?\" fragte er. \"Ja,\" antwortete ich, \"ich sagte es doch: »Weg-von-hier«,\ndas ist mein Ziel.\" \"Du hast keinen Essvorrat mit,\" sagte er. \"Ich brauche keinen,\" sagte ich, \"die Reise ist so lang,\ndass ich verhungern muss, wenn ich auf dem Weg nichts bekomme. Kein Essvorrat kann mich retten. Es ist ja zum Glück eine\nwahrhaft ungeheure Reise.\"");

        }
        e1.setTextTitle("Der Aufbruch");
        e1.setTextAuthor("Franz Kafka");
        readingExamRepo.update(e1);
    }

    public void updateReadingExam(int examId, int teacherId,String courseName, int exerciseSet){
        ReadingExam exam=getReadingExamById(examId);

        exam.setExamName(courseName);
        exam.setTeacher(teacherId);

        if(exerciseSet==1)
        {
            int nextId=questionRepo.getAll().size();
            Question q1=new Question(nextId,"Der Diener kann auf alle Fragen des Ich-Erzählers antworten.","falsch");
            questionRepo.create(q1);
            q1.setReadingId(examId);
            questionRepo.update(q1);

            List<Question> questions=new ArrayList<>();
            questions.add(q1);
            exam.setExercises(questions);
            exam.setText("Ich befahl mein Pferd aus dem Stall zu holen. Der Diener verstand mich nicht.\nIch ging selbst in den Stall, sattelte mein Pferd und bestieg es. In der Ferne hörte ich eine Trompete blasen,\nich fragte ihn, was das bedeute. Er wusste nichts und hatte nichts gehört. Beim Tore hielt er mich auf und fragte:\n\"Wohin reitest du, Herr?\" \"Ich weiß es nicht,\" sagte ich, \"nur weg von hier. Immerfort weg von hier, nur so kann ich\nmein Ziel erreichen.\" \"Du kennst also dein Ziel?\" fragte er. \"Ja,\" antwortete ich, \"ich sagte es doch: »Weg-von-hier«,\ndas ist mein Ziel.\" \"Du hast keinen Essvorrat mit,\" sagte er. \"Ich brauche keinen,\" sagte ich, \"die Reise ist so lang,\ndass ich verhungern muss, wenn ich auf dem Weg nichts bekomme. Kein Essvorrat kann mich retten. Es ist ja zum Glück eine\nwahrhaft ungeheure Reise.\"");
            exam.setTextTitle("Der Aufbruch");
            exam.setTextAuthor("Franz Kafka");
        }
        readingExamRepo.update(exam);
    }


    public List<Student> filterStudentsByPassingGradeOnReadingExam(int examId){
        List<Student> filteredStud=new ArrayList<>();
        for (Student stud:studentRepo.getAll())
            for (ExamResult result:stud.getResults())
                if (result.getExam()==examId)
                    if (result.getResult()>=5.0)
                        filteredStud.add(stud);
        return filteredStud;
    }

}
