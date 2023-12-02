package tracker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// The StudentProgress class manages the progress of students in various courses.
public class StudentProgress {

    // A map that stores students with their IDs as keys.
    private final Map<Integer, Student> students = new HashMap<>();
    // A map that stores the points of students with their IDs as keys.
    private final Map<Integer, int[]> studentPoints = new HashMap<>();

    // Gets the map of students.
    // @return The map of students.
    public Map<Integer, Student> getStudents() {

        return students;
    }

    // Gets the map of student points.
    // @return The map of student points.
    public Map<Integer, int[]> getStudentPoints() {

        return studentPoints;
    }

    // Gets the list of completed courses for a student.
    // @param student The student.
    // @return The list of completed courses for the student.
    public List<Course>getCompletedCourses(Student student) {
        return student.getCompletedCourses();
    }

    // Gets a student by their ID.
    // @param id The ID of the student.
    // @return The student with the given ID.
    public Student getStudent(int id) {

        return students.get(id);
    }

    // Adds a student to the map of students.
    // @param id The ID of the student.
    // @param student The student to add.
    public void addStudent(int id, Student student) {

        students.put(id, student);
    }

    // Adds points to a student.
    // @param id The ID of the student.
    // @param points The points to add.
    public void addPoints(int id, int[] points) {

        studentPoints.put(id, points);
    }

    // Gets the points of a student.
    // @param id The ID of the student.
    // @return The points of the student.
    public int[] getPoints(int id) {

        return studentPoints.getOrDefault(id, new int[4]);
    }

}