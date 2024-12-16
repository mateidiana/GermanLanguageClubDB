package org.example;
import org.example.model.Exceptions.ValidationException;
import org.example.repo.*;
import org.example.service.*;
import org.example.controller.*;
import org.example.model.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//        IRepository<Student> studentRepo=new InMemoryRepository<>();
//        StudentService studentService = new StudentService(studentRepo);
//        StudentController studentController = new StudentController(studentService);
//        if (studentController.createStudent(1,"Student 1"))
//            System.out.println("Registration successful!");
//
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter student ID: ");
//        int id = Integer.parseInt(scanner.nextLine());
//        System.out.println("Enter student name: ");
//        String name = scanner.nextLine();
//
//
//        try{
//            if (studentController.createStudent(id,name))
//                System.out.println("Registration successful!");
//            else System.out.println("Id already in use!");
//
//        } catch(ValidationException e){ System.out.println(e.getMessage());}
//
//        System.out.println("\n");
//        for (Student student:studentRepo.getAll())
//        {
//            System.out.println(student.getName());
//        }
    }

}
