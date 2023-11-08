import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        // Read the size of the array
        int size = scanner.nextInt();

        // Read elements of the array
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }

        // Initialize a variable to count triples
        int tripleCount = 0;

        // Check for triples in the array
        for (int i = 0; i < size - 2; i++) {
            if (isTriple(array[i], array[i + 1], array[i + 2])) {
                tripleCount++;
            }
        }

        // Output the result
        System.out.println(tripleCount);
    }

    // Helper method to check if three integers form a triple
    private static boolean isTriple(int a, int b, int c) {
        return (b == a + 1 && c == b + 1);
    }
}