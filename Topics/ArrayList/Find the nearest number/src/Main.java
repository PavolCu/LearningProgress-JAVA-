import java.util.*;


public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        List<Integer> numbers = new ArrayList<>();
        String[] inputs = scanner.nextLine().split(" ");
        for (String input : inputs) {
            numbers.add(Integer.parseInt(input));
        }
        int n = scanner.nextInt();

        Collections.sort(numbers);

        TreeMap<Integer, List<Integer>> map = new TreeMap<>();
        for (int number: numbers) {
            int diff = Math.abs(n-number);
            if (!map.containsKey(diff)) {
                map.put(diff, new ArrayList<>());
            }
            map.get(diff).add(number);
        }
        List<Integer> closestNumbers = map.firstEntry().getValue();
        for (int number: closestNumbers) {
            System.out.print(number + " ");
        }
    }
}