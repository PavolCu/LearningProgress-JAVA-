package tracker;

import java.util.regex.Pattern;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class StudentController {
    private final StudentProgress studentProgress;

    public StudentController(StudentProgress studentProgress) {
        this.studentProgress = studentProgress;
    }

    public void displayStudentPoints(int id) {
        Student student = findStudentById(id);
        if (student != null) {
            int[] points = studentProgress.getStudentPoints().get(id);
            System.out.printf("%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d%n", id, points[0], points[1], points[2], points[3]);
        } else {
            System.out.printf("No student is found for id=%d.%n", id);
        }
    }

    public void listStudentsAndPoints() {
        if (studentProgress.getStudents().isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("Students:");
            for (Integer id : studentProgress.getStudents().keySet()) {
                System.out.println(id);
            }
        }
    }

    public boolean isValidName(String name) {
        return Pattern.matches("^[A-Za-z]+([ '-][A-Za-z]+)*$", name) && name.length() >= 2;
    }

    public boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z0-9+_.-]+$";
        return Pattern.matches(emailPattern, email);
    }

    public int handleAddStudentsCommand(Scanner scanner) {
        int addedStudents = 0;
        System.out.print("Enter student credentials or 'back' to return:");

        while (true) {
            String studentInput = scanner.nextLine().strip();

            if (studentInput.equals("back")) {
                return addedStudents;
            }

            String[] parts = studentInput.split(" ");

            if (parts.length < 3) {
                System.out.println("Incorrect credentials. Please enter at least first name, last name, and email separated by spaces.");
                continue;
            }

            String firstName = parts[0].trim();
            String lastName = parts[1].trim();
            String email = parts[parts.length - 1].trim();

            if (!isValidName(firstName)) {
                System.out.println("Incorrect first name.");
            } else if (!isValidName(lastName)) {
                System.out.println("Incorrect last name.");
            } else if (!isValidEmail(email)) {
                System.out.println("Incorrect email.");
            } else {
                boolean emailExists = studentProgress.getStudents().values().stream()
                        .anyMatch(student -> student.getEmail().equalsIgnoreCase(email));

                if (emailExists) {
                    System.out.println("This email is already taken.");
                } else {
                    Student student = new Student(firstName, lastName, email);
                    int studentId = studentProgress.getStudents().size() + 10000;
                    studentProgress.addStudent(studentId, student);
                    System.out.println("The student has been added.");
                    addedStudents++;
                }
            }
        }
    }

    public void handleAddPointsCommand(Scanner scanner) {
        System.out.print("Enter an id and points or 'back' to return:");
        while (true) {

            String input = scanner.nextLine().strip();

            if (input.equalsIgnoreCase("back")) {
                System.out.println("Returning to the main menu.");
                break;
            }

            String[] tokens = input.split("\\s+");
            if (tokens.length != 5) {
                System.out.println("Incorrect points format.");
                continue; // Make sure to continue the loop without updating points
            }

            int id;
            try {
                id = Integer.parseInt(tokens[0]);
            } catch (NumberFormatException e) {
                System.out.println("No student is found for id=" + tokens[0] + ".");
                continue;
            }

            if (!studentProgress.isValidStudentId(id)) {
                System.out.println("No student is found for id=" + id + ".");
                continue;
            }

            int[] points = new int[4];
            boolean isValidPoints = true;
            for (int i = 0; i < 4; i++) {
                try {
                    points[i] = Integer.parseInt(tokens[i + 1]);
                    if (points[i] < 0) {
                        System.out.println("Incorrect points format.");
                        isValidPoints = false;
                        break; // Break the for-loop, not the while-loop
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect points format.");
                    isValidPoints = false;
                    break; // Break the for-loop, not the while-loop
                }
            }

            if (!isValidPoints) {
                continue; // Continue the while-loop without updating points
            }
            if (studentProgress.isValidStudentId(id)) {
                int[] currentPoints = studentProgress.getStudentPoints().get(id);
                for (int i = 0; i < 4; i++) {
                    currentPoints[i] += points[i];
                }
                System.out.print("Points updated.");
            } else {
                System.out.print("No student is found for id=" + id + ".");
            }
        }
    }

    public void handleFindCommand(Scanner scanner) {
        System.out.print("Enter an id or 'back' to return: ");
        while (true) {
            // Read the input (id
            String idInput = scanner.nextLine().strip();

            if (idInput.equalsIgnoreCase("back")) {
                return;
            }

            int id;
            try {
                id = Integer.parseInt(idInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format.");
                continue;
            }

            Student student = findStudentById(id);
            if (student != null) {
                displayStudentPoints(id);
            } else {
                System.out.printf("No student is found for id=%d.%n", id);
            }
        }
    }

        private Student findStudentById ( int id){
            return studentProgress.getStudents().get(id);
        }

    }
