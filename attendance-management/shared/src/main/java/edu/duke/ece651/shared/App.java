package edu.duke.ece651.shared;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The main class for the application.
 */
public class App {

  /**
   * The main method to run the application.
   * 
   * @param args The command-line arguments.
   * @throws Exception If an error occurs.
   */
  public static void main(String[] args) throws Exception {
    // load all data from files
    // try {
    BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
    // AccountOperator accountOperator = new
    // AccountOperator("shared/src/main/resources/");
    AccountOperator accountOperator = new AccountOperator("src/main/resources/");
    Map<String, Professor> professors = FileHandler.loadGlobalProfessors();
    Map<String, Student> students = FileHandler.loadGlobalStudents();
    Map<String, User> allUsers = new HashMap<>();
    allUsers.putAll(professors);
    allUsers.putAll(students);
    List<Course> courses = FileHandler.loadCourses(students, professors);

    // 创建任务
    WeeklyReporter task = new WeeklyReporter(courses);
    // 在新线程中启动任务
    Thread thread = new Thread(task);
    thread.start();

    while (true) {
      if (exitOrContinue(inputReader, System.out)) {
        break;
      }
      User currentUser = signIn(inputReader, System.out, accountOperator, allUsers);
      if (currentUser.getUserType().equals("professor")) {
        // it returns false means logout option is chosen.
        while (true) {
          try {
            boolean stayLoggedIn = professorActions(inputReader, System.out, courses, (Professor) currentUser,
                students);
            if (!stayLoggedIn) {
              System.out.println("Logged out.");
              break;
            }
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
        }
      }
    }
    // 触发中断，请求线程停止
    thread.interrupt();

    // 等待线程终止
    thread.join();
    // } catch (Exception e) {
    // System.out.println(e.getMessage());
    // }
  }

  /**
   * Asks the user if they want to exit the system or continue.
   * 
   * @param inputReader  The BufferedReader for user input.
   * @param outputStream The PrintStream for output.
   * @return True if the user wants to exit, false otherwise.
   */
  public static boolean exitOrContinue(BufferedReader inputReader, PrintStream outputStream) {
    try {
      outputStream.println("Enter end to exit the system, or type anything else to continue.");
      if (inputReader.readLine().equals("end")) {
        // System.exit(0);
        return true;
      } else {
        return false;
      }
    } catch (Exception e) {
      outputStream.println(e.getMessage());
    }
    return false;
  }

  /**
   * Reads a positive integer from the user.
   * 
   * @param reader The BufferedReader for user input.
   * @return The positive integer read, or -1 if input is invalid.
   */
  public static int readPositiveInteger(BufferedReader reader) {
    try {
      String line = reader.readLine();
      int number = Integer.parseInt(line);
      if (number > 0) {
        return number;
      } else {
        return -1;
      }
    } catch (Exception e) {
      return -1;
    }
  }

  /**
   * Handles the actions to be performed by a professor.
   * 
   * @param inputReader  The BufferedReader for user input.
   * @param outputStream The PrintStream for output.
   * @param courses      The list of courses.
   * @param professor    The professor user.
   * @param allStudents  A map containing all students.
   * @return True if the professor wants to stay logged in, false otherwise.
   * @throws Exception If an error occurs.
   */
  private static boolean professorActions(BufferedReader inputReader, PrintStream outputStream, List<Course> courses,
      Professor professor, Map<String, Student> allStudents) throws Exception {
    // try {
    // while (true) {
    outputStream.println(
        "What would you like to do?\n    1. Manage an existing course\n    2. Create a new course and manage it\n    3. Log out");
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
      Course currentCourse = createCourse(inputReader, outputStream, courses, professor, allStudents);
      AttendanceOperator attendanceOperator = new BasicAttendanceOperator();
      TextPlayer textPlayer = new TextPlayer(professor, currentCourse, attendanceOperator, inputReader, outputStream);
      return true;
    } else if (choice == 3) {
      return false;
    } else {
      // outputStream.println("Invalid input! Please try again!");
      // continue;
      throw new IllegalArgumentException("Invalid input! Please try again!");
    }
    // }
    // } catch (Exception e) {
    // outputStream.println(e.getMessage());
    // }
    // return true;
  }

