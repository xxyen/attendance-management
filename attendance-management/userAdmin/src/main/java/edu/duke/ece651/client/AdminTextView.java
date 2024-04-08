package edu.duke.ece651.userAdmin;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.List;

import edu.duke.ece651.shared.Email;
import edu.duke.ece651.shared.Professor;
import edu.duke.ece651.shared.Student;
import edu.duke.ece651.shared.ReaderUtilities;
import edu.duke.ece651.userAdmin.UserManagement;


public class AdminTextView {
    private static UserManagement admin = new UserManagement();

    public static void start(BufferedReader inputReader, PrintStream outputStream) {
        outputStream.println("Welcome to the user admini app!");
        chooseBasicAction(inputReader, outputStream);
    }

    public static void chooseBasicAction(BufferedReader inputReader, PrintStream outputStream) {
        while(true){
            outputStream.println("What would you like to do? Please enter the number to choose.  Hint: the user could be either a faculty member or a student.");
            outputStream.println("1. add a user\n2. remove a user\n3. modify a user\n4. Modify display name modification setting\n5. exit the system");
            int choice = ReaderUtilities.readPositiveInteger(inputReader);
            if(choice == 1) {
                addUser(inputReader, outputStream);
            } else if(choice == 2) {
                removeUser(inputReader, outputStream);
            } else if(choice == 3) {
                modifyUser(inputReader, outputStream);
            } else if(choice == 4) {
                setDisplayNamePermission(inputReader, outputStream);
            } else if(choice == 5) {
                break;
            } else {
                outputStream.println("Please enter a positive integer within the listed ones! (1/2/3/4/5)");
            }
        }
    }

    private static void setDisplayNamePermission(BufferedReader inputReader, PrintStream outputStream) {
        if (UserManagement.getDisplayNamePermission()) {
            outputStream.println("Current setting allows students to have display names different from their legal names.");
        } else {
            outputStream.println("Current setting DOES NOT allow students to have display names different from their legal names.");
        }
        String prompt = "Please enter Y or N to specify if you would like to allow it. Warning: this is a global setting, not specific to any student.";
        boolean canModifyDisplayName = ReaderUtilities.readInputYorN(inputReader, outputStream, prompt);
        UserManagement.setDisplayNamePermission(canModifyDisplayName);
        outputStream.println("Setting updated successfully!");
    }

    private static void addUser(BufferedReader inputReader, PrintStream outputStream){
        while(true) {
            outputStream.println("Which type of user would you like to create? Please enter the number to choose.\n1. a student\n2. a faculty member");
            int choice = ReaderUtilities.readPositiveInteger(inputReader);
            if(choice == 1) {
                addStudent(inputReader, outputStream);
                break;
            } else if(choice == 2) {
                addFaculty(inputReader, outputStream);
                break;
            }
            else {
                outputStream.println("Please enter a positive integer within the listed ones! (1/2)");
            }
        }
    }

    private static void addFaculty(BufferedReader inputReader, PrintStream outputStream) {
        while(true) {
            outputStream.println("You are creating a faculty member. Please provide listed information separated by comma, no space(unless it is part of the information):");
            outputStream.println("userid(must be unique for everyone), legal name, email address");
            try {
                String[] info = inputReader.readLine().split(",");
                if(info.length != 3 ){
                    throw new IllegalArgumentException("Your input format is invalid!");
                }
                if(UserManagement.checkFacultyExistsByID(info[0])) {
                    throw new IllegalArgumentException("Faculty member with this userid exists already!");
                }
                if(UserManagement.checkFacultyExistsByEmail(info[2])) {
                    throw new IllegalArgumentException("Faculty member with this email address exists already!");
                }
                if(!Email.checkValid(info[2])) {
                    throw new IllegalArgumentException("The email address you provide is invalid!");
                }
                Professor newFaculty = new Professor(info[0], info[1], new Email(info[2]));
                UserManagement.facultySignUp(newFaculty);
                outputStream.println("Faculty memeber " + info[0] + " added successfully!");
                break;
            } catch(Exception e) {
                outputStream.println("Failed to add faculty member: " + e.getMessage() + " Please try again!");
            }
        }
        
    }

    private static void addStudent(BufferedReader inputReader, PrintStream outputStream) {
        if(UserManagement.getDisplayNamePermissionModificationCount() == 0) {
            String prompt = "By default, students are not allowed to have display names different from their legal names. Would you like to allow this? (Y/N)";
            boolean canModifyDisplayName = ReaderUtilities.readInputYorN(inputReader, outputStream, prompt);
            UserManagement.setDisplayNamePermission(canModifyDisplayName);
        }
        while(true) {
            outputStream.println("You are creating a student. Their display names are the same as their legal names for now. You may modify those later if it is allowed.\nPlease provide listed information separated by comma, no space(unless it is part of the information):");
            outputStream.println("userid(must be unique for everyone), legal name, email address");
            try {                
                String[] info = inputReader.readLine().split(",");
                if(info.length != 3 ){
                    throw new IllegalArgumentException("Your input format is invalid!");
                }
                if(UserManagement.checkStudentExistsByID(info[0])) {
                    throw new IllegalArgumentException("Student with this userid exists already!");
                }
                if(UserManagement.checkStudentExistsByEmail(info[2])) {
                    throw new IllegalArgumentException("Student with this email address exists already!");
                }
                if(!Email.checkValid(info[2])) {
                    throw new IllegalArgumentException("The email address you provide is invalid!");
                }
                Student newStudent = new Student(info[0], info[1], info[1], new Email(info[2]));
                UserManagement.studentSignUp(newStudent);
                outputStream.println("Student " + info[0] + " added successfully!");
                break;
            } catch(Exception e) {
                outputStream.println("Failed to add student: " + e.getMessage() + " Please try again!");
            }
        }
    }

