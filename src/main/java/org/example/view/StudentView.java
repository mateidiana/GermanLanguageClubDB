package org.example.view;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;
import org.example.controller.GrammarController;
import org.example.controller.ReadingController;
import org.example.controller.*;
import org.example.model.*;
import org.example.model.Exceptions.EntityNotFoundException;
import org.example.model.Exceptions.ValidationException;

/**
 * Displays student functionalities
 */
public class StudentView {
    private StudentController studentController;
    private ReadingController readingController;
    private ExamController examController;
    private GrammarController grammarController;
    private VocabularyController vocabController;

    private int userId;  //asta vine in loc de   int studentId=readStudentId(scanner);   asta

    public int getUserId() {
        return userId;
    }
    public void setUserId(int smth){
        userId=smth;
    }

    public StudentView(StudentController studentController, ReadingController readingController, ExamController examController, GrammarController grammarController, VocabularyController vocabController){
        this.studentController=studentController;
        this.readingController=readingController;
        this.examController=examController;
        this.grammarController=grammarController;
        this.vocabController=vocabController;

    }


    public void startvechi(){
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.print("Select an option:\n\n1. Register as new student\n2. Enroll in a course\n3. View your reading courses\n4. View your grammar courses\n5. View your vocabulary courses\n0. Exit\n");

            String option = scanner.nextLine();

            switch (option) {
                case "0":
                    continueLoop = false;
                    break;
                case "1":
                    int studentId=readStudentId(scanner);
                    String name=readStudentName(scanner);
                    try{
                        if (studentController.createStudent(studentId,name))
                            System.out.println("Registration successful!");
                        else System.out.println("Id already in use!");

                    } catch(ValidationException e){ System.out.println(e.getMessage());}

                    break;
                case "2":
                    enrollMenu();
                    break;
                case "3":
                    readingMenu();
                    break;
                case "4":
                    grammarMenu();
                    break;
                case "5":
                    vocabularyMenu();
                    break;
                default:
            }
        }
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.print("Select an option:\n\n1. Register as new student\n2. Sign in\n0. Exit");

            String option = scanner.nextLine();

