

class StringUtils {
    public static boolean isPalindrome(String str) {
        if (str == null || str.isEmpty()) {
            return false; // Handle null or empty input as non-palindrome
        }

        // Remove spaces and the ' character, and convert to lowercase
        String cleanStr = str.replaceAll("[\\s']+", "").toLowerCase();

        int left = 0;
        int right = cleanStr.length() - 1;

        while (left < right) {
            if (cleanStr.charAt(left) != cleanStr.charAt(right)) {
                return false; // Characters don't match, not a palindrome
            }
            left++;
            right--;
        }

        return true; // All characters matched, it's a palindrome
    }
}
