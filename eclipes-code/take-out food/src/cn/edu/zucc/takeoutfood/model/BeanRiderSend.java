package cn.edu.zucc.takeoutfood.model;

import java.util.Date;

public class BeanRiderSend {
	private int sendID;
	private int orderID;
	private int riderID;
	private Date sendtime;
	private double income;
	public int getSendID() {
		return sendID;
	}
	public void setSendID(int sendID) {
		this.sendID = sendID;
	}
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public int getRiderID() {
		return riderID;
	}
	public void setRiderID(int riderID) {
		this.riderID = riderID;
	}
	public Date getSendtime() {
		return sendtime;
	}
	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
}
