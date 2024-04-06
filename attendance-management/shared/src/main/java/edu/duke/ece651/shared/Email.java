package edu.duke.ece651.shared;

import java.util.Objects;
import java.util.regex.*;

/**
 * This class represent email address.
 * @author Can Pei
 * @version 1.0
 */
public class Email {
    /**
     * Email address
     */
    private String emailAddr;

    /**
     * Regular expression of legal email address syntax
     */
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Check if a string is of valid email address syntax.
     * @param email is the string to check.
     */
    public static boolean checkValid(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
    /**
     * Constructor
     */
    public Email(String emailAddr) {
        if (checkValid(emailAddr)) {
            this.emailAddr = emailAddr;
        }
        else {
            throw new IllegalArgumentException(
                    "That email address is invalid: it does not have the correct format.\n"
            );
        }
    }

    /**
     * Email getter
     */
    public String getEmailAddr() {
        return emailAddr;
    }

    /**
     * Email equal operator
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(emailAddr, email.emailAddr);
    }

    /**
     * Email hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(emailAddr);
    }

}
