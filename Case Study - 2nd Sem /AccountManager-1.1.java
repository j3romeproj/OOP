import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.swing.table.DefaultTableModel;
import java.util.Base64;

public class Main {
    public static JFrame frame;
    public static JTable accountTable;
    public static DefaultTableModel accountTableModel;

    public static void main(String[] args) {
        frame = new JFrame("Password Management");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);

        accountTableModel = new DefaultTableModel();
        accountTableModel.addColumn("Website Name");
        accountTableModel.addColumn("UserName");
        accountTableModel.addColumn("Password");

        accountTable = new JTable(accountTableModel);
        JScrollPane scrollPane = new JScrollPane(accountTable);

        JPanel functionButton = new JPanel();
        JButton newButton = new JButton("New");
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPassword();
            }
        });

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editPassword();
            }
        });
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = accountTable.getSelectedRowCount();

                accountTableModel.removeRow(selectedIndex - 1);
            }
        });

        functionButton.add(newButton);
        functionButton.add(editButton);
        functionButton.add(deleteButton);

        frame.add(functionButton, BorderLayout.EAST);
        frame.add(scrollPane, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    public static void createPassword() {
        JFrame newFrame = new JFrame("New Password Entry");
        newFrame.setSize(300, 135);

        JPanel newInputPanel = new JPanel();
        newInputPanel.setLayout(new GridLayout(3, 2));
        JLabel websitesName = new JLabel("Website Name");
        JTextField websiteNameTextField = new JTextField(15);
        JLabel usersName = new JLabel("UserName");
        JTextField userNameTextField = new JTextField(15);
        JLabel passwords = new JLabel("password");
        JTextField passwordTextField = new JTextField(15);
        newInputPanel.add(websitesName);
        newInputPanel.add(websiteNameTextField);
        newInputPanel.add(usersName);
        newInputPanel.add(userNameTextField);
        newInputPanel.add(passwords);
        newInputPanel.add(passwordTextField);

        JPanel newButtonPanel = new JPanel();
        JButton newSaveButton = new JButton("Save");
        JButton newCancelButton = new JButton("Cancel");
        newButtonPanel.add(newSaveButton);
        newButtonPanel.add(newCancelButton);

        newFrame.add(newInputPanel, BorderLayout.NORTH);
        newFrame.add(newButtonPanel, BorderLayout.EAST);
        newFrame.setVisible(true);

        newSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String websiteName = websiteNameTextField.getText();
                String userName = userNameTextField.getText();
                String password = passwordTextField.getText();

                accountTableModel.addRow(new Object[]{websiteName, userName, password});

                newFrame.dispose(); // Close the new password entry frame
            }
        });
        newCancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFrame.dispose(); // Close the new password entry frame
            }
        });
    }

    public static void editPassword() {
        JFrame editFrame = new JFrame("Edit Existing Password Entry");
        editFrame.setSize(300, 300);
        int selectedIndex = accountTable.getSelectedRowCount();

        JPanel existingInputPanel = new JPanel();
        existingInputPanel.setLayout(new GridLayout(3, 2));
        JLabel existingWebsitesName = new JLabel("Website Name");
        JTextArea existingWebName = new JTextArea((String) accountTableModel.getValueAt(selectedIndex - 1, 0));
        JLabel existingUsersName = new JLabel("UserName");
        JTextArea existingUser = new JTextArea((String) accountTableModel.getValueAt(selectedIndex - 1, 1));
        JLabel existingPasswords = new JLabel("password");
        JTextArea existingPass = new JTextArea((String) accountTableModel.getValueAt(selectedIndex - 1, 2));
        existingInputPanel.add(existingWebsitesName);
        existingInputPanel.add(existingWebName);
        existingInputPanel.add(existingUsersName);
        existingInputPanel.add(existingUser);
        existingInputPanel.add(existingPasswords);
        existingInputPanel.add(existingPass);

        JPanel editInputPanel = new JPanel();
        JLabel websitesName = new JLabel("Website Name");
        JTextField websiteNameTextField = new JTextField(15);
        JLabel usersName = new JLabel("UserName");
        JTextField userNameTextField = new JTextField(15);
        JLabel passwords = new JLabel("password");
        JTextField passwordTextField = new JTextField(15);
        editInputPanel.add(websitesName);
        editInputPanel.add(websiteNameTextField);
        editInputPanel.add(usersName);
        editInputPanel.add(userNameTextField);
        editInputPanel.add(passwords);
        editInputPanel.add(passwordTextField);

        JPanel editButtonPanel = new JPanel();
        JButton editSaveButton = new JButton("Save");
        JButton editCancelButton = new JButton("Cancel");
        editButtonPanel.add(editSaveButton);
        editButtonPanel.add(editCancelButton);

        editFrame.add(existingInputPanel, BorderLayout.NORTH);
        editFrame.add(editInputPanel, BorderLayout.CENTER);
        editFrame.add(editButtonPanel, BorderLayout.SOUTH);
        editFrame.setVisible(true);

        editSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String websiteName = websiteNameTextField.getText();
                String userName = userNameTextField.getText();
                String password = passwordTextField.getText();

                accountTableModel.insertRow(selectedIndex, new Object[]{websiteName, userName, password});
                accountTableModel.removeRow(selectedIndex - 1);

                editFrame.dispose(); // Close the new password entry frame
            }
        });
        editCancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editFrame.dispose(); // Close the new password entry frame
            }
        });
    }
}
