import java.util.*;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int d = scanner.nextInt();
        scanner.nextLine();

        Set<String> knownWords = new HashSet<>();
        for (int i = 0; i < d; i++) {
            knownWords.add(scanner.nextLine().toLowerCase());
        }

        int l = scanner.nextInt();
        scanner.nextLine(); //Consume the newline

        Set<String> wrongWords = new HashSet<>();
        for (int i = 0; i < l; i++) {
            String[] wordsInLine = scanner.nextLine().toLowerCase().split(" ");
            for (String word : wordsInLine) {
                if (!knownWords.contains(word)) {
                    wrongWords.add(word);
                }
            }
        }
        wrongWords.forEach(System.out::println);
    }
}