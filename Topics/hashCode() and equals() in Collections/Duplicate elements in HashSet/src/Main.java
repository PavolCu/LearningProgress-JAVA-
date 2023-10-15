import java.util.Scanner;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Set<Person> set = new HashSet();
        set.add(new Person(scanner.nextLine()));
        set.add(new Person(scanner.nextLine()));

        System.out.println(/* Print the set size here */set.size());
    }
}

class Person {
    String name;

    public Person(String name) {
        this.name = name;
    }

    // Override the equals() method here

}