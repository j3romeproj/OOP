import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.table.DefaultTableModel;

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
        JsonManipulation.loadExistingData();

        accountTable = new JTable(accountTableModel);
        JScrollPane scrollPane = new JScrollPane(accountTable);

        JPanel functionButton = new JPanel();
        JButton newButton = new JButton("New");
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataManipulation.createPassword();
            }
        });

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataManipulation.editPassword();
            }
        });
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = accountTable.getSelectedRow();

                if (selectedIndex != -1) {
                    accountTableModel.removeRow(selectedIndex);
                    JsonManipulation.deleteJsonData(selectedIndex);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a row to delete.");
                }
            }
        });

        functionButton.add(newButton);
        functionButton.add(editButton);
        functionButton.add(deleteButton);

        frame.add(functionButton, BorderLayout.EAST);
        frame.add(scrollPane, BorderLayout.NORTH);
        frame.setVisible(true);
    }
}

class DataManipulation {
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

                Main.accountTableModel.addRow(new Object[]{websiteName, userName, password});
                JsonManipulation.writeJsonData(websiteName, userName, password);
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
        int selectedIndex = Main.accountTable.getSelectedRow();

        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(Main.frame, "Please select a row to edit.");
        } else {
            // START OF FRAME
            JPanel existingInputPanel = new JPanel();
            existingInputPanel.setLayout(new GridLayout(3, 2));
            JLabel existingWebsitesName = new JLabel("Website Name");
            JTextArea existingWebName = new JTextArea((String) Main.accountTableModel.getValueAt(selectedIndex, 0));
            JLabel existingUsersName = new JLabel("UserName");
            JTextArea existingUser = new JTextArea((String) Main.accountTableModel.getValueAt(selectedIndex, 1));
            JLabel existingPasswords = new JLabel("password");
            JTextArea existingPass = new JTextArea((String) Main.accountTableModel.getValueAt(selectedIndex, 2));
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

            Object[] message = {
                    "Website Name:", websiteNameTextField,
                    "Username:", userNameTextField,
                    "Password:", passwordTextField
            };

            JPanel editButtonPanel = new JPanel();
            JButton editSaveButton = new JButton("Save");
            JButton editCancelButton = new JButton("Cancel");
            editButtonPanel.add(editSaveButton);
            editButtonPanel.add(editCancelButton);

            editFrame.add(existingInputPanel, BorderLayout.NORTH);
            editFrame.add(editInputPanel, BorderLayout.CENTER);
            editFrame.add(editButtonPanel, BorderLayout.SOUTH);
            editFrame.setVisible(true);
            // END OF FRAME
            // START OF ACTION LISTENER
            editSaveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int option = JOptionPane.showConfirmDialog(Main.frame, message, "Edit Password Entry", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        String newWebsiteName = websiteNameTextField.getText();
                        String newUserName = userNameTextField.getText();
                        String newPassword = passwordTextField.getText();

                        Main.accountTableModel.setValueAt(newWebsiteName, selectedIndex, 0);
                        Main.accountTableModel.setValueAt(newUserName, selectedIndex, 1);
                        Main.accountTableModel.setValueAt(newPassword, selectedIndex, 2);

                        // Update the JSON data
                        JsonManipulation.updateJsonData(selectedIndex, newWebsiteName, newUserName, newPassword);
                    }

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
}

class JsonManipulation {
    public static void writeJsonData(String websiteName, String userName, String password) {
        try {
            File file = new File("account.json");
            JsonArray jsonArray;

            if (file.length() == 0) {
                jsonArray = new JsonArray();
            } else {
                FileReader fileReader = new FileReader(file);
                JsonParser jsonParser = new JsonParser();
                jsonArray = jsonParser.parse(fileReader).getAsJsonArray();
                fileReader.close();
            }

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Website Name", websiteName);
            jsonObject.addProperty("Username", userName);
            jsonObject.addProperty("Password", password);
            jsonArray.add(jsonObject);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonStr = gson.toJson(jsonArray);

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonStr);
            fileWriter.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void loadExistingData() {
        try {
            File file = new File("account.json");

            if (file.exists() && file.length() > 0) {
                FileReader fileReader = new FileReader(file);
                JsonParser jsonParser = new JsonParser();
                JsonArray jsonArray = jsonParser.parse(fileReader).getAsJsonArray();
                fileReader.close();

                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                    String websiteName = jsonObject.get("Website Name").getAsString();
                    String userName = jsonObject.get("Username").getAsString();
                    String password = jsonObject.get("Password").getAsString();

                    Main.accountTableModel.addRow(new Object[]{websiteName, userName, password});
                }
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void updateJsonData(int selectedIndex, String websiteName, String userName, String password) {
        try {
            File file = new File("account.json");

            if (file.exists() && file.length() > 0) {
                FileReader fileReader = new FileReader(file);
                JsonParser jsonParser = new JsonParser();
                JsonArray jsonArray = jsonParser.parse(fileReader).getAsJsonArray();
                fileReader.close();

                JsonObject jsonObject = jsonArray.get(selectedIndex).getAsJsonObject();
                jsonObject.addProperty("Website Name", websiteName);
                jsonObject.addProperty("Username", userName);
                jsonObject.addProperty("Password", password);

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String jsonStr = gson.toJson(jsonArray);

                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(jsonStr);
                fileWriter.close();
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void deleteJsonData(int selectedIndex) {
        try {
            File file = new File("account.json");

            if (file.exists() && file.length() > 0) {
                FileReader fileReader = new FileReader(file);
                JsonParser jsonParser = new JsonParser();
                JsonArray jsonArray = jsonParser.parse(fileReader).getAsJsonArray();
                fileReader.close();

                jsonArray.remove(selectedIndex);

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String jsonStr = gson.toJson(jsonArray);

                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(jsonStr);
                fileWriter.close();
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
