

class Person {
    // Do not change these fields
    public static final String DEFAULT_NAME = "Unknown";
    public static final int MAX_AGE = 130;
    public static final int MIN_AGE = 0;
    private String name;
    private int age;

    // Fix the constructor to make its code pass the unit tests
    Person(String name, int age) {
        if (name == null || name.trim().isEmpty()) {
            this.name = DEFAULT_NAME;
        } else {
            this.name = name;
        }

        if (age < MIN_AGE) {
            this.age = MIN_AGE;
        } else if (age > MAX_AGE) {
            this.age = MAX_AGE;
        } else {
            this.age = age;
        }
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}