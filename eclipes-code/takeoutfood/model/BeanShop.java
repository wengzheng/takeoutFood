package cn.edu.zucc.takeoutfood.model;

public class BeanShop {
	private int shopNUM;
	private String shopid;
	private String shopname;
	private String shoppwd;
	private int shopstar;
	private float avgconsume;
	private int totalnum;
	public int getShopNUM() {
		return shopNUM;
	}
	public void setShopNUM(int shopID) {
		this.shopNUM = shopID;
	}
	public String getShopid() {
		return shopid;
	}
	public void setShopid(String shopaccount) {
		this.shopid = shopaccount;
	}
	public String getShoppwd() {
		return shoppwd;
	}
	public void setShoppwd(String shoppwd) {
		this.shoppwd = shoppwd;
	}
	public int getShopstar() {
		return shopstar;
	}
	public void setShopstar(int shopstar) {
		this.shopstar = shopstar;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	public float getAvgconsume() {
		return avgconsume;
	}
	public void setAvgconsume(float avgconsume) {
		this.avgconsume = avgconsume;
	}
	public int getTotalnum() {
		return totalnum;
	}
	public void setTotalnum(int totalnum) {
		this.totalnum = totalnum;
	}
}
