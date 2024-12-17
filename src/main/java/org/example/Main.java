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
import java.util.ArrayList;
import java.util.List;
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

//        IRepository<Student> studentRepo=new InMemoryRepository<>();
//        IRepository<Teacher> teacherRepo=new InMemoryRepository<>();
//        IRepository<Reading> readingRepo=new InMemoryRepository<>();
//        IRepository<Grammar> grammarRepo=new InMemoryRepository<>();
//        IRepository<Vocabulary> vocabRepo=new InMemoryRepository<>();
//        IRepository<ReadingExam> readingExamRepo=new InMemoryRepository<>();
//        IRepository<GrammarExam> grammarExamRepo=new InMemoryRepository<>();
//        IRepository<VocabularyExam> vocabExamRepo=new InMemoryRepository<>();
//        IRepository<ExamResult> examResultRepo=new InMemoryRepository<>();
//        IRepository<Question> questionRepo=new InMemoryRepository<>();
//        IRepository<Word> wordRepo=new InMemoryRepository<>();
//        IRepository<Book> bookRepo=new InMemoryRepository<>();
//        IRepository<Enrolled> enrolledRepo = new InMemoryRepository<>();
//        IRepository<BookBelongsToCourse> bookBelongsRepo = new InMemoryRepository<>();

        IRepository<Student> studentRepo=new StudentDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub","root","Bill4761");
        IRepository<Teacher> teacherRepo=new TeacherDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub","root","Bill4761");
        IRepository<Reading> readingRepo=new ReadingDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub","root","Bill4761");
        IRepository<Grammar> grammarRepo=new GrammarDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub","root","Bill4761");
        IRepository<Vocabulary> vocabRepo=new VocabularyDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub","root","Bill4761");
        IRepository<ReadingExam> readingExamRepo=new ReadingExamDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub","root","Bill4761");
        IRepository<GrammarExam> grammarExamRepo=new GrammarExamDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub","root","Bill4761");
        IRepository<VocabularyExam> vocabExamRepo=new VocabularyExamDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub","root","Bill4761");
        IRepository<ExamResult> examResultRepo=new ExamResultDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub","root","Bill4761");
        IRepository<Question> questionRepo=new QuestionDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub","root","Bill4761");
        IRepository<Word> wordRepo=new WordDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub","root","Bill4761");
        IRepository<Book> bookRepo=new BookDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub","root","Bill4761");
        IRepository<Enrolled> enrolledRepo = new EnrolledDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub","root","Bill4761");
        IRepository<BookBelongsToCourse> bookBelongsRepo = new BookBelongsToCourseDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub","root","Bill4761");

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
        Question question2=new Question(2,"Der Ich-Erzähler nimmt einen Essvorrat.","falsch");
        Question question3=new Question(3,"Der Ich-Erzähler unternimmt eine Reise, deren Dauer undefiniert ist.","wahr");
        Question question4=new Question(4,"Die Parabel kann eine Metapher für das Unbekannte des Lebens darstellen.","wahr");
        question1.setReadingId(1);
        question2.setReadingId(1);
        question3.setReadingId(1);
        question4.setReadingId(1);

        question1.setReadingExamId(1);
        question2.setReadingExamId(1);
        question3.setReadingExamId(1);
        question4.setReadingExamId(1);

        questionRepo.create(question1);
        questionRepo.create(question2);
        questionRepo.create(question3);
        questionRepo.create(question4);

        Question question5=new Question(5,"Du (brauchen) _ Hilfe.","brauchst");
        Question question6=new Question(6,"Ich bin _ Hause.","zu");
        Question question7=new Question(7,"Er trägt _.","bei");
        Question question8=new Question(8,"Diana (setzen)_ sich auf das Sofa.","setzt");
        Question question9=new Question(9,"Stefi klettert auf _ Baum.","den");
        Question question10=new Question(10,"Ich (besuchen) _ diese Kirche.","besuche");
        Question question11=new Question(11,"Wir spielen DOTA in _ Klasse.","der");
        Question question12=new Question(12,"Mama kocht immer (lecker)_ Essen","leckeres");
        Question question13=new Question(13,"Der Ball ist unter _ Tisch gerollt.","den");
        Question question14=new Question(14,"Mein Mann kommt immer betrunken _ Hause.","nach");

        question5.setGrammarId(10);
        question6.setGrammarId(10);
        question7.setGrammarId(10);
        question8.setGrammarId(10);
        question9.setGrammarId(10);
        question10.setGrammarId(10);
        question11.setGrammarId(10);
        question12.setGrammarId(10);
        question13.setGrammarId(10);
        question14.setGrammarId(10);

        question5.setGrammarExamId(2);
        question6.setGrammarExamId(2);
        question7.setGrammarExamId(2);
        question8.setGrammarExamId(2);
        question9.setGrammarExamId(2);
        question10.setGrammarExamId(2);
        question11.setGrammarExamId(2);
        question12.setGrammarExamId(2);
        question13.setGrammarExamId(2);
        question14.setGrammarExamId(2);

        questionRepo.create(question5);
        questionRepo.create(question6);
        questionRepo.create(question7);
        questionRepo.create(question8);
        questionRepo.create(question9);
        questionRepo.create(question10);
        questionRepo.create(question11);
        questionRepo.create(question12);
        questionRepo.create(question13);
        questionRepo.create(question14);




        //Insert data into word
        Word word1=new Word(1,"dog","Hund");
        Word word2=new Word(2,"cat","Katze");
        Word word3=new Word(3,"apple","Apfel");
        Word word4=new Word(4,"book","Buch");
        Word word5=new Word(5,"house","Haus");
        Word word6=new Word(6,"car","Auto");
        Word word7=new Word(7,"tree","Baum");
        Word word8=new Word(8,"flower","Blume");
        Word word9=new Word(9,"fish","Fish");
        Word word10=new Word(10,"dog","Hund");

        word1.setVocabId(15);
        word2.setVocabId(15);
        word3.setVocabId(15);
        word4.setVocabId(15);
        word5.setVocabId(15);
        word6.setVocabId(15);
        word7.setVocabId(15);
        word8.setVocabId(15);
        word9.setVocabId(15);
        word10.setVocabId(15);

        word1.setVocabExamId(3);
        word2.setVocabExamId(3);
        word3.setVocabExamId(3);
        word4.setVocabExamId(3);
        word5.setVocabExamId(3);
        word6.setVocabExamId(3);
        word7.setVocabExamId(3);
        word8.setVocabExamId(3);
        word9.setVocabExamId(3);
        word10.setVocabExamId(3);

        wordRepo.create(word1);
        wordRepo.create(word2);
        wordRepo.create(word3);
        wordRepo.create(word4);
        wordRepo.create(word5);
        wordRepo.create(word6);
        wordRepo.create(word7);
        wordRepo.create(word8);
        wordRepo.create(word9);
        wordRepo.create(word10);

        //Insert data into book
        Book book1=new Book(1,"Das Schloss", "Franz Kafka");
        Book book2=new Book(2,"Die Verwandlung", "Franz Kafka");
        bookRepo.create(book1);
        bookRepo.create(book2);

        //Insert data into reading
        Reading reading1=new Reading(1,"Reading1",teacher1.getId(),25);

        reading1.setText("Ich befahl mein Pferd aus dem Stall zu holen. Der Diener verstand mich nicht.\nIch ging selbst in den Stall, sattelte mein Pferd und bestieg es. In der Ferne hörte ich eine Trompete blasen,\nich fragte ihn, was das bedeute. Er wusste nichts und hatte nichts gehört. Beim Tore hielt er mich auf und fragte:\n\"Wohin reitest du, Herr?\" \"Ich weiß es nicht,\" sagte ich, \"nur weg von hier. Immerfort weg von hier, nur so kann ich\nmein Ziel erreichen.\" \"Du kennst also dein Ziel?\" fragte er. \"Ja,\" antwortete ich, \"ich sagte es doch: »Weg-von-hier«,\ndas ist mein Ziel.\" \"Du hast keinen Essvorrat mit,\" sagte er. \"Ich brauche keinen,\" sagte ich, \"die Reise ist so lang,\ndass ich verhungern muss, wenn ich auf dem Weg nichts bekomme. Kein Essvorrat kann mich retten. Es ist ja zum Glück eine\nwahrhaft ungeheure Reise.\"");
        reading1.setTextAuthor("Franz Kafka");
        reading1.setTextTitle("Der Aufbruch");
        readingRepo.create(reading1);


        BookBelongsToCourse bookBelongsToCourse1=new BookBelongsToCourse(1,1,1);
        BookBelongsToCourse bookBelongsToCourse2=new BookBelongsToCourse(2,1,2);
        bookBelongsRepo.create(bookBelongsToCourse1);
        bookBelongsRepo.create(bookBelongsToCourse2);


        ReadingExam readingExam1=new ReadingExam(1,"Reading Exam 1",teacher1.getId());
        readingExamRepo.create(readingExam1);


        Grammar grammar1=new Grammar(10,"Grammar1",teacher1.getId(),30);
        grammarRepo.create(grammar1);


        GrammarExam grammarExam1=new GrammarExam(2,"Grammar exam 1", teacher1.getId());
        grammarExamRepo.create(grammarExam1);

        Vocabulary vocabulary1=new Vocabulary(15,"Vocabulary Course 1",teacher1.getId(),15);
        vocabRepo.create(vocabulary1);

        VocabularyExam vocabularyExam1=new VocabularyExam(3,"Vocabulary Exam 1", teacher1.getId());
        vocabExamRepo.create(vocabularyExam1);


        StudentService studentService=new StudentService(studentRepo);
        TeacherService teacherService=new TeacherService(teacherRepo);
        ReadingService readingService=new ReadingService(readingRepo,studentRepo,teacherRepo,questionRepo,bookRepo,enrolledRepo,bookBelongsRepo);
        GrammarService grammarService=new GrammarService(grammarRepo,studentRepo,teacherRepo,questionRepo, enrolledRepo);
        VocabService vocabService=new VocabService(vocabRepo,studentRepo,teacherRepo,wordRepo,enrolledRepo);
        ReadingExamService readingExamService=new ReadingExamService(readingExamRepo,studentRepo,teacherRepo,questionRepo,examResultRepo,readingRepo,enrolledRepo);
        GrammarExamService grammarExamService=new GrammarExamService(grammarExamRepo,studentRepo,teacherRepo,questionRepo,examResultRepo,grammarRepo,enrolledRepo);
        VocabularyExamService vocabularyExamService=new VocabularyExamService(vocabExamRepo,studentRepo,teacherRepo,wordRepo,examResultRepo,vocabRepo,enrolledRepo);

        StudentController studentController=new StudentController(studentService);
        TeacherController teacherController=new TeacherController(teacherService);
        ReadingController readingController=new ReadingController(readingService);

        readingController.enroll(1,1);

        GrammarController grammarController=new GrammarController(grammarService);

        grammarController.enroll(1,10);

        VocabularyController vocabularyController=new VocabularyController(vocabService);

        vocabularyController.enroll(1,15);

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
