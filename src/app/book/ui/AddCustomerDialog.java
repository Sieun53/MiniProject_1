package app.book.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import app.book.dto.Customer;

public class AddCustomerDialog extends JDialog {
    private JTextField custIdField, nameField, addressField, phoneField;
    private JButton addButton;

    public AddCustomerDialog(CustomerPage parent, DefaultTableModel tableModel) {
        setTitle("고객 추가");
        setSize(300, 200);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent); // 부모에 맞게

        // input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        // fields
        custIdField = new JTextField();
        nameField = new JTextField();
        addressField = new JTextField();
        phoneField = new JTextField();

        // add fields with labels to inputPanel
        inputPanel.add(new JLabel("Customer ID"));
        inputPanel.add(custIdField);
        inputPanel.add(new JLabel("Name"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Address"));
        inputPanel.add(addressField);
        inputPanel.add(new JLabel("Phone"));
        inputPanel.add(phoneField);

        // button panel
        JPanel buttonPanel = new JPanel();

        // add button
        addButton = new JButton("Add");

        buttonPanel.add(addButton);

        // add inputPanel, buttonPanel to Dialog
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // add ActionListener for addButton
        addButton.addActionListener(e -> {
            int custId = Integer.parseInt(custIdField.getText());
            String name = nameField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();

            parent.insertCustomer(new Customer(custId, name, address, phone));

            dispose(); // 다이얼로그 닫기
        });
    }
}