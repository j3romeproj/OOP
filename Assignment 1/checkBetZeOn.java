import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner Scan = new Scanner(System.in);
        // Input
        System.out.println("Sample Input:");
        // Input two number to check if it is between 0 and 1
        System.out.print("\tInput first number: ");
        float firstNum = Scan.nextFloat();
        System.out.print("\tInput second number: ");
        float secondNum = Scan.nextFloat();
        // Output
        System.out.println("\nSample Output:");
        // Conditional Statement that check if it is between 0 and 1
        if (firstNum > 0 && firstNum < 1) {
            if (secondNum > 0 && secondNum < 1){
                System.out.println("\t" + true);
            }else {
                System.out.println("\t" + false);
            }
        }else {
            System.out.println("\t" + false);
        }
    }
}
