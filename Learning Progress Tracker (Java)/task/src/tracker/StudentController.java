package tracker;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;



class StudentController {
    private final StudentProgress studentProgress;
    private int totalStudents;

    public StudentController(StudentProgress studentProgress) {
        this.studentProgress = studentProgress;
        this.totalStudents = studentProgress.getStudents().size();// Initialize totalStudents
    }

    public void handleStatisticsCommand(Scanner scanner) {
        System.out.print("Type the name of a course to see details or 'back' to quit");

        while (true) {
            String courseName = scanner.nextLine().strip().toLowerCase();
            if (courseName.equals("back")) {
                return;
            }

            if (isValidCourseName(courseName)) {
                printCourseStatistics(courseName);
            }else  {
                System.out.println("Unknown course");
            }
        }
    }

    private boolean isValidCourseName(String courseName) {
        return courseName.equals("java") || courseName.equals("dsa") || courseName.equals("databases") || courseName.equals("spring");
    }

    private void printCourseStatistics(String courseName) {
        System.out.println(courseName);
        System.out.println("ID | Points | Completion");
        List<Student> students = getStudentsForCourse(courseName);
        students.sort((s1, s2) -> {
            int points1 = getTotalPointsForStudent(s1, courseName);
            int points2 = getTotalPointsForStudent(s2, courseName);
            return Integer.compare(points2, points1);
        }).thenComparing(StudentProgress::getStudent);

        for (Student student : students) {
            int points = getTotalPointsForStudent(student, courseName);
            double completion = getCompletionPercentage(student, courseName);
            System.out.printf("%d | %d | %.1f%%\n", student.getId(), points, completion);
        }
    }

    private List<Student> getStudentForCourse(String courseName) {
        int courseIndex = getCourseIndex(courseName);
        return studentProgress.getStudents().values().stream()
                .filter(student -> studentProgress.getStudentPoints().containsKey(student.getId()))
                .filter(student -> studentProgress.getStudentPoints().get(student.getId())[courseIndex] > 0)
                .collect(Collectors.toList());
    }

    private int getTotalPointsForStudent(Student student, String courseName) {
        int courseIndex = getCourseIndex(courseName);
        return studentProgress.getStudentPoints().get(student.getId())[courseIndex];
    }

    private double getCompletionPercentage(Student student, String courseName) {
        int totalPoints = getTotalPointsForStudent(student,courseName);
        return (double) totalPoints / getMaxPointsForCourse(courseName) * 100; // MAX_POINTS_PER_COURSE is the maximum points a student can get for a course
    }

    private int getCourseIndex(String courseName) {
        switch (courseName) {
            case "java":
                return 0;
            case "dsa":
                return 1;
            case "databases":
                return 2;
            case "spring":
                return 3;
            default:
                throw new  IllegalArgumentException("Invalid coursename: " + courseName);
        }
    }
    private int getMaxPointsForCourse(String courseName) {
        switch (courseName) {
            case "java":
                return 600;
            case "dsa":
                return 400;
            case "databases":
                return 480;
            case "spring":
                return 550;
            default:
                throw new  IllegalArgumentException("Invalid coursename: " + courseName);
        }
    }

    private List<Student> getStudentsForCourse(String courseName) {
        return studentProgress.getStudents().values().stream()
                .filter(student -> studentProgress.getStudentPoints().containsKey(student.getId()))
                .filter(student -> studentProgress.getStudentPoints().get(student.getId())[getCourseIndex(courseName)] > 0)
                .collect(Collectors.toList());
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
