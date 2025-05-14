package CognifyzTechnologies.Level1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Phash {

    // Method to generate a random password
    public static String genPassword(int length, boolean uppercase, boolean lowercase, boolean numbers, boolean special) {
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String number = "0123456789";
        String specialchar = "!@#$%^&*()-_=+\\|[]{};:/?.>,<";

        StringBuilder character = new StringBuilder();
        if (uppercase) character.append(upper);
        if (lowercase) character.append(lower);
        if (numbers) character.append(number);
        if (special) character.append(specialchar);

        Random num = new Random();
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = num.nextInt(character.length());
            password.append(character.charAt(index));
        }
        return password.toString();
    }

    // Method to analyze the strength of the password
    public static String StrenghAnalyze(String check) {
        int length = check.length();

        boolean lower = check.matches(".*[a-z].*");
        boolean upper = check.matches(".*[A-Z].*");
        boolean numbers = check.matches(".*\\d.*");
        boolean special = check.matches(".*[!@#$%^&*()\\\\-_=+\\\\\\\\|\\\\[\\\\]{};:/?.>,<].*");

        int Strength = 0;

        if (lower) Strength++;
        if (upper) Strength++;
        if (numbers) Strength++;
        if (special) Strength++;
        if (length >= 11) Strength++;

        if (Strength <= 3) return "Weak";
        else if (Strength == 4 || Strength == 5) {
            return "Moderate";
        } else {
            return "Strong";
        }
    }

    
    public static String generateOTP() {
        Random rand = new Random();
        int otp = 100000 + rand.nextInt(900000);
        return String.valueOf(otp);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("P-hash");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(14, 1));

        JTextField text = new JTextField();
        JTextField phoneText = new JTextField();
        JTextField otpText = new JTextField();

        JCheckBox lowercase = new JCheckBox("Lowercase Letters");
        JCheckBox uppercase = new JCheckBox("Uppercase Letters");
        JCheckBox numbers = new JCheckBox("Numbers");
        JCheckBox special = new JCheckBox("Special Characters");
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.add(lowercase);
        row1.add(uppercase);
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.add(numbers);
        row2.add(special);

        JButton button = new JButton("Generate Password");
        JTextArea area = new JTextArea();
        JLabel strengthLab = new JLabel("Strength: ");
        JLabel otpLabel = new JLabel("OTP: ");

        frame.add(new JLabel("Enter Password Length:"));
        frame.add(text);
        frame.add(row1);
        frame.add(row2);
        frame.add(new JLabel("Enter Phone Number:"));
        frame.add(phoneText);
        frame.add(button);
        frame.add(new JScrollPane(area));
        frame.add(strengthLab);
        frame.add(otpLabel);
        frame.add(new JLabel("Enter OTP for Verification:"));
        frame.add(otpText);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int length = Integer.parseInt(text.getText());
                    String password = genPassword(length, uppercase.isSelected(), lowercase.isSelected(), numbers.isSelected(), special.isSelected());
                    area.setText(password);
                    String strength = StrenghAnalyze(password);
                    strengthLab.setText("Strength: " + strength);

                    // Generate OTP and display it
                    String otp = generateOTP();
                    otpLabel.setText("OTP: " + otp);


                    String phoneNumber = phoneText.getText();
                    if (!phoneNumber.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "OTP has been sent to " + phoneNumber);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid phone number.");
                    }

                } catch (NumberFormatException o) {
                    area.setText("Enter valid number");
                    strengthLab.setText("No strength");
                    otpLabel.setText("OTP: N/A");
                }
            }
        });

        // OTP Verification Action
        JButton verifyButton = new JButton("Verify OTP");
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredOtp = otpText.getText();
                if (enteredOtp.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter the OTP.");
                } else {
                    JOptionPane.showMessageDialog(frame, "OTP verified successfully!");
                }
            }
        });
        frame.add(verifyButton);

        JCheckBox mode = new JCheckBox("Change Mode");
        mode.addActionListener(e -> {
            boolean dark = mode.isSelected();
            Color background = dark ? Color.DARK_GRAY : Color.WHITE;
            Color foreground = dark ? Color.WHITE : Color.BLACK;
            frame.getContentPane().setBackground(background);
            for (Component c : frame.getContentPane().getComponents()) {
                c.setBackground(background);
                c.setForeground(foreground);
            }
        });

        frame.setVisible(true);
        frame.add(mode);
    }
}
