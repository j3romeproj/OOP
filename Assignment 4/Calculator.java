import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator {
    public static void main(String[] args) {
        // Creating Frame to hold text fields
        JFrame frame = new JFrame("Calculator");
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        // JPanel with grid layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        // Labels for fields
        JLabel firstNumLabel = new JLabel("First Number:");
        JLabel secondNumLabel = new JLabel("Second Number:");
        JLabel resultNumLabel = new JLabel("Result:");

        // Create fields
        JTextField firstNumField = new JTextField();
        JTextField secondNumField = new JTextField();
        JTextField resultNumField = new JTextField();
        resultNumField.setEditable(false);

        // Create Buttons
        JButton addButton = new JButton("Add");
        addButton.setActionCommand("add");
        JButton subtractButton = new JButton("Subtract");
        subtractButton.setActionCommand("subtract");
        JButton multiplyButton = new JButton("Multiply");
        multiplyButton.setActionCommand("multiply");
        JButton divideButton = new JButton("Divide");
        divideButton.setActionCommand("divide");
        JButton clearButton = new JButton("Clear");
        clearButton.setActionCommand("clear");
        clearButton.setForeground(Color.BLUE);

        // Add the text fields to the panel
        panel.add(firstNumLabel);
        panel.add(firstNumField);
        panel.add(secondNumLabel);
        panel.add(secondNumField);
        panel.add(resultNumLabel);
        panel.add(resultNumField);

        // New panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(subtractButton);
        buttonPanel.add(multiplyButton);
        buttonPanel.add(divideButton);
        buttonPanel.add(clearButton);

        // Add panel to the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        // Display frame
        frame.setVisible(true);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String command = event.getActionCommand();
                String firstNumText = firstNumField.getText();
                String secondNumText = secondNumField.getText();
                double firstNum = Double.parseDouble(firstNumText);
                double secondNum = Double.parseDouble(secondNumText);
                double result = 0;

                switch (command) {
                    case "add":
                        result = firstNum + secondNum;
                        break;
                    case "subtract":
                        result = firstNum - secondNum;
                        break;
                    case "multiply":
                        result = firstNum * secondNum;
                        break;
                    case "divide":
                        result = firstNum / secondNum;
                        break;
                    case "clear":
                        firstNumField.setText("");
                        secondNumField.setText("");
                        resultNumField.setText("");
                    default:
                }

                resultNumField.setText(String.valueOf(result));
            }
        };

        // Add action listeners to the buttons
        addButton.addActionListener(actionListener);
        subtractButton.addActionListener(actionListener);
        multiplyButton.addActionListener(actionListener);
        divideButton.addActionListener(actionListener);
        clearButton.addActionListener(actionListener);
    }
}
