package tracker;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        System.out.print("Learning Progress Tracker > ");
        Scanner scanner = new Scanner(System.in);
        int totalStudents = 0;

        while (true) {
            String input = scanner.nextLine().strip().toLowerCase();
            if (input.isBlank() || input.matches("\\s+")) {
                System.out.println("No input.");
            } else if (input.equals("exit")) {
                System.out.println("Bye!");
                break;
            } else if (input.equals("add students")) {
                totalStudents += addStudents(scanner);
            } else if (input.equals(("back"))) {
                System.out.println("Enter 'exit' to exit the program.");

            } else {
                System.out.println("Unknown command!");
            }
        }
        System.out.println("Total " + totalStudents + " students have been added.");
    }

    private static int addStudents(Scanner scanner) {
        int addedStudents = 0;
        System.out.println("Enter student credentials or 'back' to return:");

        while (true) {
            String input = scanner.nextLine().strip().toLowerCase();
            if (input.equals("back")) {
                System.out.println("Total " + addedStudents + " students have been added.");
                break;
            } else {
                String validationMessage = validateCredentials(input);
                if (validationMessage.equals("Valid")) {
                    System.out.println("The student has been added.");
                    addedStudents++;
                } else {
                    System.out.println(validationMessage);
                }
            }
        }
        return addedStudents;
    }

    private static String validateCredentials(String input) {
        String[] parts = input.split(" ");
        if (parts.length == 3) {
            String firstName = parts[0];
            String lastName = parts[1];
            String email = parts[2];

            boolean isFirstNameValid = isValidName(firstName);
            boolean isLastNameValid = isValidName(lastName);
            boolean isEmailValid = isValidEmail(email);

            if (isFirstNameValid && isLastNameValid && isEmailValid) {
                return "Valid";
            } else {
                StringBuilder errorMessage = new StringBuilder("Incorrect credentials. ");
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
        return Pattern.matches("[A-Za-z'-]+", name) && name.length() >= 2;
    }

    private static boolean isValidEmail(String email) {
        // Regular expression for a simple email format validation
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(emailPattern, email);
    }
}