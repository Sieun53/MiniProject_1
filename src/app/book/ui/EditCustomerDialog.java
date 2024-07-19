package app.book.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import app.book.dto.Customer;

public class EditCustomerDialog extends JDialog {
    private JTextField custIdField, nameField, addressField, phoneField;
    private JButton updateButton, deleteButton;

    public EditCustomerDialog(CustomerPage parent, DefaultTableModel tableModel, int rowIndex) {
        setTitle("고객 수정 or 삭제");
        setSize(300, 200);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent); // 부모에 맞게

        // 선택된 고객의 고객 ID로 고객 테이블에서 조회
        Integer custId = (Integer) tableModel.getValueAt(rowIndex, 0);
        Customer customer = parent.detailCustomer(custId);

        // input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        // fields
        custIdField = new JTextField(String.valueOf(custId));
        custIdField.setEditable(false);
        nameField = new JTextField(customer.getName());
        addressField = new JTextField(customer.getAddress());
        phoneField = new JTextField(customer.getPhone());

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

        // buttons
        updateButton = new JButton("수정");
        deleteButton = new JButton("삭제");
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // add inputPanel, buttonPanel to Dialog
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // ActionListener for updateButton
        updateButton.addActionListener(e -> {
            int ret = JOptionPane.showConfirmDialog(this, "수정할까요?", "수정 확인", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                String name = nameField.getText();
                String address = addressField.getText();
                String phone = phoneField.getText();

                parent.updateCustomer(new Customer(custId, name, address, phone));
                dispose();
            }
        });

        // ActionListener for deleteButton
        deleteButton.addActionListener(e -> {
            int ret = JOptionPane.showConfirmDialog(this, "삭제할까요?", "삭제 확인", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                parent.deleteCustomer(custId);
                dispose();
            }
        });
    }
}
