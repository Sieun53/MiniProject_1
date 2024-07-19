package app.book.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
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
import app.book.dao.CustomerDao;
import app.book.dto.Customer;

public class CustomerPage extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton searchButton, addButton, editButton, listButton;
    private JTextField searchWordField;
    private CustomerDao customerDao = new CustomerDao();
    private JComboBox<String> sortByComboBox, orderComboBox;

    public CustomerPage() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"고객 ID", "이름", "주소", "전화번호"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        listCustomer(); // 초기 데이터 로드

        Dimension textFieldSize = new Dimension(400, 28);
        searchWordField = new JTextField();
        searchWordField.setPreferredSize(textFieldSize);

        searchButton = new JButton("검색");
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("고객 이름 검색"));
        searchPanel.add(searchWordField);
        searchPanel.add(searchButton);

        sortByComboBox = new JComboBox<>(new String[]{"고객 ID", "이름", "주소", "전화번호"});
        orderComboBox = new JComboBox<>(new String[]{"오름차순", "내림차순"});

        JPanel sortPanel = new JPanel();
        sortPanel.add(new JLabel("정렬 기준"));
        sortPanel.add(sortByComboBox);
        sortPanel.add(new JLabel("정렬 순서"));
        sortPanel.add(orderComboBox);

        addButton = new JButton("등록");
        editButton = new JButton("수정 or 삭제");
        listButton = new JButton("목록");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(listButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(sortPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> {
            String searchWord = searchWordField.getText();
            if (!searchWord.isBlank()) {
                listCustomer(searchWord);
            }
        });

        addButton.addActionListener(e -> {
            AddCustomerDialog addDialog = new AddCustomerDialog(this, tableModel);
            addDialog.setVisible(true);
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                EditCustomerDialog editDialog = new EditCustomerDialog(this, tableModel, selectedRow);
                editDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "고객을 선택하세요.");
            }
        });

        listButton.addActionListener(e -> listCustomer());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        EditCustomerDialog editDialog = new EditCustomerDialog(null, tableModel, selectedRow);
                        editDialog.setVisible(true);
                    }
                }
            }
        });

        sortByComboBox.addActionListener(e -> sortCustomers());
        orderComboBox.addActionListener(e -> sortCustomers());
    }

    private void clearTable() {
        tableModel.setRowCount(0);
    }

    public void listCustomer() {
        clearTable();
        List<Customer> customerList = customerDao.listCustomer();
        for (Customer customer : customerList) {
            tableModel.addRow(new Object[]{customer.getCustId(), customer.getName(), customer.getAddress(), customer.getPhone()});
        }
    }

    public void listCustomer(String searchWord) {
        clearTable();
        List<Customer> customerList = customerDao.listCustomer(searchWord);
        for (Customer customer : customerList) {
            tableModel.addRow(new Object[]{customer.getCustId(), customer.getName(), customer.getAddress(), customer.getPhone()});
        }
    }

    public void refresh() {
        listCustomer();
    }

    Customer detailCustomer(int cusId) {
        return customerDao.detailCustomer(cusId);
    }

    void insertCustomer(Customer customer) {
        int ret = customerDao.insertCustomer(customer);
        if (ret == 1) {
            listCustomer();
        }
    }

    void updateCustomer(Customer customer) {
        int ret = customerDao.updateCustomer(customer);
        if (ret == 1) {
            listCustomer();
        }
    }

    void deleteCustomer(int cusId) {
        int ret = customerDao.deleteCustomer(cusId);
        if (ret == 1) {
            listCustomer();
        }
    }

    private void sortCustomers() {
        String sortBy = (String) sortByComboBox.getSelectedItem();
        String order = (String) orderComboBox.getSelectedItem();
        Comparator<Customer> comparator = null;

        switch (sortBy) {
            case "고객 ID":
                comparator = Comparator.comparing(Customer::getCustId);
                break;
            case "이름":
                comparator = Comparator.comparing(Customer::getName);
                break;
            case "주소":
                comparator = Comparator.comparing(Customer::getAddress);
                break;
            case "전화번호":
                comparator = Comparator.comparing(Customer::getPhone);
                break;
        }

        if (order.equals("내림차순")) {
            comparator = comparator.reversed();
        }

        List<Customer> customerList = customerDao.listCustomer();
        customerList.sort(comparator);

        clearTable();
        for (Customer customer : customerList) {
            tableModel.addRow(new Object[]{customer.getCustId(), customer.getName(), customer.getAddress(), customer.getPhone()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CustomerPage().setVisible(true);
        });
    }
}
