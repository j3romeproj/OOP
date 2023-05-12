// Create a Java method that will compute the sum of two integers and two doubles
// separately, and after showing the result of the two sums, compute for the product 
// of the sums-the result must be a double data type.Requirement: Use method over loading.

import java.util.Scanner;

public class Main {
    // Function to sum two integers
    static int sum (int firstInt, int secondInt) {
        int sumInte = firstInt + secondInt;
        return sumInte;
    }
    // Function to sum two doubles
    static double sum (double firstDou, double secondDou) {
        double sumDoub = firstDou + secondDou;
        return sumDoub;
    }
    // Function to multiply the int and double
    static double sum (int sumInt, double sumDou) {
        double produSum = sumInt * sumDou;
        return produSum;
    }

    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        // Input
        System.out.print("Enter first integer: ");
        int firstInt = myScanner.nextInt();
        System.out.print("Enter second integer: ");
        int secondInt = myScanner.nextInt();
        System.out.print("Enter first double: ");
        double firstDou = myScanner.nextDouble();
        System.out.print("Enter first double: ");
        double secondDou = myScanner.nextDouble();
        // Function call to sum the int and double as well as to product the two sums
        int sumInt = sum(firstInt, secondInt);
        double sumDou = sum(firstDou, secondDou);
        double prodSum = sum(sumInt, sumDou);
        // Output
        System.out.println("Result for sum of integers: " + sumInt);
        System.out.println("Result for sum of integers: " + sumDou);
        System.out.println("Result for product of both sums: " + prodSum);
    }
}
