package tracker;

import java.util.HashMap;
import java.util.Map;


public class StudentProgress {
    private final Map<Integer, Student> students = new HashMap<>();
    private final Map<Integer, int[]> studentPoints = new HashMap<>();

    public Map<Integer, Student> getStudents() {
        return students;
    }

    public Map<Integer, int[]> getStudentPoints() {
        return studentPoints;
    }

    public Student getStudent(int id) {
        return students.get(id);
    }

    public void addStudent(int id, Student student) {
        students.put(id, student);
    }

    public void addPoints(int id, int[] points) {
        studentPoints.put(id, points);
    }

}