package tracker;


import java.util.ArrayList;
import java.util.List;

public class Student {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final List<Course> completedCourses;
    private final List<Course> notifiedCourses;


    public void addCompletedCourse(Course course) {
       this.completedCourses.add(course);
    }
    public List<Course> getCompletedCourses() {
        return completedCourses;
    }
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Student(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.completedCourses = new ArrayList<>();
        this.notifiedCourses = new ArrayList<>();

    }

    public List<Course> getNotifiedCourses() { // This method now matches the field name
        return notifiedCourses;
    }


    public void addNotifiedCourse(Course course) {
        this.notifiedCourses.add(course);
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public String getEmail() {

        return email;
    }
}
