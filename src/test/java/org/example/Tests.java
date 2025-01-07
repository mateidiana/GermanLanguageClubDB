package org.example;

import org.example.model.*;
import org.example.model.Exceptions.DatabaseException;
import org.example.repo.*;
import org.example.service.StudentService;
import org.example.controller.StudentController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class Tests {
    @Test
    public void CRUDStudent() {
        StudentDBRepository studentRepository = new StudentDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub","root","Bill4761");
        Student student = new Student("StudentTest",101);

        studentRepository.create(student);
        assertEquals("StudentTest", studentRepository.read(101).getName());
        studentRepository.update(new Student("StudentTest1",101));
        assertEquals("StudentTest1", studentRepository.read(101).getName());
        studentRepository.delete(101);
        assertNull(studentRepository.read(101));
    }
    @Test
    public void CRUDTeacher() {
        TeacherDBRepository teacherRepository = new TeacherDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub", "root", "Bill4761");

        Teacher teacher = new Teacher("TeacherTest", 201);
        teacherRepository.create(teacher);

        assertEquals("TeacherTest", teacherRepository.read(201).getName());

        teacherRepository.update(new Teacher("TeacherTest1", 201));

        assertEquals("TeacherTest1", teacherRepository.read(201).getName());

        teacherRepository.delete(201);

        assertNull(teacherRepository.read(201));
    }

    @Test
    public void CRUDGrammar(){
        GrammarDBRepository grammarRepository = new GrammarDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub", "root", "Bill4761");
        Grammar grammar = new Grammar(201, "Basic German", 101, 30);
        grammarRepository.create(grammar);

        Grammar retrievedGrammar = grammarRepository.read(10);
        assertEquals("Basic German", retrievedGrammar.getCourseName());
        assertEquals(10, retrievedGrammar.getTeacher());
        assertEquals(10, retrievedGrammar.getAvailableSlots());

        Grammar updatedGrammar = new Grammar(201, "Advanced German", 102, 25);
        grammarRepository.update(updatedGrammar);

        Grammar retrievedUpdatedGrammar = grammarRepository.read(201);
        assertEquals("Advanced German", retrievedUpdatedGrammar.getCourseName());
        assertEquals(102, retrievedUpdatedGrammar.getTeacher());
        assertEquals(25, retrievedUpdatedGrammar.getAvailableSlots());

        grammarRepository.delete(201);

        assertNull(grammarRepository.read(201));
    }
    @Test
    public void CRUDVocabulary() {
        VocabularyDBRepository vocabRepository = new VocabularyDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub", "root", "Bill4761");
        Vocabulary vocabulary = new Vocabulary(207, "Beginner Vocabulary", 101, 30);

        vocabRepository.create(vocabulary);
        assertEquals("Beginner Vocabulary", vocabRepository.read(207).getCourseName());

        vocabRepository.update(new Vocabulary(207, "Advanced Vocabulary", 101, 40));
        assertEquals("Advanced Vocabulary", vocabRepository.read(207).getCourseName());

        vocabRepository.delete(207);
        assertNull(vocabRepository.read(207));
    }
    @Test
    public void CRUDReading() {
        ReadingDBRepository readingRepository = new ReadingDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub", "root", "Bill4761");
        Reading reading = new Reading(205, "Beginner Reading", 102, 25);

        readingRepository.create(reading);
        assertEquals("Beginner Reading", readingRepository.read(205).getCourseName());

        readingRepository.update(new Reading(205, "Advanced Reading", 102, 35));
        assertEquals("Advanced Reading", readingRepository.read(205).getCourseName());

        readingRepository.delete(205);
        assertNull(readingRepository.read(205));
    }
    @Test
    public void CRUDGrammarExam() {
        GrammarExamDBRepository grammarExamRepository = new GrammarExamDBRepository("jdbc:mysql://127.0.0.1:3306/germanlanguageclub", "root", "Bill4761");
        GrammarExam grammarExam = new GrammarExam(301, "Grammar Basics", 103);

        // Create
        grammarExamRepository.create(grammarExam);
        assertEquals("Grammar Basics", grammarExamRepository.read(301).getExamName());

        // Update
        grammarExamRepository.update(new GrammarExam(301, "Advanced Grammar", 103));
        assertEquals("Advanced Grammar", grammarExamRepository.read(301).getExamName());

        // Delete
        grammarExamRepository.delete(301);
        assertNull(grammarExamRepository.read(301));
    }

}