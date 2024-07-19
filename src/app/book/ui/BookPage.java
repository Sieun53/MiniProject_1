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
import app.book.dao.BookDao;
import app.book.dto.Book;
import java.util.Comparator;

public class BookPage extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton searchButton, addButton, editButton, listButton;
    private JTextField searchWordField;
    private BookDao bookDao = new BookDao();
    private JComboBox<String> sortByComboBox, orderComboBox;

    public BookPage() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"Book ID", "Book Name", "Publisher", "Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        listBook(); // 초기 데이터 로드

        Dimension textFieldSize = new Dimension(400, 28);
        searchWordField = new JTextField();
        searchWordField.setPreferredSize(textFieldSize);

        searchButton = new JButton("검색");
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("책 제목 검색"));
        searchPanel.add(searchWordField);
        searchPanel.add(searchButton);

        // 정렬을 위한 콤보박스 
        sortByComboBox = new JComboBox<>(new String[]{"Book ID", "Book Name", "Publisher", "Price"});
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
                listBook(searchWord);
            }
        });

        addButton.addActionListener(e -> {
            AddBookDialog addDialog = new AddBookDialog(this, this.tableModel);
            addDialog.setVisible(true);
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                EditBookDialog editDialog = new EditBookDialog(this, this.tableModel, selectedRow);
                editDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "도서를 선택하세요.");
            }
        });

        listButton.addActionListener(e -> listBook());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        EditBookDialog editDialog = new EditBookDialog(BookPage.this, tableModel, selectedRow);
                        editDialog.setVisible(true);
                    }
                }
            }
        });

        sortByComboBox.addActionListener(e -> sortBooks());
        orderComboBox.addActionListener(e -> sortBooks());
    }

    private void clearTable() {
        tableModel.setRowCount(0);
    }

    public void listBook() {
        clearTable();
        List<Book> bookList = bookDao.listBook();
        for (Book book : bookList) {
            tableModel.addRow(new Object[]{book.getBookId(), book.getBookName(), book.getPublisher(), book.getPrice()});
        }
    }

    public void listBook(String searchWord) {
        clearTable();
        List<Book> bookList = bookDao.listBook(searchWord);
        for (Book book : bookList) {
            tableModel.addRow(new Object[]{book.getBookId(), book.getBookName(), book.getPublisher(), book.getPrice()});
        }
    }

    public void refresh() {
        listBook();
    }

    Book detailBook(int bookId) {
        return bookDao.detailBook(bookId);
    }

    void insertBook(Book book) {
        int ret = bookDao.insertBook(book);
        if (ret == 1) {
            listBook();
        }
    }

    void updateBook(Book book) {
        int ret = bookDao.updateBook(book);
        if (ret == 1) {
            listBook();
        }
    }

    void deleteBook(int bookId) {
        int ret = bookDao.deleteBook(bookId);
        if (ret == 1) {
            listBook();
        }
    }

    //정렬
    private void sortBooks() {
        String sortBy = (String) sortByComboBox.getSelectedItem();
        String order = (String) orderComboBox.getSelectedItem();
        Comparator<Book> comparator = null;

        switch (sortBy) {
            case "Book ID":
                comparator = Comparator.comparing(Book::getBookId);
                break;
            case "Book Name":
                comparator = Comparator.comparing(Book::getBookName);
                break;
            case "Publisher":
                comparator = Comparator.comparing(Book::getPublisher);
                break;
            case "Price":
                comparator = Comparator.comparing(Book::getPrice);
                break;
        }

        if (order.equals("내림차순")) {
            comparator = comparator.reversed();
        }

        List<Book> bookList = bookDao.listBook();
        bookList.sort(comparator);

        clearTable();
        for (Book book : bookList) {
            tableModel.addRow(new Object[]{book.getBookId(), book.getBookName(), book.getPublisher(), book.getPrice()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BookPage().setVisible(true);
        });
    }
}
