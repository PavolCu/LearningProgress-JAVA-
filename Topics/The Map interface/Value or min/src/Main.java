import java.util.*;

class Main {
    private static int getOrMin(Map<String, Integer> map, String key) {
        // implement me
        int min = Integer.MAX_VALUE;
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            for (int value : map.values()) {
                min = Math.min(min, value);
            }
        }
        return min;
    }
    // do not change the code below

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, Integer> m = new HashMap<>();
        int size = scanner.nextInt();
        for (int i = 0; i < size; ++i) {
            String key = scanner.next();
            int value = scanner.nextInt();
            m.put(key, value);
        }
        String key = scanner.next();
        int result = getOrMin(Map.copyOf(m), key);
        System.out.println(result);
    }
}