/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.shared;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

public class App {

  public static void main(String[] args) {
    // load all data from files
    try {
      BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
      AccountOperator accountOperator = new AccountOperator("src/main/resources/");
      Map<String, Professor> professors = FileHandler.loadGlobalProfessors();
      Map<String, Student> students = FileHandler.loadGlobalStudents();
      Map<String, User> allUsers = new HashMap<>();
      allUsers.putAll(professors);
      allUsers.putAll(students);
      List<Course> courses = FileHandler.loadCourses(students, professors);
    } catch (Exception e) {
      outputStream.println(e.getMessage());
    }
    while (true) {
      User currentUser = signIn(inputReader, System.out, accountOperator, allUsers);
      if (currentUser.getUserType().equals("professor")) {
        // it returns false means logout option is chosen.
        if (!professorActions(inputReader, System.out, courses, (Professor)currentUser)) {
          System.out.println("Logged out.");
          continue;
        }
      }
    }
  }

  public static int readPositiveInteger(BufferedReader reader){
    try {
      String line = reader.readLine(); 
      int number = Integer.parseInt(line);
      if (number > 0) {
        return number;
      } else {
        return -1;
      }
    } catch (NumberFormatException e) {
          return  -1;
        }
    }


  private static boolean professorActions(BufferedReader inputReader, PrintStream outputStream, List<Course> courses,
      Professor professor) {
    try {
        while (true) {
        outputStream.println(
            "What would you like to do?\n    1. Manage an existing course/n    2. Create a new course and manage it\n    3. Log out");
        outputStream.println("Enter the number to choose your action: ");
        int choice = readPositiveInteger(inputReader);
        if (choice == 1) {
          Course currentCourse = chooseCourse(inputReader, outputStream,
              courses.stream().filter(course -> course.getProfessors().contains(professor)).collect(Collectors.toList()));
          AttendanceOperator attendanceOperator = new BasicAttendanceOperator();
          TextPlayer textPlayer = new TextPlayer(professor, currentCourse, attendanceOperator, inputReader, outputStream);
          textPlayer.loop();
          return true;
        } else if (choice == 2) {
          Course currentCourse = createCourse(inputReader, outputStream, courses);
          AttendanceOperator attendanceOperator = new BasicAttendanceOperator();
          TextPlayer textPlayer = new TextPlayer(professor, currentCourse, attendanceOperator, inputReader, outputStream);
          return true;
        } else if (choice == 3) {
          return false;
        } else {
          outputStream.println("Invalid input! Please try again!");
          continue;
        }
      } 
      } catch (Exception e) {
        outputStream.println(e.getMessage());
      }
    }
  }

  
  private static Course createCourse(BufferedReader inputReader, PrintStream outputStream, List<Course> courses) {
    outputStream.println("You are creating a new course! Please enter the information required below.");
    try {
      while(true) {
        outputStream.println("Course ID: ");
        String courseid = inputReader.readLine();
        if(courses.stream().anyMatch(course -> course.getCourseid().equals(courseid))) {
          outputStream.println("Course ID exists! Please try again!");
         continue;
        }
        outputStream.println("Do you");  
      }
      
      //return new Course(courseid, [professors], Student[] studentsArray, boolean canChangeName)
      //String courseid, Professor[] professorsArray, Student[] studentsArray, boolean canChangeName
      } catch (Exception e) {
        outputStream.println(e.getMessage());
      }
  }

  private static Course chooseCourse(BufferedReader inputReader, PrintStream outputStream, List<Course> courses) {
    while (true) {
      outputStream.println("Which course would you like to manage?");
      for (int i = 0; i < courses.size(); i++) {
        Course course = courses.get(i);
        outputStream.println(i + 1 + ". " + course.getCourseid());
      }
      outputStream.println("Enter the number to choose a course: "); // or enter \"back\" to get back to choose another
                                                                     // action.");
      int choice = readPositiveInteger(inputReader);
      if ((choice > 0) && (choice <= courses.size())) {
        return courses.get(choice - 1);
      }
      outputStream.println("Invalid input! Please try again!");
      continue;
    }
  }

  private static User signIn(BufferedReader inputReader, PrintStream outputStream, AccountOperator accountOperator,
      Map<String, User> userList) {
    while (true) {
      outputStream.println("Please enter your user ID: ");
      try {
        String userid = inputReader.readLine();
        if (!accountOperator.useridExists(userid)) {
          outputStream.println("This account doesn't exist! Please try again!");
          continue;
        }
        outputStream.println("Please enter your password: ");
        String password = inputReader.readLine();
        String personalID = accountOperator.getAccountPersonalID(userid, password);
        if (personalID == null) {
          outputStream.println("Incorrect password! Please try again!");
          continue;
        }
        Optional<Map.Entry<String, User>> userEntryOptional = userList.entrySet().stream()
            .filter(entry -> entry.getKey().equals(personalID)).findFirst();
        if (userEntryOptional.isPresent()) {
          return userEntryOptional.get().getValue();
        }
        outputStream.println("User not found! Please try again!");
        continue;
      } catch (Exception e) {
        outputStream.println(e.getMessage());
      }
    }
  }
}
