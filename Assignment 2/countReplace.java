// Create a Java method that will count the number of words in the 
// provided string and will ask for an input from the user for a word 
// to replace in the given string and print the result in uppercase.

import java.util.Scanner;

public class Main {
    // Function to replace a word
    static String newSentence(String sentence, String replaceWord, String replacementWord) {
        String brandNew = sentence.replace(replaceWord, replacementWord);
        return brandNew;
    }
    // Function to count words in string
    static int noString(String replacedSentence) {
        int numString = replacedSentence.split("\\s").length;
        return numString;
    }
    
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        // Input
        System.out.print(" Enter your sentence: ");
        String sentence = myScanner.nextLine();
        System.out.print(" What is the word you want to replace? ");
        String replaceWord = myScanner.nextLine();
        System.out.print(" What word do you want for replacement? ");
        String replacementWord = myScanner.nextLine();
        // Function Call to replace and count a words
        String replacedSentence = newSentence(sentence, replaceWord, replacementWord);
        int numString = noString(replacedSentence);
        // Output
        System.out.println("Result");
        System.out.println("Number of words in the string " + numString);
        System.out.println("Revise sentence: " + replacedSentence.toUpperCase());
    }
}
