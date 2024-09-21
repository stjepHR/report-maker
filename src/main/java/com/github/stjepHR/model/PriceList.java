package com.github.stjepHR.model;

public class PriceList {

	private String productCode;
	private Double productPrice;
	
	public PriceList(String productCode, Double productPrice) {
		super();
		this.productCode = productCode;
		this.productPrice = productPrice;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}
	@Override
	public String toString() {
		return "PriceList [productCode=" + productCode + ", productPrice=" + productPrice + "]";
	}
	
}
