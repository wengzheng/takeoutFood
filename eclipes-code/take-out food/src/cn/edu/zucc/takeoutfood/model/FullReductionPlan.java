package cn.edu.zucc.takeoutfood.model;

public class FullReductionPlan {
	private int fullreduceplanNUM;
	private int shopNUM;
	private double fullreduceprice;
	private double F_discount;
	private boolean withcoupon;
	public int getFullreduceplanNUM() {
		return fullreduceplanNUM;
	}
	public void setFullreduceplanNUM(int fullreduceplanNUM) {
		this.fullreduceplanNUM = fullreduceplanNUM;
	}
	public int getShopNUM() {
		return shopNUM;
	}
	public void setShopNUM(int shopNUM) {
		this.shopNUM = shopNUM;
	}
	public double getFullreduceprice() {
		return fullreduceprice;
	}
	public void setFullreduceprice(double fullreduceprice) {
		this.fullreduceprice = fullreduceprice;
	}
	public double getF_discount() {
		return F_discount;
	}
	public void setF_discount(double f_discount) {
		F_discount = f_discount;
	}
	public boolean getWithcoupon() {
		return withcoupon;
	}
	public void setWithcoupon(boolean withcoupon) {
		this.withcoupon = withcoupon;
	}
}