            switch (option) {
                case "0":
                    continueLoop = false;
                    break;
                case "1":
                    int studentId=readStudentId(scanner);
                    String name=readStudentName(scanner);
                    try{
                        if (studentController.createStudent(studentId,name))
                            System.out.println("Registration successful!");
                        else System.out.println("Id already in use!");

                    } catch(ValidationException e){ System.out.println(e.getMessage());}

                    break;
                case "2":
                    System.out.print("Enter your teacher ID: ");
                    int userId=scanner.nextInt();
                    try {
                        // Attempt to retrieve the student
                        Student example = studentController.getStudentById(userId);
                        // If successful, set the user ID and proceed
                        setUserId(userId);
                        actions();
                    } catch (EntityNotFoundException e) {
                        // Handle case where student is not found
                        System.out.println("Student not found. Please register first!");
                        // Directly return to the main menu
                        return;
                    }
                    break;
                    //setUserId(userId);
                    //actions();
                default:
            }
        }
    }

    public void actions(){
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.print("Select an option:\n\n1. Enroll in a course\n2. View your reading courses\n3. View your grammar courses\n4. View your vocabulary courses\n0. Exit\n");

            String option = scanner.nextLine();

            switch (option) {
                case "0":
                    continueLoop = false;
                    break;
                case "1":
                    enrollMenu();
                    break;
                case "2":
                    readingMenu();
                    break;
                case "3":
                    grammarMenu();
                    break;
                case "4":
                    vocabularyMenu();
                    break;
                default:
            }
        }
    }

    public void readingMenu(){
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.print("Select an option:\n\n1. View your reading courses\n2. Practice reading\n3. Review past mistakes\n4. Take reading exam\n5. View past exam scores\n6. View mandatory books\n0. Exit\n");

            String option = scanner.nextLine();

            switch (option) {
                case "0":
                    continueLoop = false;
                    break;

                case "1":

                    //int studentId=readStudentId(scanner);
                    try{
                        List<Reading> courses=readingController.showEnrolledReadingCourses(getUserId());
                        if (courses.isEmpty())
                            System.out.println("You are not enrolled in any reading course!");
                        else{ for (Reading reading:courses)
                            System.out.println(reading); }

                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "2":

                    //int studentId1=readStudentId(scanner);
                    int courseId=readCourseId(scanner);
                    String answer;
                    try{
                        List<Question> questions=readingController.practiceReading(getUserId(),courseId);
                        if (questions.isEmpty())
                            System.out.println("This course has no questions!");
                        else{
                            System.out.println(readingController.getReadingById(courseId).getTextTitle());
                            System.out.println(readingController.getReadingById(courseId).getTextAuthor());
                            System.out.println(readingController.getReadingById(courseId).getText());
                            for (Question q:questions)
                            {
                                System.out.println(q);
                                answer=readAnswer(scanner);
                                try{
                                    System.out.println(readingController.handleAnswer(getUserId(),q.getId(),answer)+"\n");
                                }
                                catch (ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                            }
                        }

                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "3":
//                    int studentId3=readStudentId(scanner);
                    int courseId69=readCourseId(scanner);
                    String answer3;
                    try{
                        List<PastMistake> pastMistakes=readingController.reviewPastReadingMistakes(getUserId(), courseId69);
                        if (pastMistakes.isEmpty())
                            System.out.println("You have no past mistakes yet!");
                        else{
                            for (PastMistake pm:pastMistakes)
                            {
                                System.out.println(pm);
                                answer3=readAnswer(scanner);
                                try{System.out.println(readingController.handlePMAnswer(getUserId(),pm.getId(),answer3)+"\n");
                                } catch (ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}
                            }
                        }

                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "4":
                    List<ReadingExam> exams=examController.showAllReadingExams();
                    if (exams.isEmpty()){
                        System.out.println("There are no available reading exams!");
                        break;
                    }

                    else{
                        for (ReadingExam exam:exams)
                            System.out.println(exam);
                    }

                    //int studentId4=readStudentId(scanner);
                    int examId=readExamId(scanner);
                    String answer4;
                    float score= 2.0F;

                    try{
                        List<Question> examQuestions=examController.takeReadingExam(getUserId(),examId);
                        if (examQuestions.isEmpty()) System.out.println("This exam has no questions!");
                        else{
                            //System.out.println(examController.getText(examId));
                            for (Question q:examQuestions)
                            {
                                System.out.println(q);
                                answer4=readAnswer(scanner);
                                try{System.out.println(examController.handleReadingAnswer(getUserId(),q.getId(),answer4)+"\n");
                                    if (examController.handleReadingAnswer(getUserId(), q.getId(),answer4).equals("Correct!"))
                                        score+=2;

                                } catch (ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}
                            }
                            examController.addReadingResult(getUserId(),examId,score);
                        }

                    }catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}


                    break;

                case "5":

                    //int studentId5=readStudentId(scanner);
                    try {
                        List<ExamResult> results = examController.showReadingExamResults(getUserId());

                        if (results.isEmpty())
                            System.out.println("You have taken no reading exams so far!");

                        else{ for (ExamResult result:results) System.out.println(result);}

                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "6":

                    //int studentId6=readStudentId(scanner);
                    int courseId5=readCourseId(scanner);
                    try{
                        List<Book> books=readingController.viewMandatoryBooks(getUserId(),courseId5);
                        if (books.isEmpty())
                            System.out.println("You are not enrolled in this course!");
                        else {
                            for (Book book:books) System.out.println(book);
                        }
                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                default:
            }
        }
    }



    public void grammarMenu(){
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.print("Select an option:\n\n1. View your grammar courses\n2. Practice grammar\n3. Review past mistakes\n4. Take grammar exam\n5. View past exam scores\n0. Exit\n");

            String option = scanner.nextLine();

            switch (option) {
                case "0":
                    continueLoop = false;
                    break;
                case "1":

                    //int studentId=readStudentId(scanner);
                    try{
                        List<Grammar> courses=grammarController.showEnrolledGrammarCourses(getUserId());
                        if (courses.isEmpty())
                            System.out.println("You are not enrolled in any grammar course!");
                        else{ for (Grammar grammar:courses)
                            System.out.println(grammar); }

                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "2":

                    //int studentId1=readStudentId(scanner);
                    int courseId=readCourseId(scanner);
                    String answer;
                    try{
                        List<Question> questions=grammarController.practiceGrammar(getUserId(),courseId);
                        if (questions.isEmpty())
                            System.out.println("This course has no questions!");
                        else{
                            for (Question q:questions)
                            {
                                System.out.println(q);
                                answer=readAnswer(scanner);
                                try{System.out.println(grammarController.handleAnswer(getUserId(),q.getId(),answer)+"\n");
                                } catch (ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                            }
                        }

                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "3":

//                    int studentId3=readStudentId(scanner);
                    String answer3;
                    int courseId70=readCourseId(scanner);
                    try{
                        List<PastMistake> pastMistakes=grammarController.reviewPastGrammarMistakes(getUserId(), courseId70);
                        if (pastMistakes.isEmpty())
                            System.out.println("You have no past mistakes yet!");
                        else{
                            for (PastMistake pm:pastMistakes)
                            {
                                System.out.println(pm);
                                answer3=readAnswer(scanner);
                                try{System.out.println(grammarController.handleAnswer(getUserId(),pm.getId(),answer3)+"\n");
                                } catch (ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}
                            }
                        }

                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "4":

                    List<GrammarExam> exams=examController.showAllGrammarExams();
                    if (exams.isEmpty()){
                        System.out.println("There are no available grammar exams!");
                        break;
                    }

                    else{
                        for (GrammarExam exam:exams)
                            System.out.println(exam);
                    }

                    int studentId4=readStudentId(scanner);
                    int examId=readExamId(scanner);
                    String answer4;
                    float score= 0.0F;

                    try{
                        List<Question> examQuestions=examController.takeGrammarExam(studentId4,examId);
                        if (examQuestions.isEmpty()) System.out.println("This exam has no questions!");
                        else{
                            for (Question q:examQuestions)
                            {
                                System.out.println(q);
                                answer4=readAnswer(scanner);
                                try{System.out.println(examController.handleGrammarAnswer(studentId4,q.getId(),answer4)+"\n");
                                    if (examController.handleGrammarAnswer(studentId4, q.getId(),answer4).equals("Correct!"))
                                        score+=1;

                                } catch (ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}
                            }
                            examController.addGrammarResult(studentId4,examId,score);
                        }

                    }catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "5":

                    int studentId5=readStudentId(scanner);
                    try {
                        List<ExamResult> results = examController.showGrammarExamResults(studentId5);

                        if (results.isEmpty())
                            System.out.println("You have taken no grammar exams so far!");

                        else{ for (ExamResult result:results) System.out.println(result);}

                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;
                default:
            }
        }
    }


    public void vocabularyMenu(){
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.print("Select an option:\n\n1. View your vocabulary courses\n2. Practice vocabulary\n3. Review past mistakes\n4. Take vocabulary exam\n5. View past exam scores\n0. Exit\n");

            String option = scanner.nextLine();

            switch (option) {
                case "0":
                    continueLoop = false;
                    break;
                case "1":

                    //int studentId=readStudentId(scanner);
                    try{
                        List<Vocabulary> courses=vocabController.showEnrolledVocabCourses(getUserId());
                        if (courses.isEmpty())
                            System.out.println("You are not enrolled in any vocabulary course!");
                        else{ for (Vocabulary vocab:courses)
                            System.out.println(vocab); }

                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;
                case "2":

                    //int studentId1=readStudentId(scanner);
                    int courseId=readCourseId(scanner);
                    String answer;
                    try{
                        List<Word> questions=vocabController.practiceVocabulary(getUserId(),courseId);
                        if (questions.isEmpty())
                            System.out.println("This course has no questions!");
                        else{
                            for (Word q:questions)
                            {
                                System.out.println(q);
                                answer=readAnswer(scanner);
                                try{System.out.println(vocabController.handleAnswer(getUserId(),q.getId(),answer)+"\n");
                                } catch (ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                            }
                        }

                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;
                case "3":

//                    int studentId3=readStudentId(scanner);
//                    String answer3;
//                    try{
//                        List<PastWordMistake> pastMistakes=vocabController.reviewPastVocabMistakes(getUserId());
//                        if (pastMistakes.isEmpty())
//                            System.out.println("You have no past mistakes yet!");
//                        else{
//                            for (PastWordMistake pwm:pastMistakes)
//                            {
//                                System.out.println(pwm);
//                                answer3=readAnswer(scanner);
//                                try{System.out.println(vocabController.handleAnswer(studentId3,q.getId(),answer3)+"\n");
//                                } catch (ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}
//                            }
//                        }
//
//                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "4":

                    List<VocabularyExam> exams=examController.showAllVocabExams();
                    if (exams.isEmpty()){
                        System.out.println("There are no available grammar exams!");
                        break;
                    }

                    else{
                        for (VocabularyExam exam:exams)
                            System.out.println(exam);
                    }

                    //int studentId4=readStudentId(scanner);
                    int examId=readExamId(scanner);
                    String answer4;
                    float score= 0.0F;

                    try{
                        List<Word> examQuestions=examController.takeVocabExam(getUserId(),examId);
                        if (examQuestions.isEmpty()) System.out.println("This exam has no questions!");
                        else{
                            for (Word q:examQuestions)
                            {
                                System.out.println(q);
                                answer4=readAnswer(scanner);
                                try{System.out.println(examController.handleVocabAnswer(getUserId(),q.getId(),answer4)+"\n");
                                    if (examController.handleVocabAnswer(getUserId(), q.getId(),answer4).equals("Correct!"))
                                        score+=1;

                                } catch (ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}
                            }
                            examController.addVocabResult(getUserId(),examId,score);
                        }

                    }catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;
                case "5":

                    //int studentId5=readStudentId(scanner);
                    try {
                        List<ExamResult> results = examController.showVocabExamResults(getUserId());

                        if (results.isEmpty())
                            System.out.println("You have taken no vocabulary exams so far!");

                        else{ for (ExamResult result:results) System.out.println(result);}

                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                default:
            }
        }
    }


    public void enrollMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select an option:\n\n1. Enroll in a reading course\n2. Enroll in a grammar course\n3. Enroll in a vocabulary course\n0. Exit\n");

        String option = scanner.nextLine();
        switch (option) {
            case "0":
                break;
            case "1":
                List<Reading> readingCourses = readingController.getAvailableReadingCourses();
                if (readingCourses.isEmpty())
                    System.out.println("There are no available reading courses!");
                else{
                    for (Reading course:readingCourses)
                        System.out.println(course);

                    //int studentId=readStudentId(scanner);
                    int courseId=readCourseId(scanner);
                    try{
                        readingController.enroll(getUserId(),courseId);
                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}
                }

                break;
            case "2":
                List<Grammar> grammarCourses = grammarController.getAvailableGrammarCourses();
                if (grammarCourses.isEmpty())
                    System.out.println("There are no available grammar courses!");
                else{
                    for (Grammar course:grammarCourses)
                        System.out.println(course);

                    //int studentId2=readStudentId(scanner);
                    int courseId2=readCourseId(scanner);
                    try{
                        grammarController.enroll(getUserId(),courseId2);
                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}
                }

                break;
            case "3":
                List<Vocabulary> vocabCourses = vocabController.getAvailableVocabCourses();
                if (vocabCourses.isEmpty())
                    System.out.println("There are no available grammar courses!");
                else{
                    for (Vocabulary course:vocabCourses)
                        System.out.println(course);
                    //int studentId3=readStudentId(scanner);
                    int courseId3=readCourseId(scanner);
                    try{
                        grammarController.enroll(getUserId(),courseId3);
                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}
                }

                break;

            default:
        }
    }


    private static int readStudentId(Scanner scanner) {
        System.out.println("Enter student ID: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private static String readStudentName(Scanner scanner) {
        System.out.println("Enter student name: ");
        return scanner.nextLine();
    }

    private static int readCourseId(Scanner scanner) {
        System.out.print("Enter course ID: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private static int readExamId(Scanner scanner) {
        System.out.print("Enter exam ID: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private static String readAnswer(Scanner scanner) {
        System.out.println("Enter your answer: ");
        return scanner.nextLine();
    }

}
