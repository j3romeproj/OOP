import com.google.gson.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String JSON_FILE = "account.json";
    private static final String[] COLUMN_NAMES = {"Website Name", "UserName", "Password"};

    private static JFrame frame;
    private static JTable accountTable;
    private static DefaultTableModel accountTableModel;
    private static List<AccountData> accountDataList;

    public static void main(String[] args) {
        frame = new JFrame("Password Management");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);

        accountTableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        accountDataList = new ArrayList<>();

        loadExistingData();

        accountTable = new JTable(accountTableModel);
        JScrollPane scrollPane = new JScrollPane(accountTable);

        JPanel functionButton = new JPanel();
        JButton newButton = new JButton("New");
        newButton.addActionListener(e -> createPassword());

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e -> editPassword());

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deletePassword());

        functionButton.add(newButton);
        functionButton.add(editButton);
        functionButton.add(deleteButton);

        frame.add(functionButton, BorderLayout.EAST);
        frame.add(scrollPane, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    private static void createPassword() {
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
        websitesName.setHorizontalAlignment(SwingConstants.CENTER);
        usersName.setHorizontalAlignment(SwingConstants.CENTER);
        passwords.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel newButtonPanel = new JPanel();
        JButton newSaveButton = new JButton("Save");
        JButton newCancelButton = new JButton("Cancel");
        newButtonPanel.add(newSaveButton);
        newButtonPanel.add(newCancelButton);

        newFrame.add(newInputPanel, BorderLayout.NORTH);
        newFrame.add(newButtonPanel, BorderLayout.EAST);
        newFrame.setLocationRelativeTo(null);
        newFrame.setVisible(true);

        newSaveButton.addActionListener(e -> {
            String websiteName = websiteNameTextField.getText();
            String userName = userNameTextField.getText();
            String password = passwordTextField.getText();

            addAccountData(new AccountData(websiteName, userName, password));
            saveAccountData();

            newFrame.dispose();
        });

        newCancelButton.addActionListener(e -> newFrame.dispose());
    }

    private static void editPassword() {
        int selectedIndex = accountTable.getSelectedRow();

        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a row to edit.");
        } else {
            AccountData selectedAccountData = accountDataList.get(selectedIndex);

            JTextArea websiteNameTextField = new JTextArea(selectedAccountData.getWebsiteName());
            JTextArea userNameTextField = new JTextArea(selectedAccountData.getUserName());
            JTextField passwordTextField = new JTextField(selectedAccountData.getPassword());

            websiteNameTextField.setEditable(false);
            userNameTextField.setEditable(false);

            Object[] message = {
                    "Website Name:", websiteNameTextField,
                    "Username:", userNameTextField,
                    "Password:", passwordTextField
            };

            int option = JOptionPane.showConfirmDialog(frame, message, "Edit Password Entry", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String newWebsiteName = websiteNameTextField.getText();
                String newUserName = userNameTextField.getText();
                String newPassword = passwordTextField.getText();

                selectedAccountData.setWebsiteName(newWebsiteName);
                selectedAccountData.setUserName(newUserName);
                selectedAccountData.setPassword(newPassword);

                accountTableModel.setValueAt(newWebsiteName, selectedIndex, 0);
                accountTableModel.setValueAt(newUserName, selectedIndex, 1);
                accountTableModel.setValueAt(newPassword, selectedIndex, 2);

                saveAccountData();
            }
        }
    }

    private static void deletePassword() {
        int selectedIndex = accountTable.getSelectedRow();

        if (selectedIndex != -1) {
            int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this password?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                accountTableModel.removeRow(selectedIndex);
                accountDataList.remove(selectedIndex);
                saveAccountData();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a row to delete.");
        }
    }

    private static void addAccountData(AccountData accountData) {
        accountDataList.add(accountData);
        accountTableModel.addRow(accountData.toArray());
    }

    private static void saveAccountData() {
        try (FileWriter fileWriter = new FileWriter(JSON_FILE)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(accountDataList, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadExistingData() {
        File file = new File(JSON_FILE);

        if (file.exists() && file.length() > 0) {
            try (FileReader fileReader = new FileReader(file)) {
                Gson gson = new Gson();
                AccountData[] accountDataArray = gson.fromJson(fileReader, AccountData[].class);

                if (accountDataArray != null) {
                    for (AccountData accountData : accountDataArray) {
                        addAccountData(accountData);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class AccountData {
    private String websiteName;
    private String userName;
    private String password;

    public AccountData(String websiteName, String userName, String password) {
        this.websiteName = websiteName;
        this.userName = userName;
        this.password = password;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object[] toArray() {
        return new Object[]{websiteName, userName, password};
    }
}
