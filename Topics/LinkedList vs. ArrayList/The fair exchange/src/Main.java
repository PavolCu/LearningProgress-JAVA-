import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

class ListOperations {
    public static void changeHeadsAndTails(LinkedList<String> linkedList, ArrayList<String> arrayList) {
        // write your code here
        String head = linkedList.getFirst();
        String tail = linkedList.getLast();
        linkedList.set(0, arrayList.get(0));
        linkedList.set(linkedList.size() - 1, arrayList.get(arrayList.size() - 1));
        arrayList.set(0, head);
        arrayList.set(arrayList.size() - 1, tail);
        /*LinkedList<String> linkedList1 = new LinkedList<>(Arrays.asList("f", "b", "c", "d", "j"));
        ArrayList<String> arrayList1 = new ArrayList<>(Arrays.asList("a", "g", "h", "i", "e"));
        ListOperations.changeHeadsAndTails(linkedList1, arrayList1);
        System.out.println(linkedList1);
        System.out.println(arrayList1);*/

    }
}
