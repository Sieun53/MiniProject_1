

package app.book.ui;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainUI extends JFrame {
    private JPanel customerPanel;
    private JPanel orderPanel;
    private JPanel listPanel;
    private JPanel mainPanel;
    private BookPage bookPage; // 변수 bookPage
    private CustomerPage customerPage;
    private OrderPage orderPage;
    
    public MainUI() {
        setTitle("Book Manager - sieun");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //화면 중앙에 위치

        //메뉴바
        JMenuBar menuBar = new JMenuBar();
        JMenu listMenu = new JMenu("Book List");
        JMenu customerMenu = new JMenu("Customer");
        JMenu orderMenu = new JMenu("Order");
        
        menuBar.add(listMenu);
        menuBar.add(customerMenu);
        menuBar.add(orderMenu);
        setJMenuBar(menuBar);

        
        listPanel = new JPanel();
//        listPanel.add(new JLabel("Book list Panel"));
        customerPanel = new JPanel();
//        customerPanel.add(new JLabel("Customer Panel"));
        orderPanel = new JPanel();
//        orderPanel.add(new JLabel("Order Panel"));


        //  CardLayout
        mainPanel = new JPanel(new CardLayout());
        mainPanel.add(listPanel, "Book List");
        mainPanel.add(customerPanel, "Customer");
        mainPanel.add(orderPanel, "Order");



        add(mainPanel);

        bookPage = new BookPage();
        mainPanel.add(bookPage, "Book Page");
        
        
        customerPage = new CustomerPage();
        mainPanel.add(customerPage, "Customer Page");

        
        orderPage = new OrderPage();
        mainPanel.add(orderPage, "Order Page");
        
        //시작했을 때 book page 뜨게 하기
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, "Book Page");
        
        
        customerMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showPanel("Customer");
            }
        });

        orderMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showPanel("Order");
            }
        });

        listMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showPanel("Book List");
            }
        });

    }
    
    //메뉴바 버튼 누르면 패널 보여줌
    private void showPanel(String panelName) {
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

        if (panelName.equals("Book List")) {
            cardLayout.show(mainPanel, "Book Page");
            bookPage.refresh(); // 데이터를 다시 불러오기 or 화면 갱신
        } else if (panelName.equals("Customer")) {
            cardLayout.show(mainPanel, "Customer Page");
        } 
        else if (panelName.equals("Order")) {
            cardLayout.show(mainPanel, "Order Page");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainUI().setVisible(true);
            }
        });
    }
}
