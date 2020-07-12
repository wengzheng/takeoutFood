package cn.edu.zucc.takeoutfood.model;

import java.util.List;

public class BeanComodity {
	private int commodityNUM;
	private int commoditytypeNUM;
	private String commodityname;
	private double price;
	private double discount;
	private String evaluate; 
	public int getCommoditytypeNUM() {
		return commoditytypeNUM;
	}
	public void setCommoditytypeNUM(int commoditytypeNUM) {
		this.commoditytypeNUM = commoditytypeNUM;
	}
	public int getCommodityNUM() {
		return commodityNUM;
	}
	public void setCommodityNUM(int commodityNUM) {
		this.commodityNUM = commodityNUM;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price2) {
		this.price =price2;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public String getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
	public String getCommodityname() {
		return commodityname;
	}
	public void setCommodityname(String commodityname) {
		this.commodityname = commodityname;
	}
	
}

