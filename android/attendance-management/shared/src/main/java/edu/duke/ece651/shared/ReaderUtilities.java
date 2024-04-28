package edu.duke.ece651.shared;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ReaderUtilities {
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
   * Reads user input to determine if they want to continue.
   * 
   * @param inputReader  The BufferedReader for user input.
   * @param outputStream The PrintStream for output.
   * @param prompt       The prompt to display to the user.
   * @return True if the user input is 'y', false if 'n'.
   */
  public static boolean readInputYorN(BufferedReader inputReader, PrintStream outputStream, String prompt) {
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
   * Get a choice of yes or no from a string.
   * 
   * @param choiceStr A string for the choice.
   * @return True for yes, false for no.
   */
  public static boolean getYorN(String choiceStr) {
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

  public static char readSingleLetter(BufferedReader reader) throws IOException {
    String line = reader.readLine();
    if (line != null && line.length() == 1 && Character.isLetter(line.charAt(0))) {
      return line.charAt(0);
    } else {
      throw new IllegalArgumentException("Invalid input: you should only type in one letter!");
    }
  }

  /**
   * Read export file format from input.
   */
  public static String readFormat(String prompt, BufferedReader inputReader, PrintStream out) throws IOException {
    // out.print("--------------------------------------------------------------------------------\n");
    // out.print(prompt);
    // out.print("--------------------------------------------------------------------------------\n");
    // out.println();

    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException(
              "You didn't type in any instruction!\n");
    }
    if (!(s.equals("xml") || s.equals("json"))) {
      throw new IllegalArgumentException("Invalid format! You should only choose json or xml!");
    }
    return s;
  }

}
