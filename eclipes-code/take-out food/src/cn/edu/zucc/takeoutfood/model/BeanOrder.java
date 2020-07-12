package cn.edu.zucc.takeoutfood.model;

import java.util.Date;

public class BeanOrder {
	private int orderID;
	private int addressID;
	private int riderID;
	private int userID;
	private int shopID;
	private int fullreduceplanID;
	private int couponID;
	private int incouponID;
	private double originalamount;//原价
	private double settlementamount;//优惠价
	private Date downtime;
	private Date requiretime;
	private String orderstate;//订单状态
	private String evaluation;//评价
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public int getAddressID() {
		return addressID;
	}
	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}
	public int getRiderID() {
		return riderID;
	}
	public void setRiderID(int riderID) {
		this.riderID = riderID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getShopID() {
		return shopID;
	}
	public void setShopID(int shopID) {
		this.shopID = shopID;
	}
	public int getFullreduceplanID() {
		return fullreduceplanID;
	}
	public void setFullreduceplanID(int fullreduceplanID) {
		this.fullreduceplanID = fullreduceplanID;
	}
	public int getCouponID() {
		return couponID;
	}
	public void setCouponID(int couponID) {
		this.couponID = couponID;
	}
	public int getIncouponID() {
		return incouponID;
	}
	public void setIncouponID(int incouponID) {
		this.incouponID = incouponID;
	}
	public double getOriginalamount() {
		return originalamount;
	}
	public void setOriginalamount(double originalamount) {
		this.originalamount = originalamount;
	}
	public double getSettlementamount() {
		return settlementamount;
	}
	public void setSettlementamount(double settlementamount) {
		this.settlementamount = settlementamount;
	}
	public Date getDowntime() {
		return downtime;
	}
	public void setDowntime(Date downtime) {
		this.downtime = downtime;
	}
	public Date getRequiretime() {
		return requiretime;
	}
	public void setRequiretime(Date requiretime) {
		this.requiretime = requiretime;
	}
	public String getOrderstate() {
		return orderstate;
	}
	public void setOrderstate(String orderstate) {
		this.orderstate = orderstate;
	}
	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	
}
