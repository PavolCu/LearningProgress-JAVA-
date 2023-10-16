package tracker;

import java.util.*;
import java.util.regex.Pattern;

public class Main {
    private static int totalStudents = 0; // Initialize totalStudents to 0
    private static Set<String> emailSet = new HashSet<>(); // Set to store unique email addresses
    private static List<Integer> studentIds = new ArrayList<>(); // List to store student IDs
    private static Map<Integer, Map<String, Integer>> studentProgress = new HashMap<>(); // Map to store student progress

    private static void findStudent(Scanner scanner) {
        System.out.println("Enter an id or 'back' to return:");
        String input = scanner.nextLine().strip().toLowerCase();
        if (input.equals("back")) {
            return;
        }
        try {
            int studentId = Integer.parseInt(input);
            if (studentProgress.containsKey(studentId)) {
                Map<String, Integer> courses = studentProgress.get(studentId);
                int javaPoints = courses.getOrDefault("Java", 0);
                int dsaPoints = courses.getOrDefault("DSA", 0);
                int databasesPoints = courses.getOrDefault("Databases", 0);
                int springPoints = courses.getOrDefault("Spring", 0);
                System.out.printf("%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d%n", studentId, javaPoints, dsaPoints, databasesPoints, springPoints);
            } else {
                System.out.printf("No student is found for id=%d.%n", studentId);
            }
        } catch (NumberFormatException e) {
            System.out.printf("Invalid ID: %s.%n", input);
        }
    }

    private static void listStudents() {
        if (studentIds.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("Students:");
            for (int studentId : studentIds) {
                System.out.println(studentId);
            }
        }
    }

    private static int addStudents(Scanner scanner) {
        int addedStudents = 0; // Track the number of students added in this session
        System.out.println("Enter student credentials or 'back' to return:");

        while (true) {
            String input = scanner.nextLine().strip().toLowerCase();
            if (input.equals("back")) {
                System.out.println("Total " + addedStudents + " students have been added.");
                break;
            } else if (!input.isEmpty()) {  // Check if the input is not empty
                String validationMessage = validateCredentials(input);
                if (validationMessage.equals("The student has been added.")) {
                    System.out.println("The student has been added.");
                    addedStudents++;
                    studentIds.add(totalStudents + addedStudents); // Add student ID to the list
                    studentProgress.put(totalStudents + addedStudents, new HashMap<>()); // Initialize progress for student
                } else {
                    System.out.println(validationMessage);
                }
            } else {
                System.out.println("Incorrect credentials."); // Changed message for empty input
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
                if (emailSet.contains(email)) {
                    return "This email is already taken.";
                } else {
                    emailSet.add(email); // Add the email to the set
                    return "The student has been added.";
                }
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
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z0-9+_.-]+$";
        return Pattern.matches(emailPattern, email);
    }

    private static void addPoints(Scanner scanner) {
        System.out.println("Enter an id and points or 'back' to return:");
        String input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("back")) {
            return;
        }

        String[] parts = input.split(" ");
        if (parts.length != 5) {
            System.out.println("Incorrect points format.");
            return;
        }

        try {
            int id = Integer.parseInt(parts[0]);
            int javaPoints = Integer.parseInt(parts[1]);
            int dsaPoints = Integer.parseInt(parts[2]);
            int databasesPoints = Integer.parseInt(parts[3]);
            int springPoints = Integer.parseInt(parts[4]);

            if (javaPoints >= 0 && dsaPoints >= 0 && databasesPoints >= 0 && springPoints >= 0) {
                if (studentProgress.containsKey(id)) {
                    Map<String, Integer> courses = studentProgress.get(id);
                    courses.put("Java", javaPoints);
                    courses.put("DSA", dsaPoints);
                    courses.put("Databases", databasesPoints);
                    courses.put("Spring", springPoints);
                    System.out.println("Points updated.");
                } else {
                    System.out.printf("No student is found for id=%d.%n", id);
                }
            } else {
                System.out.println("Incorrect points format.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Incorrect points format.");
        }
    }




    public static void main(String[] args) {
        System.out.print("Learning Progress Tracker > ");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine().strip().toLowerCase();
            if (input.isBlank() || input.matches("\\s+")) {
                System.out.print("No input.");
            } else if (input.equals("exit")) {
                System.out.println(" Bye!");
                break;
            } else if (input.equals("add students")) {
                totalStudents += addStudents(scanner);
            } else if (input.equals("list")) {
                listStudents();
            } else if (input.equals("add points")) {
                addPoints(scanner);
            } else if (input.equals("find")) {
                findStudent(scanner);
            } else if (input.equals("back")) {
                System.out.println("Enter 'exit' to exit the program.");
            } else {
                System.out.println("Unknown command.");
            }
        }
    }
}