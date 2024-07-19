package app.book.dto;

import java.sql.Date;
//import java.time.LocalDate;

public class Order{
	private int orderId;
	private int custId;
	private int bookId;
	private int salePrice;
	private Date orderDate;
	
	
	public Order() {}
	public Order(int orderId, int custId, int bookId, int salePrice, Date orderDate) {
		super();
		this.orderId = orderId;
		this.custId = custId;
		this.bookId = bookId;
		this.salePrice = salePrice;
		this.orderDate = orderDate;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", custId=" + custId + ", bookId=" + bookId + ", salePrice=" + salePrice
				+ ", orderDate=" + orderDate + "]";
	}
	
}
