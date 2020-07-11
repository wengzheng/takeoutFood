package cn.edu.zucc.takeoutfood.model;

import java.util.List;

public class BeanComodity {
	private int commodityNUM;
	private int commoditytypeNUM;
	private String commodityname;
	private float price;
	private float discount;
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
	public float getPrice() {
		return price;
	}
	public void setPrice(float price2) {
		this.price =price2;
	}
	public float getDiscount() {
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

