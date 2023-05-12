// Create a Java method that will multiply two integers without multiplication,
// division, bitwise operators, and loops.(Tip: use recursion)

import java.util.Scanner;

public class Main {
    // Function call to multiply without multiplication or using recursion
    static int multiply(int multiplicand, int multiplier) {
        if (multiplier == 0){
            return 0;
        }   else if (multiplier > 0){
            return multiplicand + multiply(multiplicand, multiplier - 1);
        } else {
            return -multiplicand + multiply(multiplicand, multiplier + 1);
        }
    }
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        // Input
        System.out.print("Enter your multiplicand: ");
        int multiplicand = myScanner.nextInt();
        System.out.print("Enter your multiplier: ");
        int multiplier = myScanner.nextInt();
        // Function call to use multiply function
        int multiplied = multiply(multiplicand, multiplier);
        // Output
        System.out.println("Product: " + multiplied);

    }
}
