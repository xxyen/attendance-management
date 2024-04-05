package edu.duke.ece651.client;

import java.io.BufferedReader;
import java.io.PrintStream;

import edu.duke.ece651.shared.ReaderUtilities;
import edu.duke.ece651.client.UserManagement;


public class AdminTextView {
    private static UserManagement admin = new UserManagement();

    public static void start(PrintStream outputStream) {
        outputStream.println("Welcome to the user admini app!");
    }

    public static void chooseBasicAction(BufferedReader inputReader, PrintStream outputStream) {
        outputStream.println("What would you like to do?  Hint: the user could be either faculty or student.");
        outputStream.println("1. add a user\n2. remove a user\n2. remove a user\n3. modify a user\n4. search a user");
        int choice = ReaderUtilities.readPositiveInteger(inputReader);
    }

}
