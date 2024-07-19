package app.book.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import app.book.dto.Order;

public class EditOrderDialog extends JDialog {
    private JTextField orderIdField, custIdField, bookIdField, salePriceField, orderDateField;
    private JButton updateButton, deleteButton;

    public EditOrderDialog(OrderPage parent, DefaultTableModel tableModel, int rowIndex) {
        setTitle("주문 수정 or 삭제");
        setSize(300, 250);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent); // 부모에 맞게

        // 선택된 주문의 주문 ID로 주문 테이블에서 조회
        Integer orderId = (Integer) tableModel.getValueAt(rowIndex, 0);
        Order order = parent.detailOrder(orderId);

        // input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        // fields 초기화
        orderIdField = new JTextField(String.valueOf(orderId));
        orderIdField.setEditable(false);
        custIdField = new JTextField(String.valueOf(order.getCustId()));
        bookIdField = new JTextField(String.valueOf(order.getBookId()));
        salePriceField = new JTextField(String.valueOf(order.getSalePrice()));
        orderDateField = new JTextField(String.valueOf(order.getOrderDate()));

        // add fields with labels to inputPanel
        inputPanel.add(new JLabel("Order ID"));
        inputPanel.add(orderIdField);
        inputPanel.add(new JLabel("Customer ID"));
        inputPanel.add(custIdField);
        inputPanel.add(new JLabel("Book ID"));
        inputPanel.add(bookIdField);
        inputPanel.add(new JLabel("Sale Price"));
        inputPanel.add(salePriceField);
        inputPanel.add(new JLabel("Order Date"));
        inputPanel.add(orderDateField);

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
                int custId = Integer.parseInt(custIdField.getText());
                int bookId = Integer.parseInt(bookIdField.getText());
                int salePrice = Integer.parseInt(salePriceField.getText());
                String orderDate = orderDateField.getText();

                Order updatedOrder = new Order(orderId, custId, bookId, salePrice, Date.valueOf(orderDate));
                parent.updateOrder(updatedOrder);
                dispose();
            }
        });

        // ActionListener for deleteButton
        deleteButton.addActionListener(e -> {
            int ret = JOptionPane.showConfirmDialog(this, "삭제할까요?", "삭제 확인", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                parent.deleteOrder(orderId);
                dispose();
            }
        });

        // 다이얼로그를 모달(Modal)로 설정하여 주문 수정 및 삭제 작업이 완료될 때까지 다른 작업을 막습니다.
        setModal(true);
    }
}
