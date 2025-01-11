import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnitConverter extends JFrame {
    private JComboBox<String> unitTypeComboBox;
    private JComboBox<String> fromUnitComboBox;
    private JComboBox<String> toUnitComboBox;
    private JTextField inputField;
    private JLabel resultLabel;

    public UnitConverter() {
        // Set up the main window
        setTitle("Unit Converter");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Unit type selection
        JLabel unitTypeLabel = new JLabel("Select Unit Type:");
        unitTypeComboBox = new JComboBox<>(new String[]{"Length", "Weight", "Temperature"});
        add(unitTypeLabel);
        add(unitTypeComboBox);

        // "From" unit selection
        JLabel fromUnitLabel = new JLabel("From:");
        fromUnitComboBox = new JComboBox<>();
        add(fromUnitLabel);
        add(fromUnitComboBox);

        // "To" unit selection
        JLabel toUnitLabel = new JLabel("To:");
        toUnitComboBox = new JComboBox<>();
        add(toUnitLabel);
        add(toUnitComboBox);

        // Input field for the value to convert
        JLabel inputLabel = new JLabel("Enter Value:");
        inputField = new JTextField();
        add(inputLabel);
        add(inputField);

        // Result label
        JLabel resultTextLabel = new JLabel("Result:");
        resultLabel = new JLabel("-");
        add(resultTextLabel);
        add(resultLabel);

        // Update the "From" and "To" unit options when the unit type changes
        unitTypeComboBox.addActionListener(e -> updateUnitOptions());

        // Perform the conversion when the user enters a value and presses Enter
        inputField.addActionListener(e -> convertUnits());

        // Initialize the unit options
        updateUnitOptions();
    }

    private void updateUnitOptions() {
        String selectedUnitType = (String) unitTypeComboBox.getSelectedItem();
        if (selectedUnitType == null) return;

        fromUnitComboBox.removeAllItems();
        toUnitComboBox.removeAllItems();

        switch (selectedUnitType) {
            case "Length":
                String[] lengthUnits = {"Meters", "Kilometers", "Miles"};
                for (String unit : lengthUnits) {
                    fromUnitComboBox.addItem(unit);
                    toUnitComboBox.addItem(unit);
                }
                break;

            case "Weight":
                String[] weightUnits = {"Kilograms", "Pounds"};
                for (String unit : weightUnits) {
                    fromUnitComboBox.addItem(unit);
                    toUnitComboBox.addItem(unit);
                }
                break;

            case "Temperature":
                String[] temperatureUnits = {"Celsius", "Fahrenheit"};
                for (String unit : temperatureUnits) {
                    fromUnitComboBox.addItem(unit);
                    toUnitComboBox.addItem(unit);
                }
                break;
        }
    }

    private void convertUnits() {
        String fromUnit = (String) fromUnitComboBox.getSelectedItem();
        String toUnit = (String) toUnitComboBox.getSelectedItem();
        String inputText = inputField.getText();

        if (fromUnit == null || toUnit == null || inputText.isEmpty()) {
            resultLabel.setText("Invalid input");
            return;
        }

        try {
            double inputValue = Double.parseDouble(inputText);
            double resultValue = 0;

            switch ((String) unitTypeComboBox.getSelectedItem()) {
                case "Length":
                    resultValue = convertLength(inputValue, fromUnit, toUnit);
                    break;

                case "Weight":
                    resultValue = convertWeight(inputValue, fromUnit, toUnit);
                    break;

                case "Temperature":
                    resultValue = convertTemperature(inputValue, fromUnit, toUnit);
                    break;
            }

            resultLabel.setText(String.format("%.2f", resultValue));
        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid number");
        }
    }

    private double convertLength(double value, String from, String to) {
        // Convert length units
        if (from.equals("Meters")) {
            if (to.equals("Kilometers")) return value / 1000;
            if (to.equals("Miles")) return value * 0.000621371;
        } else if (from.equals("Kilometers")) {
            if (to.equals("Meters")) return value * 1000;
            if (to.equals("Miles")) return value * 0.621371;
        } else if (from.equals("Miles")) {
            if (to.equals("Meters")) return value / 0.000621371;
            if (to.equals("Kilometers")) return value / 0.621371;
        }
        return value;
    }

    private double convertWeight(double value, String from, String to) {
        // Convert weight units
        if (from.equals("Kilograms")) {
            if (to.equals("Pounds")) return value * 2.20462;
        } else if (from.equals("Pounds")) {
            if (to.equals("Kilograms")) return value / 2.20462;
        }
        return value;
    }

    private double convertTemperature(double value, String from, String to) {
        // Convert temperature units
        if (from.equals("Celsius")) {
            if (to.equals("Fahrenheit")) return (value * 9 / 5) + 32;
        } else if (from.equals("Fahrenheit")) {
            if (to.equals("Celsius")) return (value - 32) * 5 / 9;
        }
        return value;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UnitConverter converter = new UnitConverter();
            converter.setVisible(true);
        });
    }
}
