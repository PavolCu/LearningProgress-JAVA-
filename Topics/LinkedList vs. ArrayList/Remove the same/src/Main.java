import java.util.*;

class ListOperations {
    public static void removeTheSame(LinkedList<String> linkedList, ArrayList<String> arrayList) {
        // write your code here
        int size = linkedList.size();
        for (int i = 0; i < size; i++) {
            if (linkedList.get(i).equals(arrayList.get(i))) {
                linkedList.remove(i);
                arrayList.remove(i);
                i--; // Adjust the index to account for the removed element
                size--; // Decrease the length to avoid IndexOutOfBoundsException
            }
        }
    }
}