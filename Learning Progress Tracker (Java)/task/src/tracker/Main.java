package tracker;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Inicializace scanneru pro vstup od uživatele
        Scanner scanner = new Scanner(System.in);
        System.out.print("Learning Progress Tracker > ");

        // Vytvoření instance StudentProgress a StudentController
        StudentProgress studentProgress = new StudentProgress();
        StudentController studentController = new StudentController(studentProgress);

        // Proměnná pro kontrolu, zda je první vstup "back"
        boolean isFirstInputBack = true;

        // Hlavní smyčka programu
        label:
        while (true) {

            String mainInput = scanner.nextLine().strip().toLowerCase();

            // Ověření, zda je vstup prázdný
            if (mainInput.isEmpty()) {
                System.out.print("No input.");
                continue;
            }

            // Pokud je první vstup "back", vypis zprávu
            if (isFirstInputBack && mainInput.equals("back")) {
                System.out.print("Enter 'exit' to exit the program.");
                isFirstInputBack = false;
                continue;
            }

            // Rozpoznání a zpracování příkazů od uživatele
            switch (mainInput) {
                case "back":
                    isFirstInputBack = true;
                    break;
                case "list":
                    studentController.listStudentsAndPoints();
                    break;
                case "add students":
                    // Zpracování příkazu pro přidání studentů
                    int studentsAdded = studentController.handleAddStudentsCommand(scanner);
                    System.out.print("Total " + studentsAdded + " students have been added.");
                    break;
                case "add points":
                    System.out.print("Enter an id and points or 'back' to return:");
                    while (true) {
                        String addPointsInput = scanner.nextLine().strip().toLowerCase();

                        if (addPointsInput.equalsIgnoreCase("back")) {
                            System.out.println("Returning to the main menu.");
                            break; // Exit the loop and return to the main menu
                        }

                        String[] tokens = addPointsInput.split("\\s+");
                        if (tokens.length != 5) {
                            System.out.println("Incorrect points format.");
                            continue; // Continue to the next iteration to input again
                        }

                        try {
                            int studentId = Integer.parseInt(tokens[0]);
                            int[] points = new int[4];

                            for (int i = 0; i < 4; i++) {
                                points[i] = Integer.parseInt(tokens[i + 1]);
                                if (points[i] < 0) {
                                    System.out.println("Incorrect points format.");
                                    break; // Exit the loop when points are invalid
                                }
                            }

                            if (points[0] >= 0 && points[1] >= 0 && points[2] >= 0 && points[3] >= 0) {
                                studentController.handleAddPointsCommand(studentId, points);
                                // Print "Points updated." here if needed
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter valid ID and points.");
                        }
                    }
                    break; // Exit the "add points" case

                case "find":
                    studentController.handleFindCommand(scanner);
                    break ; // Exit the "find" case

                case "exit":
                    // Ukončení programu
                    System.out.println("Bye!");
                    break label;
                default:
                    // Zpráva pro neznámý příkaz
                    System.out.print("Unknown command.");
                    break;
            }
        }

        // Uzavření scanneru
        scanner.close();
    }
}