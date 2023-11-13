package tracker;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

class StudentController {
    private final StudentProgress studentProgress;
    private int totalStudents;

    public StudentController(StudentProgress studentProgress) {
        this.studentProgress = studentProgress;
        this.totalStudents = studentProgress.getStudents().size();// Initialize totalStudents
    }

    public void listStudentsAndPoints() {
        for (Integer id : studentProgress.getStudents().keySet()) {
            Student student = studentProgress.getStudent(id);
            System.out.println("Students:");
            System.out.print(id + ". " + student.getFirstName() + " " + student.getLastName() +
                    ": (No points available)");
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
        System.out.println("Enter student credentials or 'back' to return:");
        int addedStudents = 0;

        while (true) {
            String studentInput = scanner.nextLine().strip().toLowerCase();


            if (studentInput.equals("back")) {
                return addedStudents;
            }

            String[] parts = studentInput.split(" ");
            if (parts.length < 3) {
                System.out.println("Incorrect credentials.");
                continue;
            }

            String firstName = parts[0];
            String lastName = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length - 1));
            String email = parts[parts.length - 1];

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
                    int studentId = studentProgress.getStudents().size() + 10000; // Start id from 10000
                    studentProgress.addStudent(studentId, student);
                    System.out.println("The student has been added.");
                    addedStudents++;
                }
            }
        }
    }

    public void handleAddPointsCommand(Scanner scanner) {
        System.out.print("Enter an id and points or 'back' to return:\n> ");
        String input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("back")) {
            return;
        }

        String[] inputParts = input.split(" ");
        if (inputParts.length != 5) {
            System.out.println("Incorrect points format.");
            return;
        }

        try {
            int id = Integer.parseInt(inputParts[0]);
            int[] points = new int[4];
            boolean validPoints = true;

            for (int i = 0; i < 4; i++) {
                try {
                    points[i] = Integer.parseInt(inputParts[i + 1]);
                    if (points[i] < 0) {
                        validPoints = false;
                        break;
                    }
                } catch (NumberFormatException e) {
                    validPoints = false;
                    break;
                }
            }

            if (validPoints) {
                boolean pointsUpdated = addPoints(id, points);
                if (pointsUpdated) {
                    System.out.println("Points updated.");
                }
            } else {
                System.out.println("Incorrect points format.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numbers for id and points.");
        }
    }

    public boolean addPoints(int id, int[] points) {
        Student student = studentProgress.getStudent(id);
        if (student == null) {
            System.out.printf("No student is found for id=%d.%n", id);
            return false;
        }

        // Overiť, či je pole bodov platné (nie je záporné)
        boolean validPoints = Arrays.stream(points).allMatch(point -> point >= 0);

        if (validPoints && points.length == 4) {
            int[] existingPoints = studentProgress.getStudentPoints().getOrDefault(id, new int[4]);

            // Pripočítať nové body k existujúcim bodom
            for (int i = 0; i < 4; i++) {
                existingPoints[i] += points[i];
            }

            studentProgress.addPoints(id, existingPoints);
            return true;
        } else {
            System.out.println("Incorrect points format.");
            return false;
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
    public void findStudent(int id) {
        Student student = studentProgress.getStudent(id);
        if (student != null) {
            int[] points = studentProgress.getStudentPoints().get(id);
            System.out.println(id + " points: Java=" + points[0] + "; DSA=" + points[1] + "; Databases=" + points[2] + "; Spring=" + points[3]);
        } else {
            System.out.println("No student is found for id=" + id + ".");
        }
    }
}