  /**
   * Get a choice of yes or no from a string.
   * 
   * @param choiceStr A string for the choice.
   * @return True for yes, false for no.
   */
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

  /**
   * Retrieves the input course ID from the user.
   * 
   * @param inputReader  The BufferedReader for user input.
   * @param outputStream The PrintStream for output.
   * @param courses      The list of existing courses.
   * @return The input course ID.
   */
  private static String getInputCourseID(BufferedReader inputReader, PrintStream outputStream, List<Course> courses) {
    while (true) {
      outputStream.println("Course ID: ");
      try {
        String courseid = inputReader.readLine();
        if (courses.stream().anyMatch(course -> course.getCourseid().equals(courseid))) {
          outputStream.println("Course ID exists! Please try again!");
          continue;
        }
        return courseid;
      } catch (Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
      }
    }
  }

  /**
   * Reads user input to determine if they want to continue.
   * 
   * @param inputReader  The BufferedReader for user input.
   * @param outputStream The PrintStream for output.
   * @param prompt       The prompt to display to the user.
   * @return True if the user input is 'y', false if 'n'.
   */
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

  /**
   * Checks if the given array contains the specified value.
   * 
   * @param array The array to check.
   * @param value The value to search for.
   * @return True if the array contains the value, otherwise false.
   */
  private static boolean arrayContains(String[] array, String value) {
    for (String str : array) {
      if (str.equals(value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Retrieves the index of the specified value in the array.
   * 
   * @param array The array to search.
   * @param value The value to find the index of.
   * @return The index of the value, or -1 if not found.
   */
  private static int arrayIndexOf(String[] array, String value) {
    for (int i = 0; i < array.length; i++) {
      if (array[i].equals(value)) {
        return i;
      }
    }
    return -1; // Value not found
  }

  /**
   * Reads the input order of column data from the user.
   * 
   * @param inputReader  The BufferedReader for user input.
   * @param outputStream The PrintStream for output.
   * @return The list of indices representing the order of column data.
   */
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

  /**
   * Handles the creation of a new course by a professor.
   * 
   * @param inputReader  The BufferedReader for user input.
   * @param outputStream The PrintStream for output.
   * @param courses      The list of existing courses.
   * @param professor    The professor user creating the course.
   * @param allStudents  A map containing all students.
   * @return The newly created course.
   */
  private static Course createCourse(BufferedReader inputReader, PrintStream outputStream, List<Course> courses,
      Professor professor, Map<String, Student> allStudents) {
    outputStream.println("You are creating a new course! Please enter the information required below.");
    String courseid = getInputCourseID(inputReader, outputStream, courses);
    boolean allowChangeName = readInputYorN(inputReader, outputStream,
        "Are students allowed to change their display name?(Y/N)");

    outputStream.println("You are loading the roster from a csv file! Please enter the information required below.");
    boolean withHeader = readInputYorN(inputReader, outputStream, "Does this csv file contain headers?(Y/N)");
    List<Integer> order = readInputOrder(inputReader, outputStream);

    while (true) {
      try {
        Course newCourse = new Course(courseid, professor, allowChangeName);
        // create directory and files for the course
        FileHandler.createCourse(courseid, professor.getPersonalID());
        // load student list of the course
        outputStream.println("Please provide the absolute path of the csv file you wan to load the roster from:");
        FileHandler.loadRosterFromCSVFile(courseid, newCourse, inputReader.readLine(), order, withHeader);
        return newCourse;
      } catch (Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
        // FileHandler.deleteCourse(courseid); //if not successful, delete the course
        // directory
      }
    }
  }

  /**
   * Allows the user to choose a course from the list of courses.
   *
   * @param inputReader  The BufferedReader for user input.
   * @param outputStream The PrintStream for output.
   * @param courses      The list of courses to choose from.
   * @return The chosen course.
   */
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

  /**
   * Handles the user sign-in process.
   *
   * @param inputReader     The BufferedReader for user input.
   * @param outputStream    The PrintStream for output.
   * @param accountOperator The AccountOperator for managing accounts.
   * @param userList        The list of users.
   * @return The signed-in user.
   */
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
