package app.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import app.book.common.DBManager;
import app.book.dto.Customer;

// customer table에 대한 CRUD
public class CustomerDao {
    public int insertCustomer(Customer customer) {
        int ret = -1;
        String sql = "insert into customer values (?, ?, ?, ?);";
        
        Connection con = null;
        PreparedStatement pstmt = null;
        
        try {
            con = DBManager.getConnection();            
            pstmt = con.prepareStatement(sql);
            
            pstmt.setInt(1, customer.getCustId());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getAddress());
            pstmt.setString(4, customer.getPhone());
            
            ret = pstmt.executeUpdate();
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }
        
        return ret;
    }
    
    public int updateCustomer(Customer customer) {
        int ret = -1;       
        String sql = "update customer set name = ?, address = ?, phone = ? where custid = ?;";
        
        Connection con = null;
        PreparedStatement pstmt = null;
        
        try {
            con = DBManager.getConnection();            
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getAddress());
            pstmt.setString(3, customer.getPhone());
            pstmt.setInt(4, customer.getCustId());
            
            ret = pstmt.executeUpdate();
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }
        
        return ret;
    }
    
    public int deleteCustomer(int custId) {
        int ret = -1;
        String sql = "delete from customer where custid = ?;";
        
        Connection con = null;
        PreparedStatement pstmt = null;
        
        try {
            con = DBManager.getConnection();            
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, custId);
            
            ret = pstmt.executeUpdate();
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }
        
        return ret;
    }
    
    public List<Customer> listCustomer() {
        List<Customer> list = new ArrayList<>();
        
        String sql = "select * from customer;";
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            con = DBManager.getConnection();            
            pstmt = con.prepareStatement(sql);
            
            rs = pstmt.executeQuery();
            while(rs.next()) {
                Customer customer = new Customer();
                customer.setCustId(rs.getInt("custid"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setPhone(rs.getString("phone"));
                list.add(customer);
            }
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }
        
        return list;
    }
    
    public Customer detailCustomer(int custId) {
        Customer customer = null;
        
        String sql = "select * from customer where custid = ?;";
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            con = DBManager.getConnection();            
            pstmt = con.prepareStatement(sql);
            
            pstmt.setInt(1, custId);
            
            rs = pstmt.executeQuery();
            if(rs.next()) {
                customer = new Customer();
                customer.setCustId(rs.getInt("custid"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setPhone(rs.getString("phone"));
            }
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }
        
        return customer;
    }
    
    // 검색
    public List<Customer> listCustomer(String searchWord) {
        List<Customer> list = new ArrayList<>();
        
        String sql = "select * from customer where name like ?;";
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            con = DBManager.getConnection();            
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + searchWord + "%");
            
            rs = pstmt.executeQuery();
            while(rs.next()) {
                Customer customer = new Customer();
                customer.setCustId(rs.getInt("custid"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setPhone(rs.getString("phone"));
                list.add(customer);
            }
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }
        
        return list;
    }
}
