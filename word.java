import java.util.Arrays;
/**
 * Word class is used for representing a word in the Scrabble Game
 * Implements Comparable for binary search functionality
 */

public class Word{
    private String wordText;

    // Contructor - create a new Word object
    public Word(String text){
        this.wordText = text.toLowerCase();

    }
    // Getter method to access the word
    public String getWordText() {
        return wordText;
    }

    // Check if this word can be made from the given letters available
    public boolean canMakeFrom(String letters) {
        String lettersLeft = letters.toLowerCase();
        
        // Check each letter in the word
        for (char letter : wordText.toCharArray()) {
            int letterPosition = lettersLeft.indexOf(letter);
            
            // If letter not found, return false
            if (letterPosition == -1) {
                return false;
            }
            
            // Remove the used letter from lettersLeft
            lettersLeft = lettersLeft.substring(0, letterPosition) + 
                          lettersLeft.substring(letterPosition + 1);
        }

        return true;
    }
}
