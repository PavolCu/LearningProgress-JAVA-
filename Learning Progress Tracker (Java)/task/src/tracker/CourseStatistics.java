package tracker;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

// The CourseStatistics class provides various statistics related to courses.
public class CourseStatistics {
    // The student progress data.
    private final StudentProgress studentProgress;

    // Constructor for the CourseStatistics class.
    // @param studentProgress The student progress data.
    public CourseStatistics(StudentProgress studentProgress) {

        this.studentProgress = studentProgress;
    }

    // Calculates the popularity of each course.
    // @return A map with course names as keys and their popularity as values.
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

    // Calculates the total points of each course.
    // @return A map with course names as keys and their total points as values.
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

    // Calculates the average points of each course.
    // @return A map with course names as keys and their average points as values.
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

    // Gets the course name based on the index.
    // @param index The index of the course.
    // @return The name of the course.
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


    // Calculates the hardest course based on the average points.
    // @return The name of the hardest course.
    public String calculateHardestCourse() {
        // Assuming the hardest course is the one with the lowest average points
        Map<String, Double> courseAveragePoints = calculateAveragePoints();
        return courseAveragePoints.isEmpty() || Collections.min(courseAveragePoints.values()) == 0 ? "n/a" : Collections.min(courseAveragePoints.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    // Calculates the easiest course based on the average points.
    // @return The name of the easiest course.
    public String calculateEasiestCourse() {
        // Assuming the easiest course is the one with the highest average points
        Map<String, Double> courseAveragePoints = calculateAveragePoints();
        return courseAveragePoints.isEmpty() || Collections.max(courseAveragePoints.values()) == 0 ? "n/a" : Collections.max(courseAveragePoints.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    // Calculates the course with the lowest activity based on the total points.
    // @return The name of the course with the lowest activity.
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

    // Calculates the course with the highest activity based on the total points.
    // @return The name of the course with the highest activity.
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

    // Calculates the most popular course based on the popularity.
    // @return The name of the most popular course.
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

    // Calculates the least popular course based on the popularity.
    // @return The name of the least popular course.
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
