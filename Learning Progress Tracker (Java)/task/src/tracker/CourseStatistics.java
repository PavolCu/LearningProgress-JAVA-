package tracker;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class CourseStatistics {
    private final StudentProgress studentProgress;

    public CourseStatistics(StudentProgress studentProgress) {
        this.studentProgress = studentProgress;
    }

    public String calculateMostPopularCourse() {
        Map<String, Integer> courseCount = new HashMap<>();
        for (int[] points : studentProgress.getStudentPoints().values()) {
            for (int i = 0; i < points.length; i++) {
                if (points[i] > 0) {
                    String courseName = getCourseIndex(i);
                    courseCount.put(courseName, courseCount.getOrDefault(courseName, 0) + 1);
                }
            }
        }
        return courseCount.isEmpty() ? "n/a" : Collections.max(courseCount.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public String getCourseIndex(int index) {
        switch (index) {
            case 0:
                return "java";
            case 1:
                return "dsa";
            case 2:
                return "databases";
            case 3:
                return "spring";
            default:
                throw new IllegalArgumentException("Invalid course index: " + index);
        }
    }

    public String calculateHardestCourse() {
        // Assuming the hardest course is the one with the lowest average points
        Map<String, Double> courseAveragePoints = new HashMap<>();
        for (int[] points : studentProgress.getStudentPoints().values()) {
            for (int i = 0; i < points.length; i++) {
                String courseName = getCourseIndex(i);
                courseAveragePoints.put(courseName, courseAveragePoints.getOrDefault(courseName, 0.0) + points[i]);
            }
        }
        for (String courseName : courseAveragePoints.keySet()) {
            courseAveragePoints.put(courseName, courseAveragePoints.get(courseName) / studentProgress.getStudents().size());
        }
        return Collections.min(courseAveragePoints.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public String calculateEasiestCourse() {
        // Assuming the easiest course is the one with the highest average points
        Map<String, Double> courseAveragePoints = new HashMap<>();
        for (int[] points : studentProgress.getStudentPoints().values()) {
            for (int i = 0; i < points.length; i++) {
                String courseName = getCourseIndex(i);
                courseAveragePoints.put(courseName, courseAveragePoints.getOrDefault(courseName, 0.0) + points[i]);
            }
        }
        for (String courseName : courseAveragePoints.keySet()) {
            courseAveragePoints.put(courseName, courseAveragePoints.get(courseName) / studentProgress.getStudents().size());
        }
        return Collections.max(courseAveragePoints.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public String calculateLowestActivityCourse() {
        // Assuming the course with the lowest activity is the one with the least points
        Map<String, Integer> coursePoints = new HashMap<>();
        for (int[] points : studentProgress.getStudentPoints().values()) {
            for (int i = 0; i < points.length; i++) {
                String courseName = getCourseIndex(i);
                coursePoints.put(courseName, coursePoints.getOrDefault(courseName, 0) + points[i]);
            }
        }
        return Collections.min(coursePoints.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public String calculateHighestActivityCourse() {
        // Assuming the course with the highest activity is the one with the most points
        Map<String, Integer> coursePoints = new HashMap<>();
        for (int[] points : studentProgress.getStudentPoints().values()) {
            for (int i = 0; i < points.length; i++) {
                String courseName = getCourseIndex(i);
                coursePoints.put(courseName, coursePoints.getOrDefault(courseName, 0) + points[i]);
            }
        }
        return Collections.max(coursePoints.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
    public String calculateLeastPopularCourse() {
        Map<String, Integer> courseCount = new HashMap<>();
        for (int[] points : studentProgress.getStudentPoints().values()) {
            for (int i = 0; i < points.length; i++) {
                if (points[i] > 0) {
                    String courseName = getCourseIndex(i);
                    courseCount.put(courseName, courseCount.getOrDefault(courseName, 0) + 1);
                }
            }
        }
        return Collections.min(courseCount.entrySet(), Map.Entry.comparingByValue()).getKey();
    }






}
