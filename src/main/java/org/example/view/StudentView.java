package org.example.view;
import java.util.Scanner;
import java.util.stream.IntStream;
import org.example.controller.GrammarController;
import org.example.controller.ReadingController;
import org.example.controller.*;
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

    /**
     * Based on options, a student can access functionalities for enrolling and practicing all courses,
     * taking exams and receiving results
     */
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

    /**
     * Menu for manipulating reading courses
     */
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
                    //readingController.showEnrolledCourses(readStudentId(scanner));
                    break;
                case "2":
                    //readingController.practiceReading(readStudentId(scanner),readCourseId(scanner));
                    break;
                case "3":
                    //readingController.reviewPastMistakes(readStudentId(scanner));
                    break;
                case "4":
                    //examController.showAllReadingExams();
                    //examController.takeReadingExam(readStudentId(scanner),readExamId(scanner));
                    break;
                case "5":
                    //examController.showReadingResults(readStudentId(scanner));
                    break;
                case "6":
                    //readingController.viewMandatoryBooks(readStudentId(scanner),readCourseId(scanner));
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

}
