package cn.edu.zucc.takeoutfood.model;

import java.util.Date;

public class BeanCoupon {
	private int couponNUM;
	private int shopNUM;
	private double C_discount;
	private Date C_starttime;
	private Date C_endtime;
	private int requarrenum;
	public int getCouponNUM() {
		return couponNUM;
	}
	public void setCouponNUM(int couponNUM) {
		this.couponNUM = couponNUM;
	}
	public int getShopNUM() {
		return shopNUM;
	}
	public void setShopNUM(int shopNUM) {
		this.shopNUM = shopNUM;
	}
	public double getC_discount() {
		return C_discount;
	}
	public void setC_discount(double d) {
		C_discount = d;
	}
	public Date getC_starttime() {
		return C_starttime;
	}
	public void setC_starttime(Date c_starttime) {
		C_starttime = c_starttime;
	}
	public Date getC_endtime() {
		return C_endtime;
	}
	public void setC_endtime(Date c_endtime) {
		C_endtime = c_endtime;
	}
	public int getRequarrenum() {
		return requarrenum;
	}
	public void setRequarrenum(int requarrenum) {
		this.requarrenum = requarrenum;
	}
	
}
