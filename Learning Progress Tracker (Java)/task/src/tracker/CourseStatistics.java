package tracker;
import java.util.*;

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
        return Collections.max(courseCount.entrySet(), Map.Entry.comparingByValue()).getKey();
    }




}
