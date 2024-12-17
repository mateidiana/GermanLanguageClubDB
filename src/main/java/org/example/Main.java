package org.example;
import org.example.model.Exceptions.EntityNotFoundException;
import org.example.model.Exceptions.ValidationException;
import org.example.repo.*;
import org.example.service.*;
import org.example.controller.*;
import org.example.model.*;
import org.example.view.StudentView;
import org.example.view.TeacherView;
import org.example.view.View;

import java.util.Scanner;
public class Main {

    public static void testOperations(){
        IRepository<Student> studentRepo=new InMemoryRepository<>();
        StudentService studentService = new StudentService(studentRepo);
        StudentController studentController = new StudentController(studentService);


        if (studentController.createStudent(1,"Student 1"))
            System.out.println("Registration successful!");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter student name: ");
        String name = scanner.nextLine();


        try{
            if (studentController.createStudent(id,name))
                System.out.println("Registration successful!");
            else System.out.println("Id already in use!");

        } catch(ValidationException e){ System.out.println(e.getMessage());}


        System.out.println("\n");
        for (Student student:studentRepo.getAll())
        {
            System.out.println(student.getName());
        }

        try{
            Student example=studentController.getStudentById(0);
        } catch (EntityNotFoundException | ValidationException e) {System.out.println(e.getMessage());}

    }
    public static void main(String[] args) {

        IRepository<Student> studentRepo=new InMemoryRepository<>();
        IRepository<Teacher> teacherRepo=new InMemoryRepository<>();
        IRepository<Reading> readingRepo=new InMemoryRepository<>();
        IRepository<Grammar> grammarRepo=new InMemoryRepository<>();
        IRepository<Vocabulary> vocabRepo=new InMemoryRepository<>();
        IRepository<ReadingExam> readingExamRepo=new InMemoryRepository<>();
        IRepository<GrammarExam> grammarExamRepo=new InMemoryRepository<>();
        IRepository<VocabularyExam> vocabExamRepo=new InMemoryRepository<>();
        IRepository<ExamResult> examResultRepo=new InMemoryRepository<>();
        IRepository<Question> questionRepo=new InMemoryRepository<>();
        IRepository<Word> wordRepo=new InMemoryRepository<>();
        IRepository<Book> bookRepo=new InMemoryRepository<>();

        //To do insert data in repos


        StudentService studentService=new StudentService(studentRepo);
        TeacherService teacherService=new TeacherService(teacherRepo);
        ReadingService readingService=new ReadingService(readingRepo,studentRepo,teacherRepo,questionRepo,bookRepo);
        GrammarService grammarService=new GrammarService(grammarRepo,studentRepo,teacherRepo,questionRepo);
        VocabService vocabService=new VocabService(vocabRepo,studentRepo,teacherRepo,wordRepo);
        ReadingExamService readingExamService=new ReadingExamService(readingExamRepo,studentRepo,teacherRepo,questionRepo,examResultRepo);
        GrammarExamService grammarExamService=new GrammarExamService(grammarExamRepo,studentRepo,teacherRepo,questionRepo,examResultRepo);
        VocabularyExamService vocabularyExamService=new VocabularyExamService(vocabExamRepo,studentRepo,teacherRepo,wordRepo,examResultRepo);

        StudentController studentController=new StudentController(studentService);
        TeacherController teacherController=new TeacherController(teacherService);
        ReadingController readingController=new ReadingController(readingService);
        GrammarController grammarController=new GrammarController(grammarService);
        VocabularyController vocabularyController=new VocabularyController(vocabService);
        ExamController examController=new ExamController(readingExamService,grammarExamService,vocabularyExamService);

        StudentView studentView=new StudentView(studentController,readingController,examController,grammarController,vocabularyController);
        TeacherView teacherView=new TeacherView(teacherController,readingController,vocabularyController,grammarController,examController);

        View view = new View(studentView,teacherView);
        view.start();





    }
}
