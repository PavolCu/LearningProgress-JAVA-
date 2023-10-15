import java.util.*;


class MapFunctions {

    public static void calcTheSamePairs(Map<String, String> map1, Map<String, String> map2) {
        // write your code here
        int count = 0;
        for (Map.Entry<String, String> entry1 : map1.entrySet()) {
            for (Map.Entry<String, String> entry2 : map2.entrySet()) {
                if (entry1.getKey().equals(entry2.getKey()) && entry1.getValue().equals(entry2.getValue())) {
                    count++;
                }
            }
        }
        System.out.println(count);
    }
}