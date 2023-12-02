package tracker;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


// The StudentController class handles the operations related to students.
class StudentController {
    // The student progress data.
    private final StudentProgress studentProgress;
    // The total number of students.
    private final int totalStudents;

    // Gets the course name based on the index.
    // @param index The index of the course.
    // @return The name of the course.
    public String getCourseName(int index) {
        switch (index) {
            case 0:
                return "Java";
            case 1:
                return "DSA";
            case 2:
                return "Databases";
            case 3:
                return "Spring";
            default:
                throw new IllegalArgumentException("Invalid index: " + index);
        }
    }

    // Handles the notify command.
    public void handleNotifyCommand() {
        List<String> messages = new ArrayList<>();
        int notifiedStudents = 0;

        for (Map.Entry<Integer, Student> entry : studentProgress.getStudents().entrySet()) {
            Student student = entry.getValue();
            List<Course> completedCourses = studentProgress.getCompletedCourses(student);
            if (!completedCourses.isEmpty()) {
                boolean isNewlyNotified = false;
                for (Course course : completedCourses) {
                    if (!student.getNotifiedCourses().contains(course)) {
                        String message = String.format("To: %s\nRe: Your Learning Progress\nHello, %s! You have accomplished our %s course!",
                                student.getEmail(), student.getFullName(), course.getName());
                        messages.add(message);
                        student.addNotifiedCourse(course);
                        isNewlyNotified = true;
                    }
                }
                if (isNewlyNotified) {
                    notifiedStudents++;
                }
            }
        }

        for (String message : messages) {
            System.out.println(message);
        }
        System.out.println("Total " + notifiedStudents + " students have been notified.");
    }

    // Constructor for the StudentController class.
    // @param studentProgress The student progress data.
    public StudentController(StudentProgress studentProgress) {
        this.studentProgress = studentProgress;
        this.totalStudents = studentProgress.getStudents().size();// Initialize totalStudents
    }


    // Handles the statistics command.
    // @param scanner The scanner to read user input.
    public void handleStatisticsCommand(Scanner scanner) {
        CourseStatistics courseStatistics = new CourseStatistics(studentProgress);
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        //Calculate statistics for each course
        String mostPopular = courseStatistics.calculateMostPopularCourse();
        String leastPopular = courseStatistics.calculateLeastPopularCourse();
        String highestActivity = courseStatistics.calculateHighestActivityCourse();
        String lowestActivity = courseStatistics.calculateLowestActivityCourse();
        String easiestCourse = courseStatistics.calculateEasiestCourse();
        String hardestCourse = courseStatistics.calculateHardestCourse();

        //print statistics
        System.out.println("Most popular: " + (mostPopular != null ? mostPopular : "n/a"));
        System.out.println("Least popular: " + (leastPopular != null ? leastPopular : "n/a"));
        System.out.println("Highest activity: " + (highestActivity != null ? highestActivity : "n/a"));
        System.out.println("Lowest activity: " + (lowestActivity != null ? lowestActivity : "n/a"));
        System.out.println("Easiest course: " + (easiestCourse != null ? easiestCourse : "n/a"));
        System.out.println("Hardest course: " + (hardestCourse != null ? hardestCourse : "n/a"));



        while (true) {
            String courseName = scanner.nextLine().strip().toLowerCase();
            if (courseName.equals("back")) {
                return;
            }

            if (isValidCourseName(courseName)) {
                printCourseStatistics(courseName);
            }else  {
                System.out.println("Unknown course.");
            }
        }
    }

    // Checks if the course name is valid.
    // @param courseName The name of the course.
    // @return True if the course name is valid, false otherwise.
    private boolean isValidCourseName(String courseName) {
        return courseName.equals("java") || courseName.equals("dsa") || courseName.equals("databases") || courseName.equals("spring");
    }

    // Prints the statistics for a course.
    // @param courseName The name of the course.
    private void printCourseStatistics(String courseName) {
        System.out.println(courseName.toUpperCase());
        System.out.println("id   points   completed");
        List<Student> students = getStudentsForCourse(courseName);
        students.sort(Comparator.comparingDouble((Student s) ->getTotalPointsForStudent(s, courseName)).reversed()
                .thenComparing(Student::getId));

        for (Student student : students) {
            int points = getTotalPointsForStudent(student, courseName);
            double completion = getCompletionPercentage(student, courseName);
            System.out.printf("%d   %d   %.1f%%\n", student.getId(), points, completion);
        }
    }

    // Handles the find command.
    // @param scanner The scanner to read user input.
    public void handleFindCommand(Scanner scanner) {
        while (true) {
            String findInput = scanner.nextLine().strip();
            if ("back".equalsIgnoreCase(findInput)) {
                return;
            }
            try {
                int id = Integer.parseInt(findInput);
                findStudent(id); // Call the findStudent method with id as an argument
            } catch (NumberFormatException e) {
                System.out.println("Unknown command.");
            }
        }
    }

    // Gets the total points for a student for a course.
    // @param student The student.
    // @param courseName The name of the course.
    // @return The total points for the student for the course.
    private int getTotalPointsForStudent(Student student, String courseName) {
        int courseIndex = getCourseIndex(courseName);
        return studentProgress.getStudentPoints().get(student.getId())[courseIndex];
    }

