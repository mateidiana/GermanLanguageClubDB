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

    public StudentView(StudentController studentController, ReadingController readingController, ExamController examController, GrammarController grammarController, VocabularyController vocabController){
        this.studentController=studentController;
        this.readingController=readingController;
        this.examController=examController;
        this.grammarController=grammarController;
        this.vocabController=vocabController;

    }


    public void start(){
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.print("Select an option:\n\n1. Register as new student\n2. Enroll in a course\n3. View your reading courses\n4. View your writing courses\n5. View your grammar courses\n6. View your vocabulary courses\n0. Exit\n");

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

                    int studentId=readStudentId(scanner);
                    try{
                        List<Reading> courses=readingController.showEnrolledReadingCourses(studentId);
                        if (courses.isEmpty())
                            System.out.println("You are not enrolled in any reading course!");
                        else{ for (Reading reading:courses)
                            System.out.println(reading); }

                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "2":

                    int studentId1=readStudentId(scanner);
                    int courseId=readCourseId(scanner);
                    String answer;
                    try{
                        List<Question> questions=readingController.practiceReading(studentId1,courseId);
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
                                try{System.out.println(readingController.handleAnswer(studentId1,q.getId(),answer)+"\n");
                                } catch (ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                            }
                        }

                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "3":

                    //readingController.reviewPastMistakes(readStudentId(scanner));
                    break;

                case "4":

                    //examController.showAllReadingExams();
                    //examController.takeReadingExam(readStudentId(scanner),readExamId(scanner));
                    break;

                case "5":

                    int studentId4=readStudentId(scanner);
                    try {
                        List<ExamResult> results = examController.showReadingExamResults(studentId4);

                        if (results.isEmpty())
                            System.out.println("You have taken no reading exams so far!");

                        else{ for (ExamResult result:results) System.out.println(result);}

                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "6":

                    int studentId5=readStudentId(scanner);
                    int courseId5=readCourseId(scanner);
                    try{
                        List<Book> books=readingController.viewMandatoryBooks(studentId5,courseId5);
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


    /**
     * Menu for manipulating grammar courses
     */
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
                    //grammarController.showEnrolledGrammarCourses(readStudentId(scanner));
                    break;
                case "2":
                    //grammarController.practiceGrammar(readStudentId(scanner),readCourseId(scanner));
                    break;
                case "3":
                    //grammarController.reviewPastMistakes(readStudentId(scanner),readCourseId(scanner));
                    break;
                case "4":
                    //examController.takeGrammarExam(readStudentId(scanner),readExamId(scanner));
                    break;
                case "5":
                    //examController.showGrammarResults(readStudentId(scanner));
                    break;
                default:
            }
        }
    }

    /**
     * Menu for manipulating vocabulary courses
     */
    public void vocabularyMenu(){
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.print("Select an option:\n\n1. View your vocabulary courses\n2. Practice vocabulary\n3. Review past mistakes\n4. Take vocabulary exam\n5. View past exam scores\n6. Sort courses by available slots\n0. Exit\n");

            String option = scanner.nextLine();

            switch (option) {
                case "0":
                    continueLoop = false;
                    break;
                case "1":
                    //vocabController.showEnrolledVocabularyCourses(readStudentId(scanner));
                    break;
                case "2":
                    //vocabController.practiceVocabulary(readStudentId(scanner),readCourseId(scanner));
                    break;
                case "3":
                    //vocabController.reviewPastMistakes(readStudentId(scanner),readCourseId(scanner));
                    break;
                case "4":
                    //examController.takeVocabExam(readStudentId(scanner),readExamId(scanner));
                    break;
                case "5":
                    //examController.showVocabResults(readStudentId(scanner));
                    break;
                case "6":
                    //vocabController.sortByAvailableSlotsVocab();
                default:
            }
        }
    }

    /**
     * Menu for enrolling into a course
     */
    public void enrollMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select an option:\n\n1. Enroll in a reading course\n2. Enroll in a writing course\n3. Enroll in a grammar course\n4. Enroll in a vocabulary course\n0. Exit\n");

        String option = scanner.nextLine();
        switch (option) {
            case "0":
                break;
            case "1":
                //readingController.viewCourses();
                //readingController.enrollStudent(readStudentId(scanner),readCourseId(scanner));
                break;
            case "2":
                //writingController.viewCourses();
                //writingController.enrollStudent(readStudentId(scanner),readCourseId(scanner));
                break;
            case "3":
                //grammarController.viewCourses();
                //grammarController.enrollStudent(readStudentId(scanner),readCourseId(scanner));
                break;
            case "4":
                //vocabController.viewCourses();
                //vocabController.enrollStudent(readStudentId(scanner),readCourseId(scanner));
                break;
            default:
        }
    }

    /**
     * Read inputs
     * @param scanner reads inputs
     * @return int or strings
     */
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
