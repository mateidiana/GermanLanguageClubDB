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
import java.sql.*;
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

        //Insert data into student
        Student student1=new Student("Student1",1);
        Student student2=new Student("Student2",2);
        Student student3=new Student("Student3",3);

        studentRepo.create(student1);
        studentRepo.create(student2);
        studentRepo.create(student3);

        //Insert data into teacher
        Teacher teacher1=new Teacher("Teacher1",1);
        Teacher teacher2=new Teacher("Teacher2",2);
        Teacher teacher3=new Teacher("Teacher3",3);

        teacherRepo.create(teacher1);
        teacherRepo.create(teacher2);
        teacherRepo.create(teacher3);


        //Insert data into question
        Question question1=new Question(1,"Der Diener kann auf alle Fragen des Ich-Erzählers antworten.","falsch");
        //Insert data into word
        //Insert data into book










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

//        try {
//            Connection connection=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_schema","root","Bill4761");
//            Statement statement=connection.createStatement();
//            ResultSet resultSet=statement.executeQuery("SELECT * FROM USERS");
//
//            while(resultSet.next()){
//                System.out.println(resultSet.getString("username"));
//                System.out.println(resultSet.getString("password"));
//            }
//        } catch(SQLException e){
//            e.printStackTrace();
//        }


    }
}