    private static void removeUser(BufferedReader inputReader, PrintStream outputStream) {
        while(true) {
            outputStream.println("Which type of user would you like to remove? Please enter the number to choose.\n1. a student\n2. a faculty member");
            int choice = ReaderUtilities.readPositiveInteger(inputReader);
            if(choice == 1) {
                removeStudent(inputReader, outputStream);
                break;
            } else if(choice == 2) {
                removeFaculty(inputReader, outputStream);
                break;
            }
            else {
                outputStream.println("Please enter a positive integer within the listed ones! (1/2)");
            }
        }
    }

    private static void removeFaculty(BufferedReader inputReader, PrintStream outputStream) {
        while(true) {
            try{
                outputStream.println("Please enter the userid of the faculty member to be removed:");
                String useridToRemove = inputReader.readLine();
                if(!UserManagement.checkFacultyExistsByID(useridToRemove)) {
                    throw new IllegalArgumentException("No such faculty member with this user id!");
                }
                UserManagement.removeFaculty(useridToRemove);
                outputStream.println("Faculty member " + useridToRemove + " removed successfully!");
                break;
            } catch(Exception e) {
                outputStream.println("Failed to remove faculty member: " + e.getMessage() + " Please try again!");
            }
        }
    }
    

    private static void removeStudent(BufferedReader inputReader, PrintStream outputStream) {
        while(true) {
            try{
                outputStream.println("Please enter the userid of the student to be removed:");
                String useridToRemove = inputReader.readLine();
                if(!UserManagement.checkStudentExistsByID(useridToRemove)) {
                    throw new IllegalArgumentException("No such student with this user id!");
                }
                UserManagement.removeStudent(useridToRemove);
                outputStream.println("Student " + useridToRemove + " removed successfully!");
                break;
            } catch(Exception e) {
                outputStream.println("Failed to remove student: " + e.getMessage() + " Please try again!");
            }
        }
    }

    private static void modifyUser(BufferedReader inputReader, PrintStream outputStream) {
        while(true) {
            outputStream.println("Which type of user would you like to modify? How would you prefer to search for the faculty member? Please enter the number to choose.");
            outputStream.println("1. search for a student by user id, and modify it\n2. get student(s) info by legal name, and search by user id to modify it");
            outputStream.println("3. search for a faculty member by user id, and modify it\n4. get faculty member(s) info by legal name, and search by user id to modify it");

            int choice = ReaderUtilities.readPositiveInteger(inputReader);
            if(choice == 1) {
                Student student = getStudentByID(inputReader, outputStream);
                modifyStudent(inputReader, outputStream, student);
                break;
            } else if(choice == 2) {
                findStudentByLegalName(inputReader, outputStream);
                Student student = getStudentByID(inputReader, outputStream);
                modifyStudent(inputReader, outputStream, student);
                break;
            } else if(choice == 3) {
                Professor faculty = getFacultyByID(inputReader, outputStream);
                modifyFaculty(inputReader, outputStream, faculty);
                break;
            } else if(choice == 4) {
                findFacultyByLegalName(inputReader, outputStream);
                Professor faculty = getFacultyByID(inputReader, outputStream);
                modifyFaculty(inputReader, outputStream, faculty);
                break;
            }
            else {
                outputStream.println("Please enter a positive integer within the listed ones! (1/2/3/4)");
            }
        }
    }

    private static Student getStudentByID(BufferedReader inputReader, PrintStream outputStream) {
        while(true) {
            try {
                outputStream.println("Please provide user id of the student you would like to modify:");
                String userid = inputReader.readLine();
                if(!UserManagement.checkStudentExistsByID(userid)) {
                    throw new IllegalArgumentException("No such student with this user id found!");
                }
                return UserManagement.getStudentByID(userid);
            } catch(Exception e) {
                outputStream.println("Failed to find the faculty member: " + e.getMessage() + " Please try again!");
            }
        }
    }

