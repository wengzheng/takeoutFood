package cn.edu.zucc.takeoutfood.model;

import java.util.Date;

public class BeanCouponHold{
	private int incouponID;
	private int shopID;
	private int userID;
	private int couponID;
	private double coupondiscount;
	private int couponNumber;
	private Date cdeadline;
	public int getIncouponID() {
		return incouponID;
	}
	public void setIncouponID(int givecouponID) {
		this.incouponID = givecouponID;
	}
	public int getShopID() {
		return shopID;
	}
	public void setShopID(int shopID) {
		this.shopID = shopID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getCouponID() {
		return couponID;
	}
	public void setCouponID(int couponID) {
		this.couponID = couponID;
	}
	public double getCoupondiscount() {
		return coupondiscount;
	}
	public void setCoupondiscount(double coupondiscount) {
		this.coupondiscount = coupondiscount;
	}
	public Date getCdeadline() {
		return cdeadline;
	}
	public void setCdeadline(Date cdeadline) {
		this.cdeadline = cdeadline;
	}
	public int getCouponNumber() {
		return couponNumber;
	}
	public void setCouponNumber(int couponNumber) {
		this.couponNumber = couponNumber;
	}
	
}
