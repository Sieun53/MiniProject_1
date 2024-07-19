package app.book.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import app.book.dao.OrderDao;
import app.book.dto.Order;

public class OrderPage extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton searchButton, addButton, editButton, listButton;
    private JTextField searchWordField;
    private JComboBox<String> searchComboBox; // 추가된 검색 조건 선택 콤보박스
    private OrderDao orderDao = new OrderDao();

    public OrderPage() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"Order ID", "Customer ID", "Book ID", "Sale Price", "Order Date"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        listOrders(); // Initial data load

        Dimension textFieldSize = new Dimension(400, 28);
        searchWordField = new JTextField();
        searchWordField.setPreferredSize(textFieldSize);

        searchComboBox = new JComboBox<>(new String[]{"Order ID", "Customer ID", "Book ID"});
        searchComboBox.setSelectedIndex(0); // 콤보박스 초기 선택: Order ID

        searchButton = new JButton("검색");
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("검색 조건")); //콤보박스 추가
        searchPanel.add(searchComboBox);
        searchPanel.add(new JLabel("검색어"));
        searchPanel.add(searchWordField);
        searchPanel.add(searchButton);


        addButton = new JButton("등록");
        editButton = new JButton("수정 or 삭제");
        listButton = new JButton("목록");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(listButton);

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        //콤보박스
        searchButton.addActionListener(e -> {
            String searchWord = searchWordField.getText();
            if (!searchWord.isBlank()) {
                String selectedSearchCriteria = (String) searchComboBox.getSelectedItem();
                switch (selectedSearchCriteria) {
                    case "Order ID":
                        listOrders(searchWord);
                        break;
                    case "Customer ID":
                        listOrdersByCustomer(searchWord);
                        break;
                    case "Book ID":
                        listOrdersByBook(searchWord);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "잘못된 검색 조건입니다.");
                        break;
                }
            }
        });

        addButton.addActionListener(e -> {
            AddOrderDialog addDialog = new AddOrderDialog(this, tableModel);
            addDialog.setVisible(true);
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                EditOrderDialog editDialog = new EditOrderDialog(this, tableModel, selectedRow);
                editDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "주문을 선택하세요");
            }
        });

        listButton.addActionListener(e -> listOrders());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        EditOrderDialog editDialog = new EditOrderDialog(null, tableModel, selectedRow);
                        editDialog.setVisible(true);
                    }
                }
            }
        });
    }

    private void clearTable() {
        tableModel.setRowCount(0);
    }

    public void listOrders() {
        clearTable();
        List<Order> orderList = orderDao.listOrders();
        for (Order order : orderList) {
            tableModel.addRow(new Object[]{order.getOrderId(), order.getCustId(), order.getBookId(), order.getSalePrice(), order.getOrderDate()});
        }
    }

    public void listOrders(String searchWord) {
        clearTable();
        List<Order> orderList = orderDao.searchOrders(searchWord);
        for (Order order : orderList) {
            tableModel.addRow(new Object[]{order.getOrderId(), order.getCustId(), order.getBookId(), order.getSalePrice(), order.getOrderDate()});
        }
    }

    public void listOrdersByCustomer(String customerId) {
        clearTable();
        List<Order> orderList = orderDao.searchOrdersByCustomer(customerId);
        for (Order order : orderList) {
            tableModel.addRow(new Object[]{order.getOrderId(), order.getCustId(), order.getBookId(), order.getSalePrice(), order.getOrderDate()});
        }
    }

    public void listOrdersByBook(String bookId) {
        clearTable();
        List<Order> orderList = orderDao.searchOrdersByBook(bookId);
        for (Order order : orderList) {
            tableModel.addRow(new Object[]{order.getOrderId(), order.getCustId(), order.getBookId(), order.getSalePrice(), order.getOrderDate()});
        }
    }

    public void refresh() {
        listOrders(); // Refreshes by listing all orders again
    }

    Order detailOrder(int orderId) {
        return orderDao.detailOrder(orderId);
    }

    void insertOrder(Order order) {
        int ret = orderDao.insertOrder(order);
        if (ret == 1) {
            listOrders();
        }
    }

    void updateOrder(Order order) {
        int ret = orderDao.updateOrder(order);
        if (ret == 1) {
            listOrders();
        }
    }

    void deleteOrder(int orderId) {
        int ret = orderDao.deleteOrder(orderId);
        if (ret == 1) {
            listOrders();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new OrderPage().setVisible(true);
        });
    }
}
