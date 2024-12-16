package org.example.view;
import java.util.Scanner;

/**
 * Displays a combined view where the user can choose either the student or the teacher view
 */
public class View {
    private StudentView studentView;
    private TeacherView teacherView;

    public View(StudentView studentView, TeacherView teacherView){
        this.studentView=studentView;
        this.teacherView=teacherView;
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;

        while(continueLoop){
            System.out.print("Select a role:\n\n1. Student\n2. Teacher\n3. Exit\n");
            String option = scanner.nextLine();
            switch (option) {
                case "3":
                    continueLoop = false;
                    break;
                case "1":
                    studentView.start();
                    break;
                case "2":
                    teacherView.start();
                    break;
                default:
            }
        }

    }
}
