import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner Scan = new Scanner(System.in);
        // Input
        System.out.println("Sample Input:");
        // Input four number to check of it is equal to each other
        System.out.print("\tInput first number: ");
        int firstNum = Scan.nextInt();
        System.out.print("\tInput second number: ");
        int secondNum = Scan.nextInt();
        System.out.print("\tInput third number: ");
        int thirdNum = Scan.nextInt();
        System.out.print("\tInput fourth number: ");
        int fourthNum = Scan.nextInt();
        // Output
        System.out.println("\nSample Output:");
        // Conditional Statement to identify if it is equal to each other
        if (firstNum != secondNum || secondNum != thirdNum || thirdNum != fourthNum) {
            System.out.println("\tNumbers are not equal");
        }else {
            System.out.println("\tNumbers are equal");
        }
    }
}
