package tracker;


import java.util.ArrayList;
import java.util.List;

// The Student class represents a student with an id, first name, last name, email, and lists of completed and notified courses.
public class Student {
    // The id of the student.
    private final int id;
    // The first name of the student.
    private final String firstName;
    // The last name of the student.
    private final String lastName;
    // The email of the student.
    private final String email;
    // The list of courses that the student has completed.
    private final List<Course> completedCourses;
    // The list of courses that the student has been notified about.
    private final List<Course> notifiedCourses;


    // Adds a completed course to the student's list of completed courses.
    // @param course The completed course.
    public void addCompletedCourse(Course course) {

        this.completedCourses.add(course);
    }

    // Gets the list of completed courses.
    // @return The list of completed courses.
    public List<Course> getCompletedCourses() {

        return completedCourses;
    }

    // Gets the full name of the student.
    // @return The full name of the student.
    public String getFullName() {

        return firstName + " " + lastName;
    }

    // Constructor for the Student class.
    // @param id The id of the student.
    // @param firstName The first name of the student.
    // @param lastName The last name of the student.
    // @param email The email of the student.
    public Student(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.completedCourses = new ArrayList<>();
        this.notifiedCourses = new ArrayList<>();

    }

    // Gets the list of notified courses.
    // @return The list of notified courses.
    public List<Course> getNotifiedCourses() { // This method now matches the field name

        return notifiedCourses;
    }

    // Adds a notified course to the student's list of notified courses.
    // @param course The notified course.
    public void addNotifiedCourse(Course course) {

        this.notifiedCourses.add(course);
    }

    // Gets the id of the student.
    // @return The id of the student.
    public int getId() {

        return id;
    }

    // Gets the first name of the student.
    // @return The first name of the student.
    public String getFirstName() {

        return firstName;
    }

    // Gets the last name of the student.
    // @return The last name of the student.
    public String getLastName() {

        return lastName;
    }

    // Gets the email of the student.
    // @return The email of the student.
    public String getEmail() {

        return email;
    }
}
