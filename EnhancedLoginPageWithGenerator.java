import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class EnhancedLoginPageWithGenerator extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public EnhancedLoginPageWithGenerator() {
        setTitle("Login Page");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Set up main panel
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(50, 50, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username Label and Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        // Password Label and Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // Generate Password Button
        JButton generatePasswordButton = new JButton("Generate Password");
        generatePasswordButton.setBackground(new Color(70, 130, 180));
        generatePasswordButton.setForeground(Color.WHITE);
        generatePasswordButton.setFocusPainted(false);
        generatePasswordButton.setFont(new Font("Arial", Font.BOLD, 12));

        generatePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PasswordApp passwordApp = new PasswordApp();
                passwordApp.setVisible(true);
                String generatedPassword = passwordApp.getGeneratedPassword();
                if (generatedPassword != null) {
                    passwordField.setText(generatedPassword);
                }
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(generatePasswordButton, gbc);

        // Login Button
        gbc.gridx = 1;
        gbc.gridy = 3;
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(34, 139, 34));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (!username.isEmpty() && !password.isEmpty()) {
                    JOptionPane.showMessageDialog(EnhancedLoginPageWithGenerator.this, 
                                                  "Logged in successfully!", 
                                                  "Login Status", 
                                                  JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(EnhancedLoginPageWithGenerator.this, 
                                                  "Please enter both username and password.", 
                                                  "Login Error", 
                                                  JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        panel.add(loginButton, gbc);

        add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EnhancedLoginPageWithGenerator().setVisible(true);
        });
    }
}

// Password Generator Class
class PasswordApp extends JDialog {
    private JTextField passwordField;
    private JSpinner lengthSpinner;
    private JCheckBox includeLowercase;
    private JCheckBox includeUppercase;
    private JCheckBox includeNumbers;
    private JCheckBox includeSymbols;
    private JCheckBox includeAboveAll;
    private String generatedPassword;

    public PasswordApp() {
        setTitle("Password Generator");
        setSize(450, 350);  // Increased size
        setModal(true);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Password length spinner
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Password Length:"), gbc);

        lengthSpinner = new JSpinner(new SpinnerNumberModel(8, 4, 20, 1));
        gbc.gridx = 1;
        add(lengthSpinner, gbc);

        // Checkboxes for character types
        includeLowercase = new JCheckBox("Include Lowercase");
        includeUppercase = new JCheckBox("Include Uppercase");
        includeNumbers = new JCheckBox("Include Numbers");
        includeSymbols = new JCheckBox("Include Symbols");
        includeAboveAll = new JCheckBox("Include 'Above All'");

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(includeLowercase, gbc);

        gbc.gridy = 2;
        add(includeUppercase, gbc);

        gbc.gridy = 3;
        add(includeNumbers, gbc);

        gbc.gridy = 4;
        add(includeSymbols, gbc);

        gbc.gridy = 5;
        add(includeAboveAll, gbc);

        // Generate Button
        JButton generateButton = new JButton("Generate Password");
        generateButton.setBackground(new Color(70, 130, 180));
        generateButton.setForeground(Color.WHITE);
        generateButton.setFocusPainted(false);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePassword();
            }
        });

        gbc.gridy = 6;
        add(generateButton, gbc);

        // Display generated password
        passwordField = new JTextField(20);  // Made the text field wider
        passwordField.setEditable(false);
        passwordField.setBackground(Color.BLACK);
        passwordField.setForeground(Color.WHITE);
        gbc.gridy = 7;
        add(passwordField, gbc);
    }

    private void generatePassword() {
        int length = (int) lengthSpinner.getValue();
        boolean lower = includeLowercase.isSelected();
        boolean upper = includeUppercase.isSelected();
        boolean numbers = includeNumbers.isSelected();
        boolean symbols = includeSymbols.isSelected();
        boolean aboveAll = includeAboveAll.isSelected();

        String lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
        String uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numberChars = "0123456789";
        String symbolChars = "!@#$%^&*()_+~`|}{[]:;?><,./-=\\'\"";
        String aboveAllChars = "AboveAll";  // New characters to add

        StringBuilder allChars = new StringBuilder();

        if (lower) allChars.append(lowercaseChars);
        if (upper) allChars.append(uppercaseChars);
        if (numbers) allChars.append(numberChars);
        if (symbols) allChars.append(symbolChars);
        if (aboveAll) allChars.append(aboveAllChars);  // Add "Above All" option

        if (allChars.length() == 0 || length == 0) {
            passwordField.setText("Select options!");
            generatedPassword = null;
            return;
        }

        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allChars.length());
            password.append(allChars.charAt(randomIndex));
        }

        generatedPassword = password.toString();
        passwordField.setText(generatedPassword);
    }

    // Method to return the generated password to the main login page
    public String getGeneratedPassword() {
        return generatedPassword;
    }
}
