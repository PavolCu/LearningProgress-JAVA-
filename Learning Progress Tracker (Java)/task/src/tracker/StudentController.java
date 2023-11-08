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
        Student student = studentProgress.getStudent(id);
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
    public Student getStudent(int id) {

        return studentProgress.getStudent(id);
    }

    public void handleAddPointsCommand(int id, int[] points) {
        if (!isValidPoints(points) || points.length != 4) {
            System.out.print("Incorrect points format.");
        } else {
            if (studentProgress.isValidStudentId(id)) {
                int[] currentPoints = studentProgress.getStudentPoints().get(id);
                for (int i = 0; i < 4; i++) {
                    currentPoints[i] += points[i];
                }
                System.out.println("Points updated.");
            } else {
                System.out.println("No student is found for id=" + id + ".");
            }
        }
    }


    private boolean isValidPoints(int[] points) {
        for (int point : points) {
            if (point < 0) {
                return false;
            }
        }
        return true;
    }

    public void handleFindCommand(Scanner scanner) {
        System.out.print("Enter an id or 'back' to return:> ");
        String idInput = scanner.nextLine().strip();

        if (idInput.equalsIgnoreCase("back")) {
            return; // Exit the method if 'back' is entered
        }

        int id;

        // Check if the entered input is a valid integer
        try {
            id = Integer.parseInt(idInput);
        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid integer
            System.out.printf("No student is found for id=%s.%n", idInput);
            return; // Exit the method
        }

        Student student = studentProgress.getStudent(id);

        if (student != null) {
            displayStudentPoints(id);
        } else {
            System.out.printf("No student is found for id=%d.%n", id);
        }
    }

}