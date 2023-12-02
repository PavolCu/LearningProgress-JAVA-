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


    public String calculateHardestCourse() {
        // Assuming the hardest course is the one with the lowest average points
        Map<String, Double> courseAveragePoints = calculateAveragePoints();
        return courseAveragePoints.isEmpty() || Collections.min(courseAveragePoints.values()) == 0 ? "n/a" : Collections.min(courseAveragePoints.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public String calculateEasiestCourse() {
        // Assuming the easiest course is the one with the highest average points
        Map<String, Double> courseAveragePoints = calculateAveragePoints();
        return courseAveragePoints.isEmpty() || Collections.max(courseAveragePoints.values()) == 0 ? "n/a" : Collections.max(courseAveragePoints.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public String calculateLowestActivityCourse() {
        Map<String, Integer> coursePoints = calculateCoursePoints();
        if (coursePoints.isEmpty()) {
            return "n/a";
        }
        int minPoints = Collections.min(coursePoints.values());
        List<String> lowestActivityCourses = coursePoints.entrySet().stream()
                .filter(entry -> entry.getValue() == minPoints)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        boolean hasCourseWithMinPoints = lowestActivityCourses.stream()
                .anyMatch(course -> coursePoints.get(course) == minPoints);

        return hasCourseWithMinPoints ? "n/a" : lowestActivityCourses.get(0);
    }
    public String calculateHighestActivityCourse() {
        Map<String, Integer> coursePoints = calculateCoursePoints();
        if (coursePoints.isEmpty()) {
            return "n/a";
        }
        return coursePoints.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(", "));
    }
    public String calculateMostPopularCourse() {
        Map<String, Integer> courseCount = calculateCoursePopularity();

        if (courseCount.isEmpty()){
            return "n/a";
        }
        return courseCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(", "));
    }
    public String calculateLeastPopularCourse() {
        Map<String, Integer> courseCount = calculateCoursePopularity();

        if (courseCount.isEmpty()) {
            return "n/a";
        }
        int minCount = Collections.min(courseCount.values());
        List<String> leastPopularCourses = courseCount.entrySet().stream()
                .filter(entry -> entry.getValue() == minCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        Map<String, Integer> coursePoints = calculateCoursePoints();
        int minPoints = Collections.min(coursePoints.values());
        boolean hasCourseWithMinPoints = leastPopularCourses.stream()
                .anyMatch(course -> coursePoints.get(course) == minPoints);

        if (hasCourseWithMinPoints) {
            return "n/a";
        } else {
            return leastPopularCourses.stream().collect(Collectors.joining(", "));
        }
    }
}
