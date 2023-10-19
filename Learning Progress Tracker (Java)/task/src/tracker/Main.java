package tracker;

import java.util.*;
import java.util.regex.Pattern;

class Student {
    final private String firstName;
   final private String lastName;
    final private String email;

    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }
}

public class Main {
    private static int totalStudents = 0;
    final private static Map<Integer, Student> studentProgress = new HashMap<>();
    final private static Map<Integer, int[]> studentPoints = new HashMap<>();
    private static boolean studentsAdded = false;

    private static void findStudent(Scanner scanner) {
        System.out.print("Enter an id or 'back' to return:");
        String input = scanner.nextLine().strip().toLowerCase();
        if (input.equals("back")) {
            return;
        }
        try {
            int studentId = Integer.parseInt(input);
            if (studentProgress.containsKey(studentId)) {
                Student student = studentProgress.get(studentId);
                System.out.println(student);
            } else {
                System.out.printf("No student is found for id=%d.%n", studentId);
            }
        } catch (NumberFormatException e) {
            System.out.printf("Invalid ID: %s.%n", input);
        }
    }

    private static boolean isValidName(String name) {
        return Pattern.matches("^[A-Za-z]+([ '-][A-Za-z]+)*$", name) && name.length() >= 2;
    }

    private static boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z0-9+_.-]+$";
        return Pattern.matches(emailPattern, email);
    }

    private static void addStudents(Scanner scanner) {
        System.out.print("Enter student credentials or 'back' to return:");
        int addedStudents = 0;

        while (true) {
            String studentInput = scanner.nextLine().strip().toLowerCase();
            if (studentInput.equals("back")) {
                if (addedStudents > 0) {
                    System.out.println("Total " + addedStudents + " students have been added.");
                }
                return;
            } else if (studentInput.equals("exit")) {
                System.out.println("Bye!");
                System.exit(0); // Exit the program
            } else if (!studentInput.isEmpty()) {
                String[] parts = studentInput.split(" ");
                if (parts.length >= 3) {
                    String firstName = parts[0];
                    String lastName = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length - 1));
                    String email = parts[parts.length - 1];

                    // Validate and add student logic here
                    // ... (same logic as before)

                    if (isValidName(firstName) && isValidName(lastName) && isValidEmail(email)) {
                        // Check if the email is already in use
                        boolean emailExists = studentProgress.values().stream()
                                .anyMatch(student -> student.getEmail().equalsIgnoreCase(email));
                        if (emailExists) {
                            System.out.println("This email is already taken.");
                        } else {
                            Student student = new Student(firstName, lastName, email);
                            totalStudents++;
                            studentProgress.put(totalStudents, student);
                            addedStudents++;
                            System.out.println("The student has been added.");
                        }
                    } else {
                        if (!isValidName(firstName)) {
                            System.out.println("Incorrect first name.");
                        }
                        if (!isValidName(lastName)) {
                            System.out.println("Incorrect last name.");
                        }
                        if (!isValidEmail(email)) {
                            System.out.println("Incorrect email.");
                        }
                    }
                } else {
                    System.out.println("Incorrect credentials.");
                }
            } else {
                System.out.println("Incorrect credentials.");
            }
            System.out.print("Enter student credentials or 'back' to return:");
        }
    }


    private static void listStudents() {
        System.out.println("Students:");
        if (studentPoints.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (int studentId : studentPoints.keySet()) {
                int[] points = studentPoints.get(studentId);
                boolean hasPositivePoints = false;
                for (int point : points) {
                    if (point > 0) {
                        hasPositivePoints = true;
                        break;
                    }
                }
                if (hasPositivePoints) {
                    System.out.printf("%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d%n",
                            studentId, points[0], points[1], points[2], points[3]);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean studentsAdded = false;

        System.out.print("Learning Progress Tracker > ");

        while (true) {
            String input = scanner.nextLine().strip().toLowerCase();
            if (input.isBlank()) {
                System.out.print("No input.");
            } else if (input.equals("exit")) {
                if (studentsAdded) {
                    System.out.println("Bye!");
                    break;
                } else {
                    System.out.println("No students added. Exiting without saving data.");
                    break;
                }
            } else if (input.equals("add students")) {
                addStudents(scanner);
                studentsAdded = true;
            } else if (input.equals("list")) {
                listStudents();
            } else if (input.equals("add points")) {
                System.out.println("Enter an id and points or 'back' to return:");
                addPoints(scanner);
            } else if (input.equals("find")) {
                findStudent(scanner);
            } else if (input.equals("back")) {
                System.out.print("Enter 'exit' to exit the program.");
                String backInput = scanner.nextLine().strip().toLowerCase();
                if (backInput.equals("exit")) {
                    System.out.println("Bye!");
                    break;
                }
            } else {
                System.out.print("Unknown command.");
            }
        }

        scanner.close();
    }


    private static void addPoints(Scanner scanner) {
        System.out.print("Enter an id and points or 'back' to return:");
        while (true) {
            String input = scanner.nextLine().strip().toLowerCase();
            if (input.equals("back")) {
                break;
            }

            String[] parts = input.split(" ");
            if (parts.length == 5) { // Očakáva sa presne 5 hodnôt (id + 4 body)
                try {
                    int studentId = Integer.parseInt(parts[0]);
                    int[] points = new int[4]; // Prvých 4 hodnoty sa berú ako body
                    boolean validPoints = true;
                    for (int i = 1; i < 5; i++) { // Kontroluje sa prvých 4 body
                        points[i - 1] = Integer.parseInt(parts[i]);
                        if (points[i - 1] < 0) { // Body nemôžu byť záporné
                            validPoints = false;
                            break;
                        }
                    }

                    if (validPoints) {
                        if (studentProgress.containsKey(studentId)) {
                            studentPoints.put(studentId, points); // Tu sa body pridávajú (alebo aktualizujú)
                            Student student = studentProgress.get(studentId);
                               studentPoints.put(studentId, points);
                            // Tu môžete aktualizovať body študenta podľa potreby
                            System.out.println("Points added/updated for " + student + ": " + Arrays.toString(points));
                        } else {
                            System.out.printf("No student is found for id=%d.%n", studentId);
                        }
                    } else {
                        System.out.println("Incorrect points format.");
                        System.out.print("Enter an id and points or 'back' to return:");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect points format.");
                    System.out.print("Enter an id and points or 'back' to return:");
                    continue;
                }
            } else {
                System.out.println("Incorrect format. Please enter id and points for 4 categories separated by space.");
                System.out.print("Enter an id and points or 'back' to return:");
                continue;
            }

            System.out.print("Enter an id and points or 'back' to return:");
        }
    }
}
