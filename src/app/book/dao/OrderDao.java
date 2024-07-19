package app.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import app.book.common.DBManager;
import app.book.dto.Order;

public class OrderDao {
    
    public int insertOrder(Order order) {
        int ret = -1;
        String sql = "INSERT INTO orders (orderid, custid, bookid, saleprice, orderdate) VALUES (?, ?, ?, ?, ?);";
        
        Connection con = null;
        PreparedStatement pstmt = null;
        
        try {
            con = DBManager.getConnection();            
            pstmt = con.prepareStatement(sql);
            
            pstmt.setInt(1, order.getOrderId());
            pstmt.setInt(2, order.getCustId());
            pstmt.setInt(3, order.getBookId());
            pstmt.setInt(4, order.getSalePrice());
            pstmt.setDate(5, order.getOrderDate());
            
            ret = pstmt.executeUpdate();
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }
        
        return ret;
    }
    // 기존 주문을 업데이트
    public int updateOrder(Order order) {
        int ret = -1;
        String sql = "UPDATE orders SET custid = ?, bookid = ?, saleprice = ?, orderdate = ? WHERE orderid = ?;";
        
        Connection con = null;
        PreparedStatement pstmt = null;
        
        try {
            con = DBManager.getConnection();            
            pstmt = con.prepareStatement(sql);
            
            pstmt.setInt(1, order.getCustId());
            pstmt.setInt(2, order.getBookId());
            pstmt.setInt(3, order.getSalePrice());
            pstmt.setDate(4, order.getOrderDate());
            pstmt.setInt(5, order.getOrderId());
            
            ret = pstmt.executeUpdate();
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }
        
        return ret;
    }
    
    // 주문을 삭제
    public int deleteOrder(int orderId) {
        int ret = -1;
        String sql = "DELETE FROM orders WHERE orderid = ?;";
        
        Connection con = null;
        PreparedStatement pstmt = null;
        
        try {
            con = DBManager.getConnection();            
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, orderId);
            
            ret = pstmt.executeUpdate();
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }
        
        return ret;
    }
    // 모든 주문을 리스트로 반환
    public List<Order> listOrders() {
        List<Order> list = new ArrayList<>();
        
        String sql = "SELECT * FROM orders;";
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            con = DBManager.getConnection();            
            pstmt = con.prepareStatement(sql);
            
            rs = pstmt.executeQuery();
            while(rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("orderid"));
                order.setCustId(rs.getInt("custid"));
                order.setBookId(rs.getInt("bookid"));
                order.setSalePrice(rs.getInt("saleprice"));
                order.setOrderDate(rs.getDate("orderdate"));
                list.add(order);
            }
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }
        
        return list;
    }
    // 특정 주문의 상세 정보를 반환
    public Order detailOrder(int orderId) {
        Order order = null;
        
        String sql = "SELECT * FROM orders WHERE orderid = ?;";
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            con = DBManager.getConnection();            
            pstmt = con.prepareStatement(sql);
            
            pstmt.setInt(1, orderId);
            
            rs = pstmt.executeQuery();
            if(rs.next()) {
                order = new Order();
                order.setOrderId(rs.getInt("orderid"));
                order.setCustId(rs.getInt("custid"));
                order.setBookId(rs.getInt("bookid"));
                order.setSalePrice(rs.getInt("saleprice"));
                order.setOrderDate(rs.getDate("orderdate"));
            }
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }
        
        return order;
    }
    //order id로 주문을 검색
    public List<Order> searchOrders(String searchWord) {
        List<Order> list = new ArrayList<>();
        
        String sql = "SELECT * FROM orders WHERE orderid LIKE ?;";
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            con = DBManager.getConnection();            
            pstmt = con.prepareStatement(sql);
            
            int orderId = Integer.parseInt(searchWord);
            pstmt.setInt(1, orderId);
            
            rs = pstmt.executeQuery();
            while(rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("orderid"));
                order.setCustId(rs.getInt("custid"));
                order.setBookId(rs.getInt("bookid"));
                order.setSalePrice(rs.getInt("saleprice"));
                order.setOrderDate(rs.getDate("orderdate"));
                list.add(order);
            }
            
        } catch(SQLException | NumberFormatException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }
        
        return list;
    }
    // cust id로 주문을 검색
    public List<Order> searchOrdersByCustomer(String customerId) {
        List<Order> list = new ArrayList<>();
        
        String sql = "SELECT * FROM orders WHERE custid = ?;";
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            con = DBManager.getConnection();            
            pstmt = con.prepareStatement(sql);
            
            int custId = Integer.parseInt(customerId);
            pstmt.setInt(1, custId);
            
            rs = pstmt.executeQuery();
            while(rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("orderid"));
                order.setCustId(rs.getInt("custid"));
                order.setBookId(rs.getInt("bookid"));
                order.setSalePrice(rs.getInt("saleprice"));
                order.setOrderDate(rs.getDate("orderdate"));
                list.add(order);
            }
            
        } catch(SQLException | NumberFormatException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }
        
        return list;
    }
    // book id로 주문
    public List<Order> searchOrdersByBook(String bookId) {
        List<Order> list = new ArrayList<>();
        
        String sql = "SELECT * FROM orders WHERE bookid = ?;";
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            con = DBManager.getConnection();            
            pstmt = con.prepareStatement(sql);
            
            int bId = Integer.parseInt(bookId);
            pstmt.setInt(1, bId);
            
            rs = pstmt.executeQuery();
            while(rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("orderid"));
                order.setCustId(rs.getInt("custid"));
                order.setBookId(rs.getInt("bookid"));
                order.setSalePrice(rs.getInt("saleprice"));
                order.setOrderDate(rs.getDate("orderdate"));
                list.add(order);
            }
            
        } catch(SQLException | NumberFormatException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }
        
        return list;
    }

}
