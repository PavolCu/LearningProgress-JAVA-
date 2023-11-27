package tracker;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CourseStatistics {
    private final StudentProgress studentProgress;

    public CourseStatistics(StudentProgress studentProgress) {

        this.studentProgress = studentProgress;
    }

    private Map<String, Integer> calculateCoursePopularity() {
        Map<String, Integer> courseCount = new HashMap<>();
        for (int[] points : studentProgress.getStudentPoints().values()) {
            for (int i = 0; i < points.length; i++) {
                if (points[i] > 0) {
                    String courseName = getCourseName(i);
                    courseCount.put(courseName, courseCount.getOrDefault(courseName, 0) + 1);
                }
            }
        }
        return courseCount;
    }
    private Map<String, Integer> calculateCoursePoints() {
        Map<String, Integer> coursePoints = new HashMap<>();
        for (int[] points : studentProgress.getStudentPoints().values()) {
            for (int i = 0; i < points.length; i++) {
                String courseName = getCourseName(i);
                coursePoints.put(courseName, coursePoints.getOrDefault(courseName, 0) + points[i]);
            }
        }
        return coursePoints;
    }
    private Map<String, Double> calculateAveragePoints() {
        Map<String, Double> courseAveragePoints = new HashMap<>();
        for (int[] points : studentProgress.getStudentPoints().values()) {
            for (int i = 0; i < points.length; i++) {
                String courseName = getCourseName(i);
                courseAveragePoints.put(courseName, courseAveragePoints.getOrDefault(courseName, 0.0) + points[i]);
            }
        }
        courseAveragePoints.replaceAll((courseName, totalPoints) -> totalPoints / studentProgress.getStudents().size());
        return courseAveragePoints;
    }

    private String getCourseName(int index) {
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
                throw new IllegalArgumentException("Invalid index: " + index);
        }
    }


    public String calculateHardestCourse() {
        // Assuming the hardest course is the one with the lowest average points
        Map<String, Double> courseAveragePoints = calculateAveragePoints();
        return courseAveragePoints.isEmpty() ? "n/a" : Collections.min(courseAveragePoints.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public String calculateEasiestCourse() {
        // Assuming the easiest course is the one with the highest average points
        Map<String, Double> courseAveragePoints = calculateAveragePoints();
        return courseAveragePoints.isEmpty() ? "n/a" : Collections.max(courseAveragePoints.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public String calculateLowestActivityCourse() {
        // Assuming the course with the lowest activity is the one with the least points
        Map<String, Integer> coursePoints = calculateCoursePoints();
        return coursePoints.isEmpty() ? "n/a" : Collections.min(coursePoints.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public String calculateHighestActivityCourse() {
        // Assuming the course with the highest activity is the one with the most points
        Map<String, Integer> coursePoints = calculateCoursePoints();
        return coursePoints.isEmpty() ? "n/a" : Collections.max(coursePoints.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public String calculateMostPopularCourse() {
        Map<String, Integer> courseCount = calculateCoursePopularity();

        if (courseCount.isEmpty()){
            return "n/a";
        }
        int maxCount = Collections.max(courseCount.values());
        return courseCount.entrySet().stream()
                .filter(entry -> entry.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(", "));
    }
    public String calculateLeastPopularCourse() {
        int[] coursePopularity = new int[4];
        for (int[] points : studentProgress.getStudentPoints().values()) {
            for (int i = 0; i < 4; i++) {
                if (points[i] > 0) {
                    coursePopularity[i]++;
                }
            }
        }
        int minIndex = 0;
        for (int i = 1; i < 4; i++) {
            if (coursePopularity[i] < coursePopularity[minIndex]) {
                minIndex = i;
            }
        }
        return getCourseName(minIndex);
    }
}
