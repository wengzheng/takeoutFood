package cn.edu.zucc.takeoutfood.model;

public class BeanSystemUser {
	private String SystemUserid;
	private String pwd;
	private String SystemUsername;
	private int stype;
	private int systemNUM;
	public String getSystemUserid() {
		return SystemUserid;
	}
	public void setSystemUserid(String SystemUserid) {
		this.SystemUserid=SystemUserid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getSystemUsername() {
		return SystemUsername;
	}
	public void setSystemUsername(String systemUsername) {
		SystemUsername = systemUsername;
	}
	public int getStype() {
		return stype;
	}
	public void setStype(int stype) {
		this.stype = stype;
	}
	public int getSystemNUM() {
		return systemNUM;
	}
	public void setSystemNUM(int systemNUM) {
		this.systemNUM = systemNUM;
	}
	
}
