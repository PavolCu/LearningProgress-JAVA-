package tracker;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create a new Scanner object for user input
        Scanner scanner = new Scanner(System.in);
        // Create a new StudentProgress object
        StudentProgress studentProgress = new StudentProgress();
        // Create a new StudentController object
        StudentController studentController = new StudentController(studentProgress);

        // Print a prompt to the console
        System.out.print("Learning Progress Tracker > ");
        // Boolean flag to check if the first input is 'back'
        boolean isFirstInputBack = true;

        // Loop until the user enters 'exit', start of the main program loop
        label:
        while (true) {
            // Read the user input, remove leading and trailing whitespace, and convert to lowercase
            String input = scanner.nextLine().strip().toLowerCase();

            // If the input is empty, print "No input." and continue to the next iteration of the loop
            if (input.isEmpty()) {
                System.out.println("No input.");
                continue;
            }
            //
            if (input.equalsIgnoreCase("back")) {
                System.out.println("Enter 'exit' to exit the program.");
                continue;
            }

            if (isFirstInputBack && input.equalsIgnoreCase("back")) {
                System.out.println("Enter 'exit' to exit the program.");
                continue;
            }

            // Switch statement to handle different commands
            switch (input.toLowerCase()) {
                case "back":
                    continue;
                case "list":
                    studentController.listStudentsAndPoints();
                    break;
                case "add students":
                    int studentsAdded = studentController.handleAddStudentsCommand(scanner);
                    System.out.println("Total " + studentsAdded + " students have been added.");
                    break;
                case "add points":
                    studentController.handleAddPointsCommand(scanner);
                    break;
                case "find":
                    System.out.print("Enter an id or 'back' to return: ");
                    studentController.handleFindCommand(scanner);
                    break;
                case "exit":
                    System.out.println("Bye!");
                    break label;
                case "statistics":
                    studentController.handleStatisticsCommand(scanner
                    );
                    break;
                case "notify":
                    studentController.handleNotifyCommand();
                    break;
                default:
                    System.out.println("Unknown command.");
                    break;
            }
        }
        scanner.close();
    }
}