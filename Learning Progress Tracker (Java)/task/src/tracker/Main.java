package tracker;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("Learning Progress Tracker > ");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine().strip().toLowerCase();  // Use strip() to remove leading and trailing whitespace
            if (input.isBlank() || input.matches("\\s+")) {  // Check if the input is blank or contains only whitespace characters
                System.out.println("No input.");
            } else if (input.equals("exit")) {
                System.out.println("Bye!");
                break;
            } else {
                System.out.println("Unknown command!");
            }
        }
    }
}
