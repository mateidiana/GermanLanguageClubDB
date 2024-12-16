package org.example.view;
import java.util.Scanner;
import java.util.stream.IntStream;
import org.example.controller.*;

/**
 * Displays teacher functionalities
 */
public class TeacherView {
    private TeacherController teacherController;
    private ReadingController readingController;
    private VocabularyController vocabController;
    private GrammarController grammarController;
    private ExamController examController;

    public TeacherView(TeacherController teacherController,ReadingController readingController, VocabularyController vocabController, GrammarController grammarController, ExamController examController){
        this.teacherController=teacherController;
        this.readingController=readingController;
        this.vocabController=vocabController;
        this.grammarController=grammarController;
        this.examController=examController;
    }

    /**
     * Based on options, a teacher can access functionalities for manipulating all courses, exams and results
     */
    public void start(){
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.print("Select an option:\n\n1. Register as new teacher\n2. View reading courses\n3. View writing courses\n4. View grammar courses\n5. View vocabulary courses\n0. Exit\n");

            String option = scanner.nextLine();

            switch (option) {
                case "0":
                    continueLoop = false;
                    break;
                case "1":
                    //teacherController.createTeacher(readTeacherId(scanner),readTeacherName(scanner));
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

    /**
     * Menu for manipulating reading courses
     */
    public void readingMenu(){
        Scanner scanner=new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.print("Select an option:\n\n1. View your courses\n2. View students enrolled in reading courses\n3. Create/modify a reading course\n4. Delete a reading course\n5. Create/modify a reading exam\n6. Delete a reading exam\n7. View the results on exams\n8. Add a mandatory book\n9. Show passing students\n0. Exit\n");

            String option = scanner.nextLine();

            switch (option) {
                case "0":
                    continueLoop = false;
                    break;
                case "1":
                    //readingController.viewCourseTaughtByTeacher(readTeacherId(scanner));
                    break;
                case "2":
                    //readingController.showStudentsEnrolledInReadingCourses();
                    break;
                case "3":
                    //readingController.createOrUpdateReadingCourse(readCourseId(scanner),readTeacherId(scanner),readCourseName(scanner),readMaxStudents(scanner),readExerciseSetId(scanner));
                    break;
                case "4":
                    //readingController.deleteCourse(readCourseId(scanner),readTeacherId(scanner));
                    break;
                case "5":
                    //examController.createOrUpdateReadingExam(readExamId(scanner),readTeacherId(scanner),readExamName(scanner));
                    break;
                case "6":
                    //examController.deleteReadingExam(readExamId(scanner),readTeacherId(scanner));
                    break;
                case "7":
                    //examController.showResultsOfAllStudentsOnReadingExam(readTeacherId(scanner));
                    break;
                case "8":
                    //readingController.addMandatoryBook(readTeacherId(scanner),readCourseId(scanner),readBookName(scanner));
                    break;
                case "9":
                    //examController.filterStudentsByPassingGradeReading(readCourseId(scanner));
                default:
            }
        }
    }


    /**
     * Menu for manipulating grammar courses
     */
    public void grammarMenu(){
        Scanner scanner=new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.print("Select an option:\n\n1. View your courses\n2. View students enrolled in grammar courses\n3. Create/modify a grammar course\n4. Delete a grammar course\n5. Create/modify a grammar exam\n6. Delete a grammar exam\n7. View the results on exams\n8. View students sorted by grade\n0. Exit\n");

            String option = scanner.nextLine();

            switch (option) {
                case "0":
                    continueLoop = false;
                    break;
                case "1":
                    //grammarController.viewCourseTaughtByTeacher(readTeacherId(scanner));
                    break;
                case "2":
                    //grammarController.showStudentsEnrolledInGrammarCourses();
                    break;
                case "3":
                    //grammarController.createOrUpdateGrammarCourse(readCourseId(scanner),readTeacherId(scanner),readCourseName(scanner),readMaxStudents(scanner));
                    break;
                case "4":
                    //grammarController.deleteCourse(readCourseId(scanner),readTeacherId(scanner));
                    break;
                case "5":
                    //examController.createOrUpdateGrammarExam(readExamId(scanner),readTeacherId(scanner),readExamName(scanner));
                    break;
                case "6":
                    //examController.deleteGrammarExam(readExamId(scanner),readTeacherId(scanner));
                    break;
                case "7":
                    //examController.showResultsOfAllStudentsOnGrammarExam(readTeacherId(scanner));
                    break;
                case "8":
                    //examController.sortStudentsByGrammarGrades(readExamId(scanner));
                default:
            }
        }
    }

    /**
     * Menu for manipulating vocabulary courses
     */
    public void vocabularyMenu(){
        Scanner scanner=new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.print("Select an option:\n\n1. View your courses\n2. View students enrolled in vocabulary courses\n3. Create/modify a vocabulary course\n4. Delete a vocabulary course\n5. Create/modify a vocabulary exam\n6. Delete a vocabulary exam\n7. View the results on exams\n8. Sort courses by available slots\n0. Exit\n");

            String option = scanner.nextLine();

            switch (option) {
                case "0":
                    continueLoop = false;
                    break;
                case "1":
                    //vocabController.viewCourseTaughtByTeacher(readTeacherId(scanner));
                    break;
                case "2":
                    //vocabController.showStudentsEnrolledInVocabularyCourses();
                    break;
                case "3":
                    //vocabController.createOrUpdateVocabularyCourse(readCourseId(scanner),readTeacherId(scanner),readCourseName(scanner),readMaxStudents(scanner));
                    break;
                case "4":
                    //vocabController.deleteVocabularyCourse(readCourseId(scanner),readTeacherId(scanner));
                    break;
                case "5":
                    //examController.createOrUpdateVocabularyExam(readExamId(scanner),readTeacherId(scanner),readExamName(scanner));
                    break;
                case "6":
                    //examController.deleteVocabularyExam(readExamId(scanner),readTeacherId(scanner));
                    break;
                case "7":
                    //examController.showResultsOfAllStudentsOnVocabularyExam(readTeacherId(scanner));
                    break;
                case "8":
                    //vocabController.sortByAvailableSlotsVocab();
                default:
            }
        }
    }

    /**
     * Read inputs
     * @param scanner reads inputs
     * @return int or strings
     */
    private static int readTeacherId(Scanner scanner) {
        System.out.println("Enter teacher ID: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private static int readExerciseSetId(Scanner scanner) {
        System.out.println("Enter exercises ID between 1 and 5: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private static String readTeacherName(Scanner scanner) {
        System.out.println("Enter teacher name: ");
        return scanner.nextLine();
    }

    private static String readCourseName(Scanner scanner) {
        System.out.println("Enter course name: ");
        return scanner.nextLine();
    }

    private static String readExamName(Scanner scanner) {
        System.out.println("Enter exam name: ");
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

    private static int readMaxStudents(Scanner scanner) {
        System.out.print("Enter max number of students: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private static String readBookName(Scanner scanner) {
        System.out.println("Enter book name: ");
        return scanner.nextLine();
    }



}
