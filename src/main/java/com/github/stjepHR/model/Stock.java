package com.github.stjepHR.model;

public class Stock {

	private String productCode;
	private String storeCode;
	private Double storeProductQuantity;
	
	public Stock(String productCode, String storeCode, Double storeProductQuantity) {
		super();
		this.productCode = productCode;
		this.storeCode = storeCode;
		this.storeProductQuantity = storeProductQuantity;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public Double getStoreProductQuantity() {
		return storeProductQuantity;
	}
	public void setStoreProductQuantity(Double storeProductQuantity) {
		this.storeProductQuantity = storeProductQuantity;
	}
	@Override
	public String toString() {
		return "Stock [productCode=" + productCode + ", storeCode=" + storeCode + ", storeProductQuantity="
				+ storeProductQuantity + "]";
	}
	
}
