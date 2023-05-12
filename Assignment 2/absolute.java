// Create a Java method that will convert a floating value to an absolute value.

import java.util.Scanner;

public class Main {
    // Function to convert floating value to absolute value
    static float absolute(float number)
    {
        float absnumber = (number >= 0) ? number : -number;
        return absnumber;
    }

    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        // Input
        System.out.print("Enter a float number: ");
        // Function call to convert floating value to absolute value
        float number = myScanner.nextFloat();
        // Output
        System.out.println("Absolute value: " + absolute(number));
    }
}
