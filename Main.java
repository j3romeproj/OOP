import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner Scan = new Scanner(System.in);
        // Input
        System.out.println("Sample Input:");
        // Input a Number
        System.out.print("\tInput a number: ");
        int number = Scan.nextInt();
        // Output
        System.out.println("\nSample Output:");
        // Loops to multiply 10 times
        for (int i = 1; i <= 10; i++) {
            int product = number * i;
            System.out.println("\t" + number + " x " + i + " = " + product);
        }
    }
}