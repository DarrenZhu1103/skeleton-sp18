public class Palindrome {

    public Deque<Character> wordToDeque(String word){
        Deque<Character> result = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++)
            result.addLast(word.charAt(i));
        return result;
    }

    public boolean isPalindrome(String word){
        return isPalindrome(wordToDeque(word));
    }

    private boolean isPalindrome(Deque<Character> arr){
        if (arr.isEmpty() || arr.size() == 1)
            return true;
        if (arr.removeFirst() != arr.removeLast())
            return false;
        return isPalindrome(arr);
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        return isPalindrome(wordToDeque(word), cc);
    }

    private boolean isPalindrome(Deque<Character> arr, CharacterComparator cc){
        if (arr.isEmpty() || arr.size() == 1)
            return true;
        if (!cc.equalChars(arr.removeFirst(), arr.removeLast()))
            return false;
        return isPalindrome(arr, cc);
    }
}
