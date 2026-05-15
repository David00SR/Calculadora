import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {

    private final int WIDTH = 360;
    private final int HEIGHT = 540;

    private final Color LIGHT_GRAY = new Color(212, 212, 210);
    private final Color DARK_GRAY = new Color(80, 80, 80);
    private final Color BLACK = new Color(28, 28, 28);
    private final Color ORANGE = new Color(255, 149, 0);

    private final String[] buttonValues = {
            "AC", "+/-", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "√", "="
    };

    private final String[] rightSymbols = {"÷", "×", "-", "+", "="};
    private final String[] topSymbols = {"AC", "+/-", "%"};

    private JFrame frame;
    private JLabel displayLabel;
    private JPanel buttonsPanel;

    private String firstNumber = "";
    private String operator = "";

    public Calculator() {

        createWindow();
        createDisplay();
        createButtons();

        frame.setVisible(true);
    }

    private void createWindow() {
        frame = new JFrame("Calculator");

        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    }

    private void createDisplay() {

        displayLabel = new JLabel("0");

        displayLabel.setBackground(BLACK);
        displayLabel.setForeground(Color.WHITE);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 70));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setOpaque(true);

        JPanel displayPanel = new JPanel(new BorderLayout());
        displayPanel.add(displayLabel);

        frame.add(displayPanel, BorderLayout.NORTH);
    }

    private void createButtons() {

        buttonsPanel = new JPanel(new GridLayout(5, 4));
        buttonsPanel.setBackground(BLACK);

        for (String value : buttonValues) {

            JButton button = new JButton(value);

            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setFocusable(false);
            button.setBorder(new LineBorder(BLACK));

            styleButton(button, value);

            button.addActionListener(e -> handleButtonClick(value));

            buttonsPanel.add(button);
        }

        frame.add(buttonsPanel);
    }

    private void styleButton(JButton button, String value) {

        if (Arrays.asList(topSymbols).contains(value)) {
            button.setBackground(LIGHT_GRAY);
            button.setForeground(BLACK);

        } else if (Arrays.asList(rightSymbols).contains(value)) {
            button.setBackground(ORANGE);
            button.setForeground(Color.WHITE);

        } else {
            button.setBackground(DARK_GRAY);
            button.setForeground(Color.WHITE);
        }
    }

    private void handleButtonClick(String value) {

        if ("0123456789".contains(value)) {
            handleNumber(value);

        } else if (value.equals(".")) {
            handleDecimal();

        } else if (Arrays.asList(rightSymbols).contains(value)) {
            handleOperator(value);

        } else {
            handleTopButtons(value);
        }
    }

    private void handleNumber(String value) {

        if (displayLabel.getText().equals("0")) {
            displayLabel.setText(value);

        } else {
            displayLabel.setText(displayLabel.getText() + value);
        }
    }

    private void handleDecimal() {

        if (!displayLabel.getText().contains(".")) {
            displayLabel.setText(displayLabel.getText() + ".");
        }
    }

    private void handleOperator(String value) {

        if (value.equals("=")) {
            calculate();
            return;
        }

        if (value.equals("√")) {
            double number = Double.parseDouble(displayLabel.getText());
            displayLabel.setText(removeZeroDecimal(Math.sqrt(number)));
            return;
        }

        firstNumber = displayLabel.getText();
        operator = value;

        displayLabel.setText("0");
    }

    private void calculate() {

        if (operator.isEmpty()) return;

        double num1 = Double.parseDouble(firstNumber);
        double num2 = Double.parseDouble(displayLabel.getText());

        double result = 0;

        switch (operator) {

            case "+":
                result = num1 + num2;
                break;

            case "-":
                result = num1 - num2;
                break;

            case "×":
                result = num1 * num2;
                break;

            case "÷":

                if (num2 == 0) {
                    displayLabel.setText("Erro");
                    clear();
                    return;
                }

                result = num1 / num2;
                break;
        }

        displayLabel.setText(removeZeroDecimal(result));

        clear();
    }

    private void handleTopButtons(String value) {

        switch (value) {

            case "AC":
                displayLabel.setText("0");
                clear();
                break;

            case "+/-":

                double current = Double.parseDouble(displayLabel.getText());
                displayLabel.setText(removeZeroDecimal(current * -1));
                break;

            case "%":

                double percent = Double.parseDouble(displayLabel.getText());
                displayLabel.setText(removeZeroDecimal(percent / 100));
                break;
        }
    }

    private void clear() {
        firstNumber = "";
        operator = "";
    }

    private String removeZeroDecimal(double number) {

        if (number % 1 == 0) {
            return Integer.toString((int) number);
        }

        return Double.toString(number);
    }

    public static void main(String[] args) {
        new Calculator();
    }
}