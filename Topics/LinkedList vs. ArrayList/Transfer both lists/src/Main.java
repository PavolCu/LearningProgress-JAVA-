import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class ListOperations {
    public static void transferAllElements(LinkedList<String> linkedList, ArrayList<String> arrayList) {
        // write your code here
        List<String> tempList = new ArrayList<>(linkedList);
        ArrayList<String> tempArray = new ArrayList<>(arrayList);

        linkedList.clear();
        arrayList.clear();

        arrayList.addAll(tempList);
        linkedList.addAll(tempArray);
    }
}