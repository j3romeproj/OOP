import com.google.gson.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.util.Base64;

public class Main {

    private static final String JSON_FILE = "account.json";
    private static final String[] COLUMN_NAMES = {"Website Name", "UserName", "Password"};

    private static final String adminPassword = "123456";

    private static JFrame loginFrame;
    private static JFrame mainFrame;
    private static JTable accountTable;
    private static DefaultTableModel accountTableModel;
    private static List<AccountData> accountDataList;

    public static void main(String[] args) {
        LoginFrame();
        Runtime.getRuntime().addShutdownHook(new Thread(Main::savePasswords));
    }

    private static void LoginFrame() {
        loginFrame = new JFrame("Login");
        loginFrame.setSize(270, 120);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("Username");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals("admin") && password.equals(adminPassword)) {
                loginFrame.dispose();
                MainFrame();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid username or password. Please try again.");
            }
        });

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel()); // empty label for alignment

        loginFrame.add(loginPanel, BorderLayout.NORTH);
        loginFrame.add(loginButton, BorderLayout.CENTER);
        loginFrame.setVisible(true);
    }

    private static void MainFrame() {
        mainFrame = new JFrame("Password Management");
        mainFrame.setSize(500, 500);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        JToggleButton encryptionToggleButton = new JToggleButton("Decrypt");
        encryptionToggleButton.addActionListener(e -> {
            int selectedRow = accountTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(mainFrame, "Please select a row");
            } else {
                String adminInput = JOptionPane.showInputDialog(mainFrame, "Enter admin password:");
                if (adminInput != null && adminInput.equals(adminPassword)) {
                    AccountData selectedAccountData = accountDataList.get(selectedRow);
                    if (encryptionToggleButton.isSelected()) {
                        accountTableModel.setValueAt(PasswordManager.getPassword(selectedRow), selectedRow, 2);
                        encryptionToggleButton.setText("Encrypt");
                    } else {
                        accountTableModel.setValueAt(selectedAccountData.getPassword(), selectedRow, 2);
                        encryptionToggleButton.setText("Decrypt");
                    }
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Invalid password. Please try again.");
                }
            }
        });