    // Gets the completion percentage for a student for a course.
    // @param student The student.
    // @param courseName The name of the course.
    // @return The completion percentage for the student for the course.
    private double getCompletionPercentage(Student student, String courseName) {
        double totalPoints = getTotalPointsForStudent(student,courseName);
        double completion = totalPoints / getMaxPointsForCourse(courseName) * 100;

        BigDecimal bd = new BigDecimal(Double.toString(completion));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    // Gets the index of a course.
    // @param courseName The name of the course.
    // @return The index of the course.
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

    // Gets the maximum points for a course.
    // @param courseName The name of the course.
    // @return The maximum points for the course.
    private int getMaxPointsForCourse(String courseName) {
        switch (courseName.toLowerCase()) {
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

    // Gets the students for a course.
    // @param courseName The name of the course.
    // @return A list of students for the course.
    private List<Student> getStudentsForCourse(String courseName) {
        return studentProgress.getStudents().values().stream()
                .filter(student -> studentProgress.getStudentPoints().containsKey(student.getId()))
                .filter(student -> studentProgress.getStudentPoints().get(student.getId())[getCourseIndex(courseName)] > 0)
                .collect(Collectors.toList());
    }

    // Lists the students and their points.
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

    // Checks if a name is valid.
    // @param name The name to check.
    // @return True if the name is valid, false otherwise.
    public boolean isValidName(String name) {
        return Pattern.matches("^[A-Za-z]+([ '-][A-Za-z]+)*$", name) && name.length() >= 2;
    }

    // Checks if an email is valid.
    // @param email The email to check.
    // @return True if the email is valid, false otherwise.
    public boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z0-9+_.-]+$";
        return Pattern.matches(emailPattern, email);
    }

    // Handles the add students command.
    // @param scanner The scanner to read user input.
    // @return The number of students added.
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
                    int id = studentProgress.getStudents().size() + 10000; // Generate an id for the new student
                    Student student = new Student(id, firstName, lastName, email);
                    studentProgress.addStudent(id, student);
                    System.out.println("The student has been added.");
                    addedStudents++;
                }
            }
        }
    }

    // Handles the add points command.
    // @param scanner The scanner to read user input.
    public void handleAddPointsCommand(Scanner scanner) {
        System.out.print("Enter an id and points or 'back' to return:\n> ");
        while (true) {
            String input = scanner.nextLine().strip().toLowerCase();
            if (input.equals("back")) {
                return;
            }
            String[] inputParts = input.split(" ");
            if (inputParts.length != 5) {
                System.out.println("Incorrect points format.");
                continue;
            }
            try {
                int id = Integer.parseInt(inputParts[0]);
                Student student = studentProgress.getStudent(id);
                if (student == null) {
                    System.out.println("No student is found for id=" + inputParts[0] + ".");
                    continue;
                }
                int[] points = new int[4];
                try {
                    for (int i = 0; i < 4; i++) {
                        points[i] = Integer.parseInt(inputParts[i + 1]);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect points format.");
                    continue;
                }
                boolean pointsUpdated = addPoints(id, points);
                if (pointsUpdated) {
                    System.out.println("Points updated.");
                }
            } catch (NumberFormatException e) {
                System.out.println("No student is found for id=" + inputParts[0] + ".");
            }
        }
    }

    // Adds points to a student.
    // @param id The id of the student.
    // @param points The points to add.
    // @return True if the points were added, false otherwise.
    public boolean addPoints(int id, int[] points) {
        Student student = studentProgress.getStudent(id);
        if (student == null) {
            System.out.printf("No student is found for id=%d.%n", id);
            return false;
        }

        // Verify if the points array is valid (not negative)
        boolean validPoints = Arrays.stream(points).allMatch(point -> point >= 0);

        if (validPoints && points.length == 4) {
            int[] existingPoints = studentProgress.getPoints(id); // Use the new getPoints method

            // Add the new points to the existing points
            for (int i = 0; i < 4; i++) {
                existingPoints[i] += points[i];
                //Check if the student has completed the course
                if (existingPoints[i] >= getMaxPointsForCourse(getCourseName(i))) {
                    student.addCompletedCourse(new Course(getCourseName(i)));
                }
            }

            // Update the studentPoints map with the new total points
            studentProgress.addPoints(id, existingPoints);

            return true;
        } else {
            System.out.println("Incorrect points format.");
            return false;
        }
    }

    // Finds a student by id.
    // @param id The id of the student.
    public void findStudent(int id) {
        Student student = studentProgress.getStudent(id);
        if (student != null) {
            int[] points = studentProgress.getStudentPoints().getOrDefault(id, new int[4]);
            System.out.printf("%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d%n",
                    id, points[0], points[1], points[2], points[3]);
        } else {
            System.out.printf("No student is found for id=%d.%n", id);
        }
    }

    // Gets the total number of students.
    // @return The total number of students.
    public int getTotalStudents() {

        return totalStudents;
    }
}
