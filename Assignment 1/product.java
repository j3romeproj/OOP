import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner Scan = new Scanner(System.in);
        // Input
        System.out.println("Sample Input:");
        // Input two numbers to multiply
        System.out.print("\tInput first number: ");
        int firstNum = Scan.nextInt();
        System.out.print("\tInput second number: ");
        int secondNum = Scan.nextInt();
        // Output
        System.out.println("\nSample Output:");
        // Computation for product
        int product = firstNum * secondNum;
        System.out.println("\t" + firstNum + " x " + secondNum + " = " + product);
    }
}
