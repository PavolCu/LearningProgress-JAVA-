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
                    studentController.handleAddPointsCommand(scanner);
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