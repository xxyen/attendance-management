package edu.duke.ece651.shared.dao;
import edu.duke.ece651.shared.Student;
import edu.duke.ece651.shared.Email;
import edu.duke.ece651.shared.dao.StudentDAO;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;


public class StudentDAOTest {

    @Test
    public void testAddStudent() throws Exception {
        StudentDAO studentDAO = new StudentDAO();
        Student student = new Student("user123", "hash123", "John Doe", "Johnny", new Email("john.doe@example.com"));
        System.out.println(student);
        
        int result = studentDAO.addStudent(student);
        assertEquals(1, result);
        Student queriedStudent = studentDAO.queryStudentById("user123");
        System.out.println(queriedStudent);

        assertNotNull(queriedStudent);
        assertEquals("John Doe", queriedStudent.getLegalName());
        result = studentDAO.deleteStudent("user123");

    }

    @Test
    public void testDeleteStudent() throws Exception{
        StudentDAO studentDAO = new StudentDAO();

        Student student = new Student("user456", "hash456", "Jane Doe", "Janie", new Email("jane.doe@example.com"));
        studentDAO.addStudent(student);
        System.out.println(student);

        int result = studentDAO.deleteStudent("user456");
        assertEquals(1, result);
        Student queriedStudent = studentDAO.queryStudentById("user456");
        System.out.println(queriedStudent);

        assertNull(queriedStudent);
    }

    @Test
    public void testUpdateStudent() throws Exception{
        StudentDAO studentDAO = new StudentDAO();

        Student student = new Student("user789", "hash789", "Alex Smith", "Alex", new Email("alex.smith@example.com"));
        studentDAO.addStudent(student);
        System.out.println(student);


        student.setDisplayName("Alexander");
        int result = studentDAO.updateStudent(student);
        assertEquals(1, result);
        Student queriedStudent = studentDAO.queryStudentById("user789");
        System.out.println(queriedStudent);

        assertNotNull(queriedStudent);
        assertEquals("Alexander", queriedStudent.getDisplayName());
        result = studentDAO.deleteStudent("user789");

    }

    @Test
    public void testQueryStudentById() throws Exception{
        StudentDAO studentDAO = new StudentDAO();

        Student student = new Student("user101", "hash101", "Sam Johnson", "Sammy", new Email("sam.johnson@example.com"));
        studentDAO.addStudent(student);

        Student queriedStudent = studentDAO.queryStudentById("user101");
        System.out.println(queriedStudent);
        assertNotNull(queriedStudent);
        assertEquals("Sam Johnson", queriedStudent.getLegalName());
        int result = studentDAO.deleteStudent("user101");

    }
}
