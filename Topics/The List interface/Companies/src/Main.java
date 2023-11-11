import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        List<String> list = new ArrayList<>();

        while (scanner.hasNext()) {
            list.add(scanner.next());
        }

        System.out.println(list);
    }
}