    private static void findStudentByLegalName(BufferedReader inputReader, PrintStream outputStream) {
        while(true) {
            try {
                outputStream.println("Please provide legal name of the student you would like to modify:");
                String legalName = inputReader.readLine();
                if(!UserManagement.checkStudentExistsByLegalName(legalName)) {
                    throw new IllegalArgumentException("No such student with this legal name found!");
                } else {
                    outputStream.println("Please check the result:");
                    List<Student> studentList = UserManagement.getStudentByLegalName(legalName);
                    for(int i = 0; i < studentList.size(); i ++) {
                        Student student = studentList.get(i);
                        outputStream.println(i+1 + ". userid: " + student.getUserid() + ",  email: " + student.getEmail().getEmailAddr());
                    }
                    break;
                }
            } catch(Exception e) {
                outputStream.println("Failed to find the faculty member: " + e.getMessage() + " Please try again!");
            }
        }
    }

    private static Professor getFacultyByID(BufferedReader inputReader, PrintStream outputStream) {
        while(true) {
            try {
                outputStream.println("Please provide user id of the faculty member you would like to modify:");
                String userid = inputReader.readLine();
                if(!UserManagement.checkFacultyExistsByID(userid)) {
                    throw new IllegalArgumentException("No such faculty member with this user id found!");
                }
                return UserManagement.getFacultyByID(userid);
            } catch(Exception e) {
                outputStream.println("Failed to find the faculty member: " + e.getMessage() + " Please try again!");
            }
        }
    }

    private static void findFacultyByLegalName(BufferedReader inputReader, PrintStream outputStream) {
        while(true) {
            try {
                outputStream.println("Please provide legal name of the faculty member you would like to modify:");
                String legalName = inputReader.readLine();
                if(!UserManagement.checkFacultyExistsByLegalName(legalName)) {
                    throw new IllegalArgumentException("No such faculty member with this legal name found!");
                } else {
                    outputStream.println("Please check the result:");
                    List<Professor> facultyList = UserManagement.getFacultyByLegalName(legalName);
                    for(int i = 0; i < facultyList.size(); i ++) {
                        Professor faculty = facultyList.get(i);
                        outputStream.println(i+1 + ". userid: " + faculty.getUserid() + ",  email: " + faculty.getEmail().getEmailAddr());
                    }
                    break;
                }
            } catch(Exception e) {
                outputStream.println("Failed to find the faculty member: " + e.getMessage() + " Please try again!");
            }
        }
    }


    private static void modifyFaculty(BufferedReader inputReader, PrintStream outputStream, Professor faculty) {
        while(true) {
            outputStream.println("You are modifying this faculty member:");
            outputStream.println("userid: " + faculty.getUserid());
            outputStream.println("legal name: " + faculty.getName());
            outputStream.println(faculty.getEmail().getEmailAddr() + "\nPlease provide listed information:\nemail address");
            
            try {
                String emailString = inputReader.readLine();
                if(!Email.checkValid(emailString)) {
                    throw new IllegalArgumentException("The email address you provide is invalid!");
                } 
                Professor modifiedFaculty = new Professor(faculty.getUserid(), faculty.getName(), new Email(emailString));
                
                UserManagement.updateFaculty(modifiedFaculty);
                outputStream.println("Faculty member " + faculty.getUserid() + " modified successfully!");
                break;
            } catch(Exception e) {
                outputStream.println("Failed to modify faculty member: " + e.getMessage() + " Please try again!");
            }
        }
    }


    private static void modifyStudent(BufferedReader inputReader, PrintStream outputStream, Student student) {
        while(true) {
            outputStream.println("You are modifying this student:");
            outputStream.println("userid: " + student.getUserid());
            outputStream.println("legal name: " + student.getLegalName());
            String prompt = "email: " + student.getEmail().getEmailAddr() + "\nPlease provide listed information:";
            if(UserManagement.getDisplayNamePermission()) {
                outputStream.println("display name: " + student.getDisplayName());
                outputStream.println(prompt);
                outputStream.println("display name, email address  (separated by comma, no space, unless it is part of the information)");
            }
            else {
                outputStream.println(prompt);
                outputStream.println("email address");
            }
            
            try {
                String[] info = inputReader.readLine().split(",");
                String emailString = "";
                int expectedLength = -1;
                if(UserManagement.getDisplayNamePermission()) {
                    expectedLength = 2;
                    emailString = info[1];
                } else {
                    expectedLength = 1;
                    emailString = info[0];
                }
                if(info.length != expectedLength ){
                    throw new IllegalArgumentException("Your input format is invalid!");
                }
                if(!Email.checkValid(emailString)) {
                    throw new IllegalArgumentException("The email address you provide is invalid!");
                } 

                String displayNameString = student.getDisplayName();
                if(UserManagement.getDisplayNamePermission()) {
                    displayNameString = info[0];
                } 
                Student modifiedStudent = new Student(student.getUserid(), student.getLegalName(), displayNameString, new Email(emailString));
                
                UserManagement.updateStudent(modifiedStudent);
                outputStream.println("Student " + student.getUserid() + " modified successfully!");
                break;
            } catch(Exception e) {
                outputStream.println("Failed to modify student: " + e.getMessage() + " Please try again!");
            }
        }
    }

    
    

}
