// Create Java method(s)to check whether a string is a valid password and
// provide the necessary conditional statement if the terms/rules are not
// followed (Password is not valid).
// Password rules:
// A password must have at least ten characters.
// A password consists of only letters and digits.
// A password must contain at least two digits.

import java.util.Scanner;

public class Main {
    // Condition one Length of Password allowed
    static boolean passLength(String password) {
        if(password.length() >= 10) {
            return true;
        }
        return false;
    }
    // Condition two Only Letters and Digit allowed
    static boolean passLetDig(String password) {
        int len = password.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isLetterOrDigit(password.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    // Condition three Must be At least Two Digit
    static boolean passTwoDig(String password) {
        int numberOfDigit = 0;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                numberOfDigit++;
                if (numberOfDigit >= 2) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);

        // Input Statement
        System.out.println("Password Rules:");
        System.out.println("\t1. A password must have at least ten characters.");
        System.out.println("\t2. A password consists of only letters and digits.");
        System.out.println("\t3. A password must contain at least two digits.");
        System.out.print("Input a password: ");
        String password = myScanner.nextLine();
        // Boolean initialization that holds the answer for functions (True or False)
        boolean passLen = passLength(password);
        boolean passLeDi = passLetDig(password);
        boolean passTuDi = passTwoDig(password);
        // Conditional Statement that determine if it is valid or invalid
        if (passLen && passLeDi && passTuDi) {
                System.out.println("Password is Valid: " + password);
        }else {
            System.out.println("Password is Invalid: " + password);
        }
    }
}
