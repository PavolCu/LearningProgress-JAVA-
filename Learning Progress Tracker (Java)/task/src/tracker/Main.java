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

            if (isFirstInputBack && input.equals("back")) {
                isFirstInputBack = false;
                System.out.println("Enter 'exit' to exit the program.");
                continue;
            }

            switch (input) {
                case "back":

                    break;
                case "list":
                    studentController.listStudentsAndPoints();
                    break;
                case "add students":
                    int studentsAdded = studentController.handleAddStudentsCommand(scanner);
                    System.out.println("Total " + studentsAdded + " students have been added.");
                    break;
                case "add points":
                    System.out.print("Enter an id and points or 'back' to return:\n> ");
                    while (true) {

                        String pointsInput = scanner.nextLine().strip();
                        if (pointsInput.equals("back")) {
                            break;
                        }
                        String[] pointsParts = pointsInput.split(" ");
                        if (pointsParts.length != 5) {
                            System.out.println("Incorrect points format.");
                            continue;
                        }

                        try {
                            int id = Integer.parseInt(pointsParts[0]);
                            int[] points = new int[4];
                            for (int i = 0; i < 4; i++) {
                                points[i] = Integer.parseInt(pointsParts[i + 1]);
                            }
                            boolean pointsUpdated = studentController.addPoints(id, points);
                            if (pointsUpdated) {
                                System.out.println("Points updated.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Incorrect points format.");
                        }
                    }
                    continue;
                case "find":
                    System.out.print("Enter an id or 'back' to return:\n> ");
                    String idInput = scanner.nextLine().strip();
                    if (idInput.equals("back")) {
                        continue;
                    }
                    try {
                        int id = Integer.parseInt(idInput);
                        studentController.findStudent(id);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid id.");
                    }
                    break;
                case "exit":
                    System.out.println("Bye!");
                    break label;
                default:
                    System.out.println("Unknown command.");
                    break;
            }
        }
        scanner.close();
    }
}
