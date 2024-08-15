import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingCalculator {

    private JFrame frame;
    private JTextField textField;
    private double num1 = 0;
    private double num2 = 0;
    private String operator = "";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SwingCalculator::new);
    }

    public SwingCalculator() {
        frame = new JFrame("Swing Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500); // Increased size to accommodate larger text field
        frame.setLayout(new BorderLayout());

        // Create and set up the text field
        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.BOLD, 40)); // Increased font size
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setPreferredSize(new Dimension(400, 100)); // Increased dimensions
        frame.add(textField, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 5, 5)); // Added spacing between buttons

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "C", "0", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 24)); // Increased font size for buttons
            button.setPreferredSize(new Dimension(80, 80)); // Increased button size
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String command = source.getText();

            switch (command) {
                case "C":
                    textField.setText("");
                    num1 = 0;
                    num2 = 0;
                    operator = "";
                    break;
                case "=":
                    num2 = Double.parseDouble(textField.getText());
                    double result;
                    switch (operator) {
                        case "+":
                            result = num1 + num2;
                            break;
                        case "-":
                            result = num1 - num2;
                            break;
                        case "*":
                            result = num1 * num2;
                            break;
                        case "/":
                            if (num2 != 0) {
                                result = num1 / num2;
                            } else {
                                JOptionPane.showMessageDialog(frame, "Error: Division by zero.");
                                textField.setText("");
                                return;
                            }
                            break;
                        default:
                            result = 0;
                    }
                    textField.setText(String.valueOf(result));
                    num1 = result;
                    operator = "";
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                    operator = command;
                    num1 = Double.parseDouble(textField.getText());
                    textField.setText("");
                    break;
                default:
                    textField.setText(textField.getText() + command);
            }
        }
    }
}
