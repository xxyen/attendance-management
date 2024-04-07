package edu.duke.ece651.courseManage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;

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

  
  public static void createCourse(BufferedReader inputReader, PrintStream outputStream) {
    outputStream.println("You are creating a new course! Please enter the information required below.");
    CourseDAO courseIO = new CourseDAO();
    List<Course> courses = courseIO.findAllCourses();
    String courseId = getInputCourseID(inputReader, outputStream, courses);
    String courseName = getInputCourseName(inputReader, outputStream, courses);
    while (true) {
      try {
        Course newCourse = new Course(courseId, courseName);
        courseIO.addCourse(newCourse);
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
        boolean deleteYN = readInputYorN(inputReader, outputStream, "DANGER!!! Remove a class will be destructive, do you want to continue?");
        if (!deleteYN) {
          return;
        }
        courseIO.deleteCourse(courseId);
        return;// newCourse;
      } catch (Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
        // FileHandler.deleteCourse(courseid); //if not successful, delete the course
        // directory
      }
    }
  }
  
  public static void updateCourse(BufferedReader inputReader, PrintStream outputStream) {
  }
  
  public void loop() throws Exception {
    //boolean flag = true;
    while (true) {
      try {
        out.print("--------------------------------------------------------------------------------\n");
        out.print("1. Add a new course.\n" +
                  "2. Remove an existing course.\n" +
                  "3. Update an existing course.\n" +
                  "4. Exit the system.\n" +
                  "Above are all the available actions. What do you want to do? Please type in the index number:\n");
        out.println("--------------------------------------------------------------------------------\n");
        int index = readPositiveInteger(inputReader);
        if (index == 1) {
          createCourse(inputReader, out);
        }
        else if (index == 2) {
          removeCourse(inputReader, out);
        }
        else if (index == 3) {
          updateCourse(inputReader, out);
        }
        else if (index == 4) {
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

  private static List<Integer> readInputOrder(BufferedReader inputReader, PrintStream outputStream) {
    while (true) {
      outputStream.println("What is the order of column data in your file? Please use numbers to indicate the order.");
      outputStream.println(
          "  0.Anything not mentioned below\n  1. Student ID(required)\n  2. Student legal name(required)\n  3. Student display name(optional)\n  4. Student email(required)");
      outputStream
          .println("(Example: enter 3,0,1,4 if your columns are in this order: legal name, phone number, id, email)");
      try {
        String line = inputReader.readLine();
        String[] order = line.split(",");
        if (!arrayContains(order, "1") || !arrayContains(order, "2") || !arrayContains(order, "4")) {

        }
        List<Integer> indexInOrder = new ArrayList<>();
        indexInOrder.add(arrayIndexOf(order, "1"));
        indexInOrder.add(arrayIndexOf(order, "2"));
        if (arrayIndexOf(order, "3") == -1) {
          indexInOrder.add(arrayIndexOf(order, "2"));
        } else {
          indexInOrder.add(arrayIndexOf(order, "3"));
        }
        indexInOrder.add(arrayIndexOf(order, "4"));
        return indexInOrder;
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

  private int readPositiveInteger(BufferedReader reader) throws IOException {
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

