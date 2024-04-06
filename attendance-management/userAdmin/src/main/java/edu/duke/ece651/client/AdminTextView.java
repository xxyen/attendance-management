package edu.duke.ece651.client;

import java.io.BufferedReader;
import java.io.PrintStream;

import edu.duke.ece651.shared.Email;
import edu.duke.ece651.shared.Professor;
import edu.duke.ece651.shared.ReaderUtilities;
import edu.duke.ece651.client.UserManagement;


public class AdminTextView {
    private static UserManagement admin = new UserManagement();

    public static void start(BufferedReader inputReader, PrintStream outputStream) {
        outputStream.println("Welcome to the user admini app!");
        // chooseBasicAction(inputReader, outputStream);
    }

    public static void chooseBasicAction(BufferedReader inputReader, PrintStream outputStream) {
        while(true){
            outputStream.println("What would you like to do? Please enter the number to choose.  Hint: the user could be either a faculty member or a student.");
            outputStream.println("1. add a user\n2. remove a user\n3. modify a user\n4. exit the system");
            int choice = ReaderUtilities.readPositiveInteger(inputReader);
            if(choice == 1) {
                addUser(inputReader, outputStream);
            } else if(choice == 2) {
                break;
                //removeUser(inputReader, outputStream);
            } else if(choice == 3) {
                break;
                //modifyUser(inputReader, outputStream);
            } else if(choice == 4) {
                break;
            } else {
                outputStream.println("Please enter a positive integer within the listed ones! (1/2/3/4)");
            }
        }

    }

    private static void addUser(BufferedReader inputReader, PrintStream outputStream){
        outputStream.println("Which type of user would you like to create? Please enter the number to choose.\n1. a student\n2. a faculty member");
        int choice = ReaderUtilities.readPositiveInteger(inputReader);
        if(choice == 1) {
            addStudent(inputReader, outputStream);
        } else if(choice == 2) {
            addFaculty(inputReader, outputStream);
        }
        else {
            outputStream.println("Please enter a positive integer within the listed ones! (1/2)");
        }
    }

    private static void addFaculty(BufferedReader inputReader, PrintStream outputStream) {
        while(true) {
            outputStream.println("You are creating a faculty member. Please provide listed information separated by comma, no space(unless it is part of the information):");
            outputStream.println("userid(must be unique for everyone), legal name, email address");
            try {
                String[] info = inputReader.readLine().split(",");
                // !!! if can find info[0] in db: already exists
                if(!Email.checkValid(info[2])) {
                    throw new IllegalArgumentException("The email address you provide is invalid!");
                }
                Professor newFaculty = new Professor(info[0], info[1], new Email(info[2]));
                UserManagement.facultySignUp(newFaculty);
            } catch(Exception e) {
                outputStream.println("Failed to read faculty information: " + e.getMessage() + " Please try again!");
            }
        }
        
    }

    private static void addStudent(BufferedReader inputReader, PrintStream outputStream) {
        boolean canModifyDisplayName = ReaderUtilities.readInputYorN(inputReader, outputStream, "Are students allowed to have display names different from their legal names? (Y/N)");
        while(true) {
            outputStream.println("You are creating a student. Please provide listed information separated by comma, no space(unless it is part of the information):");
            outputStream.println("userid(must be unique for everyone), legal name, email address");
            try {
                String[] info = inputReader.readLine().split(",");
                // !!! if can find info[0] in db: already exists
                if(!Email.checkValid(info[2])) {
                    throw new IllegalArgumentException("The email address you provide is invalid!");
                }
                Professor newFaculty = new Professor(info[0], info[1], new Email(info[2]));
                UserManagement.facultySignUp(newFaculty);
            } catch(Exception e) {
                outputStream.println("Failed to read faculty information: " + e.getMessage() + " Please try again!");
            }
        }
    }

    private static void removeUser(BufferedReader inputReader, PrintStream outputStream) {
        outputStream.println("Which type of user would you like to remove? Please enter the number to choose.\n1. a student\n2. a faculty member");
        int choice = ReaderUtilities.readPositiveInteger(inputReader);
        if(choice == 1) {
            //removeStudent(inputReader, outputStream);
        } else if(choice == 2) {
            //removeFaculty(inputReader, outputStream);
        }
        else {
            outputStream.println("Please enter a positive integer within the listed ones! (1/2)");
        }
    }

    // private static void removeFaculty(BufferedReader inputReader, PrintStream outputStream) {
    //     try{
    //         outputStream.println("Please enter the userid of the faculty member to be removed:");
    //         String useridToRemove = inputReader.readLine();
            
    //     }
    // }
    

    // private static void removeStudent(BufferedReader inputReader, PrintStream outputStream) {
    //     try{
    //         outputStream.println("Please enter the userid of the student to be removed:");
    //         String useridToRemove = inputReader.readLine();
            
    //     }
    // }

    private static void modifyUser(BufferedReader inputReader, PrintStream outputStream) {

    }

    private static void modifyFaculty(BufferedReader inputReader, PrintStream outputStream) {

    }

    private static void modifyStudent(BufferedReader inputReader, PrintStream outputStream) {

    }

    
    

}
