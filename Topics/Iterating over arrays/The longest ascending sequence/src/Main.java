import java.util.Scanner;


class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        // Read the size of the array
        int size = scanner.nextInt();
        int[] array = new int[size];

        // Read elements of the array
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }

        // Calculate the length of the longest ascending sequence
        int maxLength = 1;
        int currentLength = 1;

        for (int i = 1; i < size; i++) {
            if (array[i] > array[i - 1]) {
                currentLength++;
                if (currentLength > maxLength) {
                    maxLength = currentLength;
                }
            } else {
                currentLength = 1;
            }
        }

        // Output the result
        System.out.println(maxLength);
    }
}