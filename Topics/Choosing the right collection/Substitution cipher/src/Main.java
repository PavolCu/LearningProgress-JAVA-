import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the original and cipher alphabets
        String originalAlphabet = scanner.nextLine();
        String cipherAlphabet = scanner.nextLine();

        // Create mappings for encoding and decoding
        Map<Character, Character> encodeMap = new HashMap<>();
        Map<Character, Character> decodeMap = new HashMap<>();
        for (int i = 0; i < originalAlphabet.length(); i++) {
            encodeMap.put(originalAlphabet.charAt(i), cipherAlphabet.charAt(i));
            decodeMap.put(cipherAlphabet.charAt(i), originalAlphabet.charAt(i));
        }

        // Read the strings to encode and decode
        String stringToEncode = scanner.nextLine();
        String stringToDecode = scanner.nextLine();

        // Encode
        StringBuilder encodedString = new StringBuilder();
        for (char c : stringToEncode.toCharArray()) {
            encodedString.append(encodeMap.get(c));
        }

        // Decode
        StringBuilder decodedString = new StringBuilder();
        for (char c : stringToDecode.toCharArray()) {
            decodedString.append(decodeMap.get(c));
        }

        // Output the results
        System.out.println(encodedString);
        System.out.println(decodedString);
    }
}