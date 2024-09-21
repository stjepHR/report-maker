package com.github.stjepHR.model;

public class Product {
	
	private String productCode;
	private String productName;
	private String productUnit;
	
	public Product(String productCode, String productName, String productUnit) {
		super();
		this.productCode = productCode;
		this.productName = productName;
		this.productUnit = productUnit;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductUnit() {
		return productUnit;
	}
	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}
	@Override
	public String toString() {
		return "Product [productCode=" + productCode + ", productName=" + productName + ", productUnit=" + productUnit
				+ "]";
	}
	
}
