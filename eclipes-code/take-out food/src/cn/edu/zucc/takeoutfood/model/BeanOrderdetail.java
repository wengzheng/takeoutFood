package cn.edu.zucc.takeoutfood.model;

public class BeanOrderdetail {
	private int ordercommodityID;
	private int commodityID;
	private int orderID;
	private int cnum;
	private double price;
	private double cdiscount;
	public int getOrdercommodityID() {
		return ordercommodityID;
	}
	public void setOrdercommodityID(int ordercommodityID) {
		this.ordercommodityID = ordercommodityID;
	}
	public int getCommodityID() {
		return commodityID;
	}
	public void setCommodityID(int commodityID) {
		this.commodityID = commodityID;
	}
	public int getCnum() {
		return cnum;
	}
	public void setCnum(int cnum) {
		this.cnum = cnum;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getCdiscount() {
		return cdiscount;
	}
	public void setCdiscount(double cdiscount) {
		this.cdiscount = cdiscount;
	}
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
}
