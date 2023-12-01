package tracker;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentProgress studentProgress = new StudentProgress();
        StudentController studentController = new StudentController(studentProgress);

        System.out.print("Learning Progress Tracker > ");
        boolean isFirstInputBack = true;

        label:
        while (true) {
            String input = scanner.nextLine().strip().toLowerCase();

            if (input.isEmpty()) {
                System.out.println("No input.");
                continue;
            }
            if (input.equalsIgnoreCase("back")) {
                System.out.println("Enter 'exit' to exit the program.");
                continue;
            }

            if (isFirstInputBack && input.equalsIgnoreCase("back")) {
                System.out.println("Enter 'exit' to exit the program.");
                continue;
            }

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