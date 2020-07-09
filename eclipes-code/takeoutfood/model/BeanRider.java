package cn.edu.zucc.takeoutfood.model;

import java.util.Date;

public class BeanRider {
	private int riderID;
	private String riderid;
	private String ridername;
	private String riderpwd;
	private Date rstarttime;
	private String rIdtpye;
	public int getRiderID() {
		return riderID;
	}
	public void setRiderID(int riderID) {
		this.riderID = riderID;
	}
	public String getRiderid() {
		return riderid;
	}
	public void setRiderid(String rideraccount) {
		this.riderid = rideraccount;
	}
	public String getRiderpwd() {
		return riderpwd;
	}
	public void setRiderpwd(String riderpwd) {
		this.riderpwd = riderpwd;
	}
	public Date getRstarttime() {
		return rstarttime;
	}
	public void setRstarttime(Date rstarttime) {
		this.rstarttime = rstarttime;
	}
	public String getrIdtpye() {
		return rIdtpye;
	}
	public void setrIdtpye(String rIdtpye) {
		this.rIdtpye = rIdtpye;
	}
	public String getRidername() {
		return ridername;
	}
	public void setRidername(String ridername) {
		this.ridername = ridername;
	}
}
