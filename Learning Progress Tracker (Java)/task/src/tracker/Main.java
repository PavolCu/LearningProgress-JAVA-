package tracker;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static int totalStudents = 0; // Initialize totalStudents to 0

    public static void main(String[] args) {
        System.out.print("Learning Progress Tracker > ");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine().strip().toLowerCase();
            if (input.isBlank() || input.matches("\\s+")) {
                System.out.println("No input.");
            } else if (input.equals("exit")) {
                System.out.println(" Bye!");
                break;
            } else if (input.equals("add students")) {
                totalStudents += addStudents(scanner);
            } else if (input.equals(("back"))) {
                System.out.println("Enter 'exit' to exit the program.");
            } else {
                System.out.println("Unknown command!");
            }
        }
    }

    private static int addStudents(Scanner scanner) {
        int addedStudents = 0; // Track the number of students added in this session
        System.out.println("Enter student credentials or 'back' to return:");

        while (true) {
            String input = scanner.nextLine().strip().toLowerCase();
            if (input.equals("back")) {
                System.out.println("Total " + addedStudents + " students have been added in this session.");
                break;
            } else if (!input.isEmpty()) {  // Check if the input is not empty
                String validationMessage = validateCredentials(input);
                if (validationMessage.equals("The student has been added.")) {
                    System.out.println("The student has been added.");
                    addedStudents++;
                } else {
                    System.out.println(validationMessage);
                }
            } else {
                System.out.println("No input.");
            }
        }

        return addedStudents;
    }

    private static String validateCredentials(String input) {
        String[] parts = input.split(" ");
        if (parts.length >= 3) {
            String firstName = parts[0];
            String lastName = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length - 1));
            String email = parts[parts.length - 1];

            boolean isFirstNameValid = isValidName(firstName);
            boolean isLastNameValid = isValidName(lastName);
            boolean isEmailValid = isValidEmail(email);

            if (isFirstNameValid && isLastNameValid && isEmailValid) {
                return "The student has been added.";
            } else {
                StringBuilder errorMessage = new StringBuilder();
                if (!isFirstNameValid) {
                    errorMessage.append("Incorrect first name. ");
                }
                if (!isLastNameValid) {
                    errorMessage.append("Incorrect last name. ");
                }
                if (!isEmailValid) {
                    errorMessage.append("Incorrect email.");
                }
                return errorMessage.toString();
            }
        }
        return "Incorrect credentials.";
    }

    private static boolean isValidName(String name) {
        // Allow multiple words, hyphens, and apostrophes in names
        return Pattern.matches("^[A-Za-z]+([ '-][A-Za-z]+)*$", name) && name.length() >= 2;
    }

    private static boolean isValidEmail(String email) {
        // Ensure the email pattern allows valid email addresses
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailPattern, email);
    }
}
