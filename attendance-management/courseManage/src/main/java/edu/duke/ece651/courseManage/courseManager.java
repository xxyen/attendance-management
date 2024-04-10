package edu.duke.ece651.courseManage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.Date;
import java.text.SimpleDateFormat;

import edu.duke.ece651.shared.*;
import edu.duke.ece651.shared.model.*;
import edu.duke.ece651.shared.dao.*;

public class courseManager {
  
  private final BufferedReader inputReader;
  private final PrintStream out;
  
  public courseManager(BufferedReader inputReader, PrintStream out) {
    this.inputReader = inputReader;
    this.out = out;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public static void viewCourse(BufferedReader inputReader, PrintStream outputStream) {
    CourseDAO courseIO = new CourseDAO();
    List<Course> courses = courseIO.findAllCourses();
    outputStream.print("--------------------------------------------------------------------------------\n");
    outputStream.println("Here is the list of current courses:");
    for (Course course : courses) {
      outputStream.println("ID: " + course.getCourseId() + "     Name: " + course.getCourseName());
    }
    outputStream.print("--------------------------------------------------------------------------------\n\n");
  }
  
  public static void createCourse(BufferedReader inputReader, PrintStream outputStream) {
    outputStream.println("You are creating a new course! Please enter the information required below.");
    CourseDAO courseIO = new CourseDAO();
    //outputStream.println("Created courseIO.");
    List<Course> courses = courseIO.findAllCourses();
    //List<Course> courses = new ArrayList<Course>();
    //outputStream.println("Fetched course List.");
    String courseId = getInputCourseID(inputReader, outputStream, courses);
    String courseName = getInputCourseName(inputReader, outputStream, courses);
    while (true) {
      try {
        Course newCourse = new Course(courseId, courseName);
        courseIO.addCourse(newCourse);
        outputStream.print("--------------------------------------------------------------------------------\n");
        outputStream.println("Successfully created a new course!\nNow back to the MAIN MENU.");
        outputStream.print("--------------------------------------------------------------------------------\n");
        return;// newCourse;
      } catch (Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
      }
    }
  }

  public static void removeCourse(BufferedReader inputReader, PrintStream outputStream) {
    outputStream.println("You are removing an existing course! Please enter the information required below.");
    CourseDAO courseIO = new CourseDAO();
    List<Course> courses = courseIO.findAllCourses();
    String courseId = getRemoveCourseID(inputReader, outputStream, courses);
    while (true) {
      try {
        outputStream.print("********************************************************************************\n");
        boolean deleteYN = readInputYorN(inputReader, outputStream, "DANGER!!! Remove a class will be destructive, do you want to continue? [Y/N]");
        outputStream.print("********************************************************************************\n");
        if (!deleteYN) {
          outputStream.print("--------------------------------------------------------------------------------\n");
          outputStream.println("Removal cancelled! Nothing is removed.\nNow back to the MAIN MENU.");
          outputStream.print("--------------------------------------------------------------------------------\n");
          return;
        }
        courseIO.deleteCourse(courseId);
        outputStream.print("--------------------------------------------------------------------------------\n");
        outputStream.println("Successfully removed an existing course!\nNow back to the MAIN MENU.");
        outputStream.print("--------------------------------------------------------------------------------\n");
        return;// newCourse;
      } catch (Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
        // FileHandler.deleteCourse(courseid); //if not successful, delete the course
        // directory
      }
    }
  }

  ////////////////////////////////////////////////////////////////////////////////

  public static void changeCourseName(BufferedReader inputReader, PrintStream outputStream) {
    outputStream.println("You are changing name of an existing course! Please enter the information required below.");
    CourseDAO courseIO = new CourseDAO();
    //outputStream.println("Created courseIO.");
    List<Course> courses = courseIO.findAllCourses();
    //List<Course> courses = new ArrayList<Course>();
    //outputStream.println("Fetched course List.");
    String courseId = getRemoveCourseID(inputReader, outputStream, courses);
    outputStream.println("Please enter the new name of the course below.");
    String newName = getInputCourseName(inputReader, outputStream, courses);
    while (true) {
      try {
        //Course newCourse = new Course(courseId, courseName);
        courseIO.updateCourseName(courseId, newName);
        outputStream.print("--------------------------------------------------------------------------------\n");
        outputStream.println("Successfully changed course name!\nNow back to the UPDATE COURSE MENU.");
        outputStream.print("--------------------------------------------------------------------------------\n");
        return;// newCourse;
      } catch (Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
      }
    }
  }

  public static void viewProfessor(BufferedReader inputReader, PrintStream outputStream) {
    FacultyDAO profIO = new FacultyDAO();
    Set<Professor> professors = profIO.queryAllFaculty();
    outputStream.print("--------------------------------------------------------------------------------\n");
    outputStream.println("Here is the list of all professors:");
    for (Professor prof : professors) {
      outputStream.println("ID: " + prof.getUserid() + "     Name: " + prof.getName());
    }
    outputStream.print("--------------------------------------------------------------------------------\n\n");
   }
  
  public static void viewAllStudents(BufferedReader inputReader, PrintStream outputStream) {
    StudentDAO stuIO = new StudentDAO();
    Set<Student> students = stuIO.queryAllStudents();
    outputStream.print("--------------------------------------------------------------------------------\n");
    outputStream.println("Here is the list of all students:");
    for (Student stu : students) {
      outputStream.println(stu.toString());
    }
    outputStream.print("--------------------------------------------------------------------------------\n\n");
  }
  
  public static void viewStudentsInSection(BufferedReader inputReader, PrintStream outputStream) {
    EnrollmentDAO enrollIO = new EnrollmentDAO();
    SectionDAO sectionIO = new SectionDAO();
    List<Section> sections = sectionIO.queryAllSections();
    int sectionId = getRemoveSectionID(inputReader, outputStream, sections);
    List<Enrollment> enrolls = enrollIO.listEnrollmentsBySection(sectionId);
    outputStream.print("--------------------------------------------------------------------------------\n");
    outputStream.println("Here is the list of all enrolled students:");
    for (Enrollment enro : enrolls) {
      outputStream.println(enro.toString());
    }
    outputStream.print("--------------------------------------------------------------------------------\n\n");
  }

  public static void viewSection(BufferedReader inputReader, PrintStream outputStream) {
    outputStream.println("Please enter the course information required below to view sections.");
    CourseDAO courseIO = new CourseDAO();
    List<Course> courses = courseIO.findAllCourses();
    String courseId = getRemoveCourseID(inputReader, outputStream, courses);
    SectionDAO sectionIO = new SectionDAO();
    List<Section> sections = sectionIO.listSectionsByCourse(courseId);
    outputStream.print("--------------------------------------------------------------------------------\n");
    outputStream.println("Here is the list of all sections:");
    for (Section sec : sections) {
      outputStream.println("Section ID: " + sec.getSectionId() + "     Course ID: " + sec.getCourseId() + "     Professor ID: " + sec.getFacultyId());
    }
    outputStream.print("--------------------------------------------------------------------------------\n\n");
  }
  
  public static void addSection(BufferedReader inputReader, PrintStream outputStream) {
    outputStream.println("You are creating a new section! Please enter the information required below.");
    CourseDAO courseIO = new CourseDAO();
    //outputStream.println("Created courseIO.");
    List<Course> courses = courseIO.findAllCourses();
    //List<Course> courses = new ArrayList<Course>();
    //outputStream.println("Fetched course List.");
    String courseId = getRemoveCourseID(inputReader, outputStream, courses);
    SectionDAO sectionIO = new SectionDAO();
    List<Section> sections = sectionIO.listSectionsByCourse(courseId);
    //int sectionId = getInputSectionID(inputReader, outputStream, sections);
    FacultyDAO profIO = new FacultyDAO();
    Set<Professor> profs = profIO.queryAllFaculty();
    String profId = getInputProfID(inputReader, outputStream, profs);
    while (true) {
      try {
        //Course newCourse = new Course(courseId, courseName);
        //courseIO.addCourse(newCourse);
        Section newSection = new Section(1, courseId, profId);
        outputStream.println("newSection  courseId = " + courseId + " profId = " + profId);
        sectionIO.addSectionToCourse(newSection);
        outputStream.print("--------------------------------------------------------------------------------\n");
        outputStream.println("Successfully created a new section!\nNow back to the UPDATE COURSE MENU.");
        outputStream.print("--------------------------------------------------------------------------------\n");
        return;// newCourse;
      } catch (Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
      }
    }
  }

  public static void removeSection(BufferedReader inputReader, PrintStream outputStream) {
    outputStream.println("You are removing an existing section! Please enter the information required below.");
    CourseDAO courseIO = new CourseDAO();
    //outputStream.println("Created courseIO.");
    List<Course> courses = courseIO.findAllCourses();
    //List<Course> courses = new ArrayList<Course>();
    //outputStream.println("Fetched course List.");
    String courseId = getRemoveCourseID(inputReader, outputStream, courses);
    SectionDAO sectionIO = new SectionDAO();
    List<Section> sections = sectionIO.listSectionsByCourse(courseId);
    int sectionId = getRemoveSectionID(inputReader, outputStream, sections);
    while (true) {
      try {
        outputStream.print("********************************************************************************\n");
        boolean deleteYN = readInputYorN(inputReader, outputStream, "DANGER!!! Remove a section will be destructive, do you want to continue? [Y/N]");
        outputStream.print("********************************************************************************\n");
        if (!deleteYN) {
          outputStream.print("--------------------------------------------------------------------------------\n");
          outputStream.println("Removal cancelled! Nothing is removed.\nNow back to the UPDATE COURSE MENU.");
          outputStream.print("--------------------------------------------------------------------------------\n");
          return;
        }
        sectionIO.deleteSection(sectionId);
        outputStream.print("--------------------------------------------------------------------------------\n");
        outputStream.println("Successfully removed an existing section!\nNow back to the UPDATE COURSE MENU.");
        outputStream.print("--------------------------------------------------------------------------------\n");
        return;// newCourse;
      } catch (Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
      }
    }
  }

  public static void addStudentToSection(BufferedReader inputReader, PrintStream outputStream) {
    outputStream.println("You are adding a new studnet to a section! Please enter the information required below.");
    SectionDAO sectionIO = new SectionDAO();
    List<Section> sections = sectionIO.queryAllSections();
    int sectionId = getRemoveSectionID(inputReader, outputStream, sections);
    StudentDAO studentIO = new StudentDAO();
    Set<Student> students = studentIO.queryAllStudents();
    String studentId = getInputStuID(inputReader, outputStream, students);
    boolean notifyYN = readInputYorN(inputReader, outputStream, "Do you want to receive notifications? [Y/N]");
    Enrollment newEnroll = new Enrollment(sectionId, studentId, new Date(), "Enrolled", notifyYN);
    EnrollmentDAO enrollIO = new EnrollmentDAO();
    enrollIO.addEnrollment(newEnroll);
    outputStream.print("--------------------------------------------------------------------------------\n");
    outputStream.println("Successfully added a new student!\nNow back to the UPDATE COURSE MENU.");
    outputStream.print("--------------------------------------------------------------------------------\n");
  }

  public static void loadStudentsToSection(BufferedReader inputReader, PrintStream outputStream) throws Exception {
    outputStream.println("You are adding new studnets to a section! Please enter the information required below.");
    SectionDAO sectionIO = new SectionDAO();
    List<Section> sections = sectionIO.queryAllSections();
    int sectionId = getRemoveSectionID(inputReader, outputStream, sections);
    boolean notifyYN = readInputYorN(inputReader, outputStream, "Do you want to receive notifications? [Y/N]");
    outputStream.println("CSV File Path:");
    while (true) {
      try {
        String path = inputReader.readLine();
        List<Student> students = readStudentsFromFile(inputReader, outputStream, path, true);
        outputStream.print("--------------------------------------------------------------------------------\n");
        outputStream.println("Here is the list of students to be added to the section:");
        for (Student stu : students) {
          outputStream.println(stu.toString());
        }
        outputStream.print("--------------------------------------------------------------------------------\n\n");
        EnrollmentDAO enrollIO = new EnrollmentDAO();
        for (Student stu : students) {
          Enrollment newEnroll = new Enrollment(sectionId, stu.getUserid(), new Date(), "Enrolled", notifyYN);
          enrollIO.addEnrollment(newEnroll);
        }
        outputStream.print("--------------------------------------------------------------------------------\n");
        outputStream.println("Successfully added new students from file!\nNow back to the UPDATE COURSE MENU.\n");
        outputStream.print("--------------------------------------------------------------------------------\n");
        return;
      }
      catch (Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
      }
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public static void updateCourse(BufferedReader inputReader, PrintStream outputStream) throws Exception {
    while (true) {
      try {
        outputStream.print("--------------------------------------------------------------------------------\n");
        outputStream.print("UPDATE COURSE MENU\n" +
                           "1.  Change course name.\n" +
                           "2.  View the list of all professors.\n" +
                           "3.  View the list of current sections.\n" +
                           "4.  Add a new section.\n" +
                           "5.  Remove an existing section.\n" +
                           "6.  View the list of all students.\n" +
                           "7.  View the list of students in a section.\n" +
                           "8.  Add one student to a section.\n" +
                           "9.  Add a list of students to a section.\n" +
                           "10. Remove one student from a section." +
                           "11. Exit to MAIN MENU.\n" +
                           "Above are all the available actions. What do you want to do? Please type in the index number:\n");
        outputStream.println("--------------------------------------------------------------------------------\n");
        int index = readPositiveInteger(inputReader);
        if (index == 1) {
          changeCourseName(inputReader, outputStream);
        }
        else if (index == 2) {
          viewProfessor(inputReader, outputStream);
        }
        else if (index == 3) {
          viewSection(inputReader, outputStream);
        }
        else if (index == 4) {
          addSection(inputReader, outputStream);
        }
        else if (index == 5) {
          removeSection(inputReader, outputStream);
        }
        else if (index == 6) {
          viewAllStudents(inputReader, outputStream);
        }
        else if (index == 7) {
          viewStudentsInSection(inputReader, outputStream);
        }
        else if (index == 8) {
          addStudentToSection(inputReader, outputStream);
        }
        else if (index == 9) {
          loadStudentsToSection(inputReader, outputStream);
        }
        else if (index == 11) {
          outputStream.print("--------------------------------------------------------------------------------\n");
          outputStream.print("You are back to the MAIN MENU!\n");
          outputStream.println("--------------------------------------------------------------------------------\n");
          return;
        }
        else {
          throw new IllegalArgumentException("Invalid action number, please choose your action again!");
        }
      } catch (Exception e) {
        outputStream.println(e.getMessage());
      }
    }
  }

  ////////////////////////////////////////////////////////////////////////////////
  
  public void loop() throws Exception {
    //boolean flag = true;
    while (true) {
      try {
        out.print("--------------------------------------------------------------------------------\n");
        out.print("MAIN MENU\n" +
                  "1.  View the list of all courses.\n" +
                  "2.  Add a new course.\n" +
                  "3.  Remove an existing course.\n" +
                  "4.  Update an existing course.\n" +
                  "5.  Exit the system.\n" +
                  "Above are all the available actions. What do you want to do? Please type in the index number:\n");
        out.println("--------------------------------------------------------------------------------\n");
        int index = readPositiveInteger(inputReader);
        if (index == 1) {
          viewCourse(inputReader, out);
        }
        else if (index == 2) {
          createCourse(inputReader, out);
        }
        else if (index == 3) {
          removeCourse(inputReader, out);
        }
        else if (index == 4) {
          updateCourse(inputReader, out);
        }
        else if (index == 5) {
          out.print("--------------------------------------------------------------------------------\n");
          out.print("You have exited the system!\n");
          out.println("--------------------------------------------------------------------------------\n");
          return;
        }
        else {
          throw new IllegalArgumentException("Invalid action number, please choose your action again!");
        }
      } catch (Exception e) {
        out.println(e.getMessage());
      }
    }
  }





  
  /////////////////////////////////////////////////////////////////////////////////

  private static boolean getYorN(String choiceStr) {
    if (choiceStr != null && choiceStr.length() == 1 && Character.isLetter(choiceStr.charAt(0))) {
      char choice = choiceStr.charAt(0);
      if ((choice == 'y') || (choice == 'Y')) {
        return true;
      } else if ((choice == 'n') || (choice == 'N')) {
        return false;
      }
    }
    throw new IllegalArgumentException("Invalid input: you should only type in y or n!");
  }

  private static String getInputCourseID(BufferedReader inputReader, PrintStream outputStream, List<Course> courses) {
    while (true) {
      outputStream.println("Course ID: ");
      try {
        String courseid = inputReader.readLine();
        if (courses.stream().anyMatch(course -> course.getCourseId().equals(courseid))) {
          outputStream.println("Course ID exists! Please try again!");
          continue;
        }
        outputStream.println("Course ID = " + courseid);
        return courseid;
      } catch (Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
      }
    }
  }

  private static String getInputCourseName(BufferedReader inputReader, PrintStream outputStream, List<Course> courses) {
    while (true) {
      outputStream.println("Course Name: ");
      try {
        String courseName = inputReader.readLine();
        if (courses.stream().anyMatch(course -> course.getCourseName().equals(courseName))) {
          outputStream.println("Course Name exists! Please try again!");
          continue;
        }
        return courseName;
      } catch (Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
      }
    }
  }

  private static String getRemoveCourseID(BufferedReader inputReader, PrintStream outputStream, List<Course> courses) {
    while (true) {
      outputStream.println("Course ID: ");
      try {
        String courseid = inputReader.readLine();
        if (!courses.stream().anyMatch(course -> course.getCourseId().equals(courseid))) {
          outputStream.println("Course ID does not exist! Please try again!");
          continue;
        }
        return courseid;
      } catch (Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
      }
    }
  }

  ///////////////////////////////////////////////////////////////////////////////

  private static int getInputSectionID(BufferedReader inputReader, PrintStream outputStream, List<Section> sections) {
    while (true) {
      outputStream.println("Section ID: ");
      try {
        int sectionId = readPositiveInteger(inputReader);
        //String courseid = inputReader.readLine();
        if (sections.stream().anyMatch(section -> section.getSectionId() == sectionId)) {
          outputStream.println("Section ID exists! Please try again!");
          continue;
        }
        //outputStream.println("Section ID = " + courseid);
        return sectionId;
      } catch (Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
      }
    }
  }

  private static int getRemoveSectionID(BufferedReader inputReader, PrintStream outputStream, List<Section> sections) {
    while (true) {
      outputStream.println("Section ID: ");
      try {
        int sectionId = readPositiveInteger(inputReader);
        //String courseid = inputReader.readLine();
        if (!sections.stream().anyMatch(section -> section.getSectionId() == sectionId)) {
          outputStream.println("Section ID does not exist! Please try again!");
          continue;
        }
        //outputStream.println("Section ID = " + courseid);
        return sectionId;
      } catch (Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
      }
    }
  }

  private static String getInputProfID(BufferedReader inputReader, PrintStream outputStream, Set<Professor> profs) {
    while (true) {
      outputStream.println("Professor ID: ");
      try {
        String profId = inputReader.readLine();
        if (!profs.stream().anyMatch(prof -> prof.getUserid().equals(profId))) {
          outputStream.println("Professor ID does not exist! Please try again!");
          continue;
        }
        outputStream.println("Professor ID = " + profId);
        return profId;
      } catch (Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
      }
    }
  }

  private static String getInputStuID(BufferedReader inputReader, PrintStream outputStream, Set<Student> stus) {
    while (true) {
      outputStream.println("Student ID: ");
      try {
        String stuId = inputReader.readLine();
        if (!stus.stream().anyMatch(stu -> stu.getUserid().equals(stuId))) {
          outputStream.println("Student ID does not exist! Please try again!");
          continue;
        }
        outputStream.println("Student ID = " + stuId);
        return stuId;
      } catch (Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
      }
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////

  private static List<Student> readStudentsFromFile(BufferedReader inputReader, PrintStream outputStream, String rosterPath, boolean withHeader) throws Exception {
    File rosterFile = new File(rosterPath);
    if (!rosterFile.exists()) {
      throw new FileNotFoundException("Roster file not found at: " + rosterPath);
    }
    List<Student> students = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(rosterFile))) {
      String line;
      if (withHeader) {
        br.readLine();
      }
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        Student newStudent = new Student(values[0], values[1], values[2], new Email(values[3]));
        students.add(newStudent);
      }
    }
    return students;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  private static boolean readInputYorN(BufferedReader inputReader, PrintStream outputStream, String prompt) {
    while (true) {
      try {
        outputStream.println(prompt);
        return getYorN(inputReader.readLine());
      } catch (Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
      }
    }
  }

  private static int arrayIndexOf(String[] array, String value) {
    for (int i = 0; i < array.length; i++) {
      if (array[i].equals(value)) {
        return i;
      }
    }
    return -1; // Value not found
  }

  private static boolean arrayContains(String[] array, String value) {
    for (String str : array) {
      if (str.equals(value)) {
        return true;
      }
    }
    return false;
  }

  private static int readPositiveInteger(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        try {
            int number = Integer.parseInt(line);
            if (number > 0) {
                return number; 
            } else {
                throw new IllegalArgumentException("Invalid input: it is not a positive integer!");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input: it is not a positive integer!");
        }
    }


}



