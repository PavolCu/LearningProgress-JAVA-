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
        studentPoints.put(id, new int[]{0, 0, 0, 0});
    }

    public boolean addPoints(int id, int[] points) {
        int[] currentPoints = studentPoints.getOrDefault(id, new int[]{0, 0, 0, 0});
        for (int i = 0; i < 4; i++) {
            currentPoints[i] += points[i];
        }
        studentPoints.put(id, currentPoints);
        return true;
    }

    public boolean isValidStudentId(int id) {
        return students.containsKey(id);
    }

    public boolean containsStudent(int id) {
        return students.containsKey(id);
    }
}