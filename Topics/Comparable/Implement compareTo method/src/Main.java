class Person implements Comparable<Person> {
    private String name;
    private int age;
    private int height;
    private int weight;

    public Person(String name, int age, int height, int weight) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWeight() {
        return this.weight;
    }

    @Override
    public int compareTo(Person otherPerson) {
        // Compare by name
        int nameComparison = this.name.compareTo(otherPerson.name);

        // If names are equal, compare by age
        if (nameComparison == 0) {
            return Integer.compare(this.age, otherPerson.age);
        }

        // Otherwise, return the result of name comparison
        return nameComparison;
    }
}