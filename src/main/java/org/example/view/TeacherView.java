package org.example.view;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;
import org.example.controller.*;
import org.example.model.*;
import org.example.model.Exceptions.EntityNotFoundException;
import org.example.model.Exceptions.ValidationException;


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


    public void start(){
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.print("Select an option:\n\n1. Register as new teacher\n2. View reading courses\n3. View grammar courses\n4. View vocabulary courses\n0. Exit\n");

            String option = scanner.nextLine();

            switch (option) {
                case "0":
                    continueLoop = false;
                    break;
                case "1":
                    int teacherId=readTeacherId(scanner);
                    String name=readTeacherName(scanner);
                    try{
                        if (teacherController.createTeacher(teacherId,name))
                            System.out.println("Registration successful!");
                        else System.out.println("Id already in use!");

                    } catch(ValidationException e){ System.out.println(e.getMessage());}
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
        Scanner scanner=new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.print("Select an option:\n\n1. View your courses\n2. View students enrolled in reading courses\n3. Create/modify a reading course\n4. Delete a reading course\n5. Create/modify a reading exam\n6. Delete a reading exam\n7. View the results on exams\n8. Add a mandatory book\n0. Exit\n");

            String option = scanner.nextLine();

            switch (option) {
                case "0":
                    continueLoop = false;
                    break;
                case "1":

                    int teacherId=readTeacherId(scanner);
                    try{
                        List<Reading> readingCourses=readingController.viewReadingCoursesTaughtByTeacher(teacherId);
                        if (readingCourses.isEmpty())
                            System.out.println("You don't have any reading courses!");
                        else
                            for (Reading course:readingCourses)
                                System.out.println(course);
                    }catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "2":

                    List<Student> enrolledStud=readingController.showStudentsEnrolledInReadingCourses();
                    if (enrolledStud.isEmpty())
                        System.out.println("No students are enrolled in reading coures yet!");
                    else for(Student stud:enrolledStud)
                        System.out.println(stud);

                    break;

                case "3":

                    int courseId=readCourseId(scanner);
                    int teacherId3=readTeacherId(scanner);
                    String courseName=readCourseName(scanner);
                    int maxStudents=readMaxStudents(scanner);
                    int exerciseSet=readExerciseSetId(scanner);
                    try{
                        readingController.createOrUpdateReadingCourse(courseId,teacherId3,courseName,maxStudents,exerciseSet);
                        System.out.println("Course successfully added/updated!");
                    }catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "4":

                    int courseId4=readCourseId(scanner);
                    int teacherId4=readTeacherId(scanner);
                    try{
                        if (readingController.removeCourse(courseId4,teacherId4))
                            System.out.println("Course deleted successfully!");
                        else
                            System.out.println("You don't have access to this course!");
                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}
                    break;

                case "5":

                    int examId=readExamId(scanner);
                    int teacherId5=readTeacherId(scanner);
                    String examName=readCourseName(scanner);
                    try{
                        examController.createOrUpdateReadingExam(examId,teacherId5,examName);
                        System.out.println("Exam successfully created/updated!");
                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "6":

                    int examId6=readExamId(scanner);
                    int teacherId6=readTeacherId(scanner);
                    try{
                        if (examController.removeReadingExam(examId6,teacherId6))
                            System.out.println("Exam deleted successfully!");
                        else
                            System.out.println("You don't have access to this exam!");
                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}
                    break;

                case "7":

                    int examId7=readExamId(scanner);
                    int teacherId7=readTeacherId(scanner);
                    try{
                        List<ExamResult> results=examController.showAllResultsOfReadingTeacherExam(teacherId7,examId7);
                        if (results.isEmpty())
                            System.out.println("No student took this exam yet!");
                        else for (ExamResult result:results)
                            System.out.println(result);
                    }catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}
                    break;

                case "8":
                    int teacherId8=readTeacherId(scanner);
                    int courseId8=readCourseId(scanner);
                    String bookName=readBookName(scanner);
                    String bookAuthor=readBookName(scanner);
                    try {
                        if (readingController.addMandatoryBook(teacherId8,courseId8,bookName,bookAuthor))
                            System.out.println("Book added successfully");
                        else System.out.println("You don't have access to this course!");
                    }catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                default:
            }
        }
    }



    public void grammarMenu(){
        Scanner scanner=new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.print("Select an option:\n\n1. View your courses\n2. View students enrolled in grammar courses\n3. Create/modify a grammar course\n4. Delete a grammar course\n5. Create/modify a grammar exam\n6. Delete a grammar exam\n7. View the results on exams\n0. Exit\n");

            String option = scanner.nextLine();

            switch (option) {
                case "0":
                    continueLoop = false;
                    break;
                case "1":

                    int teacherId=readTeacherId(scanner);
                    try{
                        List<Grammar> grammarCourses=grammarController.viewGrammarCoursesTaughtByTeacher(teacherId);
                        if (grammarCourses.isEmpty())
                            System.out.println("You don't have any grammar courses!");
                        else
                            for (Grammar course:grammarCourses)
                                System.out.println(course);
                    }catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}
                    break;

                case "2":

                    List<Student> enrolledStud=grammarController.showStudentsEnrolledInGrammarCourses();
                    if (enrolledStud.isEmpty())
                        System.out.println("No students are enrolled in grammar courses yet!");
                    else for(Student stud:enrolledStud)
                        System.out.println(stud);
                    break;

                case "3":

                    int courseId=readCourseId(scanner);
                    int teacherId3=readTeacherId(scanner);
                    String courseName=readCourseName(scanner);
                    int maxStudents=readMaxStudents(scanner);

                    try{
                        grammarController.createOrUpdateGrammarCourse(courseId,teacherId3,courseName,maxStudents);
                        System.out.println("Course successfully added/updated!");
                    }catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "4":

                    int courseId4=readCourseId(scanner);
                    int teacherId4=readTeacherId(scanner);
                    try{
                        if (grammarController.removeCourse(courseId4,teacherId4))
                            System.out.println("Course deleted successfully!");
                        else
                            System.out.println("You don't have access to this course!");
                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "5":

                    int examId=readExamId(scanner);
                    int teacherId5=readTeacherId(scanner);
                    String examName=readCourseName(scanner);
                    try{
                        examController.createOrUpdateGrammarExam(examId,teacherId5,examName);
                        System.out.println("Exam successfully created/updated!");
                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "6":

                    int examId6=readExamId(scanner);
                    int teacherId6=readTeacherId(scanner);
                    try{
                        if (examController.removeGrammarExam(examId6,teacherId6))
                            System.out.println("Exam deleted successfully!");
                        else
                            System.out.println("You don't have access to this exam!");
                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "7":

                    int examId7=readExamId(scanner);
                    int teacherId7=readTeacherId(scanner);
                    try{
                        List<ExamResult> results=examController.showAllResultsOfGrammarTeacherExam(teacherId7,examId7);
                        if (results.isEmpty())
                            System.out.println("No student took this exam yet!");
                        else for (ExamResult result:results)
                            System.out.println(result);
                    }catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                default:
            }
        }
    }


    public void vocabularyMenu(){
        Scanner scanner=new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.print("Select an option:\n\n1. View your courses\n2. View students enrolled in vocabulary courses\n3. Create/modify a vocabulary course\n4. Delete a vocabulary course\n5. Create/modify a vocabulary exam\n6. Delete a vocabulary exam\n7. View the results on exams\n0. Exit\n");

            String option = scanner.nextLine();

            switch (option) {
                case "0":
                    continueLoop = false;
                    break;
                case "1":

                    int teacherId=readTeacherId(scanner);
                    try{
                        List<Vocabulary> vocabCourses=vocabController.viewVocabCoursesTaughtByTeacher(teacherId);
                        if (vocabCourses.isEmpty())
                            System.out.println("You don't have any vocabulary courses!");
                        else
                            for (Vocabulary vocab:vocabCourses)
                                System.out.println(vocab);
                    }catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "2":

                    List<Student> enrolledStud=vocabController.showStudentsEnrolledInVocabCourses();
                    if (enrolledStud.isEmpty())
                        System.out.println("No students are enrolled in vocabulary courses yet!");
                    else for(Student stud:enrolledStud)
                        System.out.println(stud);

                    break;

                case "3":

                    int courseId=readCourseId(scanner);
                    int teacherId3=readTeacherId(scanner);
                    String courseName=readCourseName(scanner);
                    int maxStudents=readMaxStudents(scanner);

                    try{
                        vocabController.createOrUpdateVocabCourse(courseId,teacherId3,courseName,maxStudents);
                        System.out.println("Course successfully added/updated!");
                    }catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "4":

                    int courseId4=readCourseId(scanner);
                    int teacherId4=readTeacherId(scanner);
                    try{
                        if (vocabController.removeCourse(courseId4,teacherId4))
                            System.out.println("Course deleted successfully!");
                        else
                            System.out.println("You don't have access to this course!");
                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "5":

                    int examId=readExamId(scanner);
                    int teacherId5=readTeacherId(scanner);
                    String examName=readCourseName(scanner);
                    try{
                        examController.createOrUpdateVocabExam(examId,teacherId5,examName);
                        System.out.println("Exam successfully created/updated!");
                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "6":

                    int examId6=readExamId(scanner);
                    int teacherId6=readTeacherId(scanner);
                    try{
                        if (examController.removeVocabExam(examId6,teacherId6))
                            System.out.println("Exam deleted successfully!");
                        else
                            System.out.println("You don't have access to this exam!");
                    } catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                case "7":

                    int examId7=readExamId(scanner);
                    int teacherId7=readTeacherId(scanner);
                    try{
                        List<ExamResult> results=examController.showAllResultsOfVocabTeacherExam(teacherId7,examId7);
                        if (results.isEmpty())
                            System.out.println("No student took this exam yet!");
                        else for (ExamResult result:results)
                            System.out.println(result);
                    }catch(ValidationException | EntityNotFoundException e){ System.out.println(e.getMessage());}

                    break;

                default:
            }
        }
    }


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
