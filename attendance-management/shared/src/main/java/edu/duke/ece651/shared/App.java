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

public class App {

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

      while (true) {
        User currentUser = signIn(inputReader, System.out, accountOperator, allUsers);
        if (currentUser.getUserType().equals("professor")) {
          // it returns false means logout option is chosen.
          while (true) {
            try {
              boolean stayLoggedIn = professorActions(inputReader, System.out, courses, (Professor) currentUser, students);
              if (!stayLoggedIn) {
                System.out.println("Logged out.");
                //continue;
                break;
              }
            } catch (Exception e) {
              System.out.println(e.getMessage());
            }
          }
        }
      }
//    } catch (Exception e) {
//      System.out.println(e.getMessage());
//    }
  }

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

  private static boolean professorActions(BufferedReader inputReader, PrintStream outputStream, List<Course> courses,
      Professor professor, Map<String, Student> allStudents) throws Exception {
    //try {
        //while (true) {
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
          //outputStream.println("Invalid input! Please try again!");
          //continue;
          throw new IllegalArgumentException("Invalid input! Please try again!");
        }
      //}
//      } catch (Exception e) {
//        outputStream.println(e.getMessage());
//      }
      //return true;
    }

  private static boolean getYorN(String choiceStr) {
    if (choiceStr != null && choiceStr.length() == 1 && Character.isLetter(choiceStr.charAt(0))) {
      char choice = choiceStr.charAt(0);
      if((choice == 'y') || (choice == 'Y')) {
        return true;
      } else if ((choice == 'n') || (choice == 'N')) {
        return false;
      }
    } 
    throw new IllegalArgumentException("Invalid input: you should only type in y or n!");
  }

  private static String getInputCourseID(BufferedReader inputReader, PrintStream outputStream, List<Course> courses) {
    while(true) {
      outputStream.println("Course ID: ");
      try {
        String courseid = inputReader.readLine();
        if (courses.stream().anyMatch(course -> course.getCourseid().equals(courseid))) {
          outputStream.println("Course ID exists! Please try again!");
          continue;
        }
        return courseid;
      } catch(Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
      }
    }
  }

  private static boolean readInputYorN(BufferedReader inputReader, PrintStream outputStream, String prompt){
    while(true){
      try {
        outputStream.println(prompt);
        return getYorN(inputReader.readLine());
      } catch(Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
      }
    }
  }

  private static boolean arrayContains(String[] array, String value) {
    for (String str : array) {
        if (str.equals(value)) {
            return true;
        }
    }
    return false;
  }

  private static int arrayIndexOf(String[] array, String value) {
    for (int i = 0; i < array.length; i++) {
        if (array[i].equals(value)) {
            return i;
        }
    }
    return -1; // Value not found
}

  private static List<Integer> readInputOrder(BufferedReader inputReader, PrintStream outputStream){
    while(true) {
      outputStream.println("What is the order of column data in your file? Please use numbers to indicate the order.");
      outputStream.println("  0.Anything not mentioned below\n  1. Student ID(required)\n  2. Student legal name(required)\n  3. Student display name(optional)\n  4. Student email(required)");
      outputStream.println("(Example: enter 3,0,1,4 if your columns are in this order: legal name, phone number, id, email)");
      try {
        String line = inputReader.readLine();
        String[] order = line.split(",");      
        if(!arrayContains(order, "1") || !arrayContains(order, "2") || !arrayContains(order, "4")){
                
        }
        List<Integer> indexInOrder = new ArrayList<>();
        indexInOrder.add(arrayIndexOf(order, "1"));
        indexInOrder.add(arrayIndexOf(order, "2"));
        if(arrayIndexOf(order, "3") == -1){
          indexInOrder.add(arrayIndexOf(order, "2"));
        }
        else {
          indexInOrder.add(arrayIndexOf(order, "3"));
        }
        indexInOrder.add(arrayIndexOf(order, "4"));
        return indexInOrder;
      } catch(Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
      }
    }
  }


  
  private static Course createCourse(BufferedReader inputReader, PrintStream outputStream, List<Course> courses, Professor professor, Map<String, Student> allStudents) {
    outputStream.println("You are creating a new course! Please enter the information required below.");
    String courseid = getInputCourseID(inputReader, outputStream, courses);
    boolean allowChangeName = readInputYorN(inputReader, outputStream, "Are students allowed to change their display name?(Y/N)");

    outputStream.println("You are loading the roaster from a csv file! Please enter the information required below.");
    boolean withHeader = readInputYorN(inputReader, outputStream, "Does this csv file contain headers?(Y/N)");
    List<Integer> order = readInputOrder(inputReader, outputStream);

    
    while (true) {
      try{
        Course newCourse = new Course(courseid, professor, allowChangeName);
        // create directory and files for the course
         FileHandler.createCourse(courseid, professor.getPersonalID()); 
         // load student list of the course
         outputStream.println("Please provide the absolute path of the csv file you wan to load the roster from:");
         FileHandler.loadRosterFromCSVFile(courseid, newCourse, inputReader.readLine(), order, withHeader);
         return newCourse;
       } catch (Exception e) {
        outputStream.println(e.getMessage() + " Please try again!");
        //FileHandler.deleteCourse(courseid); //if not successful, delete the course directory
      }
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
