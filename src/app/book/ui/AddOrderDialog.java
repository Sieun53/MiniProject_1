package app.book.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import app.book.dto.Order;

public class AddOrderDialog extends JDialog {
    private JTextField orderIdField, custIdField, bookIdField, salePriceField, orderDateField;
    private JButton addButton;

    public AddOrderDialog(OrderPage parent, DefaultTableModel tableModel) {
        setTitle("주문 추가");
        setSize(300, 250);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent); // 부모에 맞춰 다이얼로그 위치 설정

        // 입력 패널 설정
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        // 각 필드 초기화
        orderIdField = new JTextField();
        custIdField = new JTextField();
        bookIdField = new JTextField();
        salePriceField = new JTextField();
        orderDateField = new JTextField();

        // 라벨과 필드를 입력 패널에 추가
        
        
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
        
        // 버튼 패널 설정
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("추가");
        buttonPanel.add(addButton);

        // 입력 패널과 버튼 패널을 다이얼로그에 추가
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // 추가 버튼에 ActionListener 추가
        addButton.addActionListener(e -> {
            // 각 필드에서 입력된 값 가져오기
            int orderId = Integer.parseInt(orderIdField.getText());
            int custId = Integer.parseInt(custIdField.getText());
            int bookId = Integer.parseInt(bookIdField.getText());
            int salePrice = Integer.parseInt(salePriceField.getText());
            String orderDate = orderDateField.getText();

            // Order 객체 생성
            Order order = new Order(orderId, custId, bookId, salePrice, Date.valueOf(orderDate));

            // 부모 OrderPage의 insertOrder 메서드 호출하여 주문 추가
            parent.insertOrder(order);

            // 다이얼로그 닫기
            dispose();
        });

        // 다이얼로그를 모달(Modal)로 설정하여 주문 추가 작업이 완료될 때까지 다른 작업을 막습니다.
        setModal(true);
    }
}
