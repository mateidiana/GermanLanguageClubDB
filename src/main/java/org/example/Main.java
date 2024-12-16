package org.example;
import org.example.model.Exceptions.EntityNotFoundException;
import org.example.model.Exceptions.ValidationException;
import org.example.repo.*;
import org.example.service.*;
import org.example.controller.*;
import org.example.model.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
//        IRepository<Student> studentRepo=new InMemoryRepository<>();
//        Student s1=new Student("Student1",1);
//        Student s2=new Student("Student2",2);
//        Student s3=new Student("Student3",3);
//        studentRepo.create(s1);
//        studentRepo.create(s2);
//        studentRepo.create(s3);
//        StudentService studentService=new StudentService(studentRepo);
//        for (Student student:studentRepo.getAll())
//        {
//            System.out.println(student.getName());
//        }
//
//        Student studentRepl=new Student("Studentrepl",1); //replaces student with id 1 with studentRepl
//
//        studentRepo.update(studentRepl);
//
//        for (Student student:studentRepo.getAll())
//        {
//            System.out.println(student.getName());
//        }
//        Student student=studentService.getStudentById(1);
//        System.out.println(student.getName());


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
}