// Add a ListSelectionListener to the accountTable to update the toggle button state when a different row is selected
        accountTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = accountTable.getSelectedRow();
            if (selectedRow != -1) {
                AccountData selectedAccountData = accountDataList.get(selectedRow);
                if (encryptionToggleButton.isSelected()) {
                    accountTableModel.setValueAt(PasswordManager.getPassword(selectedRow), selectedRow, 2);
                } else {
                    accountTableModel.setValueAt(selectedAccountData.getPassword(), selectedRow, 2);
                }
            }
        });

        mainFrame.add(functionButton, BorderLayout.EAST);
        mainFrame.add(scrollPane, BorderLayout.NORTH);
        mainFrame.add(encryptionToggleButton, BorderLayout.WEST);
        mainFrame.setVisible(true);
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
        newFrame.setVisible(true);

        newSaveButton.addActionListener(e -> {
            try {
                AES_ENCRYPTION aes_encryption = new AES_ENCRYPTION();
                aes_encryption.init();
                String websiteName = websiteNameTextField.getText();
                String userName = userNameTextField.getText();
                String password = passwordTextField.getText();

                PasswordManager.savePassword(password);

                //ENCRYPTION AND DECRYPTION OF INSERTED PASSWORD.
                String encryptedPassword = aes_encryption.encrypt(password);
                String decryptNewPassword= aes_encryption.decrypt(encryptedPassword);

                addAccountData(new AccountData(websiteName, userName, encryptedPassword, decryptNewPassword));
                saveAccountData();

                newFrame.dispose();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });

        newCancelButton.addActionListener(e -> newFrame.dispose());
    }

    private static void editPassword() {
        int selectedIndex = accountTable.getSelectedRow();

        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(mainFrame, "Please select a row to edit.");
        } else {
            String adminInput = JOptionPane.showInputDialog(mainFrame, "Enter admin password:");
            if (adminInput != null && adminInput.equals(adminPassword)) {
                try {
                    AES_ENCRYPTION aes_encryption = new AES_ENCRYPTION();
                    aes_encryption.init();
                    AccountData selectedAccountData = accountDataList.get(selectedIndex);

                    JTextField websiteNameTextField = new JTextField(selectedAccountData.getWebsiteName());
                    JTextField userNameTextField = new JTextField(selectedAccountData.getUserName());
                    JTextField passwordTextField = new JTextField(PasswordManager.getPassword(selectedIndex));

                    websiteNameTextField.setEditable(false);
                    userNameTextField.setEditable(false);

                    Object[] message = {
                            "Website Name:", websiteNameTextField,
                            "Username:", userNameTextField,
                            "Password:", passwordTextField
                    };

                    int option = JOptionPane.showConfirmDialog(mainFrame, message, "Edit Password Entry", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        String newWebsiteName = websiteNameTextField.getText();
                        String newUserName = userNameTextField.getText();
                        String newPassword = passwordTextField.getText();

                        //ENCRYPT AND DECRYPT THE NEW PASSWORD.
                        String encryptNewPassword = aes_encryption.encrypt(newPassword);
                        String decryptNewPassword = aes_encryption.decrypt(encryptNewPassword);

                        selectedAccountData.setWebsiteName(newWebsiteName);
                        selectedAccountData.setUserName(newUserName);
                        selectedAccountData.setPassword(encryptNewPassword);
                        selectedAccountData.setPasswords(decryptNewPassword);

                        accountTableModel.setValueAt(newWebsiteName, selectedIndex, 0);
                        accountTableModel.setValueAt(newUserName, selectedIndex, 1);
                        accountTableModel.setValueAt(encryptNewPassword, selectedIndex, 2);

                        saveAccountData();
                    }
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Invalid password. Please try again.");
            }
        }
    }

    private static void deletePassword() {
        int selectedIndex = accountTable.getSelectedRow();

        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(mainFrame, "Please select a row to delete.");
        } else {
            String adminInput = JOptionPane.showInputDialog(mainFrame, "Enter admin password:");
            if (adminInput != null && adminInput.equals(adminPassword)) {
                int option = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to delete this password?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    accountTableModel.removeRow(selectedIndex);
                    accountDataList.remove(selectedIndex);

                    PasswordManager.deletePassword(selectedIndex);
                    saveAccountData(); // Save the updated account data to the JSON file
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Invalid password. Please try again.");
            }
        }
    }

    private static void addAccountData(AccountData accountData) {
        accountDataList.add(accountData);
        accountTableModel.addRow(accountData.toArray());
    }

    private static void saveAccountData() {
        try (FileWriter fileWriter = new FileWriter(JSON_FILE)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonArray jsonArray = new JsonArray();
            for (AccountData accountData : accountDataList) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("websiteName", accountData.getWebsiteName());
                jsonObject.addProperty("userName", accountData.getUserName());
                jsonObject.addProperty("password", accountData.getPassword());
                jsonArray.add(jsonObject);
            }
            fileWriter.write(gson.toJson(jsonArray));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void loadExistingData() {
        File file = new File(JSON_FILE);

        if (file.exists() && file.length() > 0) {
            try (FileReader fileReader = new FileReader(file)) {
                AES_ENCRYPTION aes_encryption = new AES_ENCRYPTION();
                aes_encryption.init();

                Gson gson = new Gson();
                AccountData[] accountDataArray = gson.fromJson(fileReader, AccountData[].class);


                if (accountDataArray != null) {
                    for (AccountData accountData : accountDataArray) {
                        addAccountData(accountData);
                    }
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
        PasswordManager.loadPasswordsFromFile();
    }

    private static void savePasswords() {
        PasswordManager.savePasswordsToFile();
    }
}

class AccountData {
    private String websiteName;
    private String userName;
    private String password;
    private String passwords;

    public AccountData(String websiteName, String userName, String encryptedPassword, String decryptNewPassword) {
        this.websiteName = websiteName;
        this.userName = userName;
        this.password = encryptedPassword;
        this.passwords = decryptNewPassword;
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

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public Object[] toArray() {
        return new Object[]{websiteName, userName, password};
    }
}

class AES_ENCRYPTION {
    private SecretKey key;
    private Cipher encryptionCipher;

    public void init() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        int KEY_SIZE = 128;
        keyGenerator.init(KEY_SIZE);
        key = keyGenerator.generateKey();
    }

    public String encrypt(String data) throws Exception {
        byte[] dataInBytes = data.getBytes();
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = encryptionCipher.doFinal(dataInBytes);
        return encode(encryptedBytes);
    }

    public String decrypt(String encryptedData) throws Exception {
        byte[] dataInBytes = decode(encryptedData);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        int DATA_LENGTH = 128;
        GCMParameterSpec spec = new GCMParameterSpec(DATA_LENGTH, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(dataInBytes);
        return new String(decryptedBytes);
    }

    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }
}

class PasswordManager {
    private static final String PASSWORD_FILE = "passwords.txt";
    private static final List<String> originalPasswords = new ArrayList<>();

    public static void savePassword(String password) {
        originalPasswords.add(password);
        savePasswordsToFile();
    }

    public static String getPassword(int index) {
        return originalPasswords.get(index);
    }

    public static void savePasswordsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PASSWORD_FILE))) {
            for (String password : originalPasswords) {
                writer.println(password);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadPasswordsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PASSWORD_FILE))) {
            String password;
            while ((password = reader.readLine()) != null) {
                originalPasswords.add(password);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deletePassword(int index) {
        originalPasswords.remove(index);
        savePasswordsToFile(); // Save the updated passwords to the file
    }
}
