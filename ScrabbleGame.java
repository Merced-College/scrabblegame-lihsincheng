import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * This class runs our simple Scrabble game.
 * It gives players letters and checks their words.
 */
public class ScrabbleGame {
    private ArrayList<Word> wordList; // Our dictionary
    private String currentLetters;    // Letters given to player
    
    // Set up the game
    public ScrabbleGame() {
        wordList = new ArrayList<>();
        loadDictionary("CollinsScrabbleWords_2019.txt");
        Collections.sort(wordList, (a, b) -> a.getWordText().compareTo(b.getWordText()));
    }
    
    // Load words from file
    private void loadDictionary(String filename) {
        try {
            Scanner fileScanner = new Scanner(new File(filename));
            System.out.println("Loading dictionary...");
            
            while (fileScanner.hasNextLine()) {
                String word = fileScanner.nextLine().trim();
                if (!word.isEmpty()) {
                    wordList.add(new Word(word));
                }
            }
            fileScanner.close();
            System.out.println("Dictionary loaded with " + wordList.size() + " words!");
        } catch (FileNotFoundException e) {
            System.out.println("Oops! Couldn't find the dictionary file.");
            System.exit(1);
        }
    }
    
    // Give player 4 random letters
    public void generateLetters() {
        String letters = "";
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        
        for (int i = 0; i < 4; i++) {
            int randomIndex = (int)(Math.random() * alphabet.length());
            letters += alphabet.charAt(randomIndex);
        }
        
        currentLetters = letters;
        System.out.println("\nYour letters are: " + currentLetters);
    }
    
    // Check if word is valid
    public boolean checkWord(String word) {
        // First check if word exists in dictionary
        boolean found = false;
        for (Word dictWord : wordList) {
            if (dictWord.getWordText().equals(word.toLowerCase())) {
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("That word isn't in our dictionary.");
            return false;
        }
        
        // Then check if it can be made from current letters
        Word testWord = new Word(word);
        if (!testWord.canMakeFrom(currentLetters)) {
            System.out.println("You can't make that word with your letters.");
            return false;
        }
        
        return true;
    }
    
    // Calculate score based on word length
    public int getScore(String word) {
        int length = word.length();
        if (length == 1) return 1;
        if (length == 2) return 2;
        if (length == 3) return 3;
        if (length == 4) return 5; // Bonus for using all letters!
        return 0;
    }
    
    // Play the game
    public void play() {
        Scanner input = new Scanner(System.in);
        int totalScore = 0;
        
        System.out.println("Welcome to Simple Scrabble!");
        System.out.println("You'll get 4 random letters. Make a word to score points!");
        
        while (true) {
            generateLetters();
            
            System.out.print("Enter your word (or 'quit' to end): ");
            String playerWord = input.nextLine().trim().toLowerCase();
            
            if (playerWord.equals("quit")) {
                break;
            }
            
            if (checkWord(playerWord)) {
                int points = getScore(playerWord);
                totalScore += points;
                System.out.println("Good job! +" + points + " points!");
                System.out.println("Total score: " + totalScore);
            }
            
            System.out.print("Play again? (yes/no): ");
            String choice = input.nextLine().trim().toLowerCase();
            if (!choice.equals("yes")) {
                break;
            }
        }
        
        System.out.println("\nGame over! Your final score: " + totalScore);
        input.close();
    }
    
    public static void main(String[] args) {
        ScrabbleGame game = new ScrabbleGame();
        game.play();
    }
}