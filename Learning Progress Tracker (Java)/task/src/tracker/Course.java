package tracker;

// The Course class represents a course with a name.
public class Course {

    // The name of the course.
    private final String name;

    // Constructor for the Course class.
    // @param name The name of the course.
    public Course(String name) {

        this.name = name;
    }

    // Getter for the name of the course.
    // @return The name of the course.
    public String getName() {

        return name;
    }
}
