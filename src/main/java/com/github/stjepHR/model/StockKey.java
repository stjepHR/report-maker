package com.github.stjepHR.model;

import java.util.Objects;

public class StockKey {
	
	private String productCode;
	private String storeCode;
	
	public StockKey(String articleCode, String storeCode) {
		super();
		this.productCode = articleCode;
		this.storeCode = storeCode;
	}
	public String getArticleCode() {
		return productCode;
	}
	public void setArticleCode(String articleCode) {
		this.productCode = articleCode;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	@Override
	public String toString() {
		return "StockKey [productCode=" + productCode + ", storeCode=" + storeCode + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.productCode, this.storeCode);
	}
	
	@Override
	public boolean equals(Object object) {
		if (this == object) return Boolean.TRUE;
		if ((object == null) || (getClass() != object.getClass())) return false;
		StockKey stockKey = (StockKey) object;
		return Objects.equals(this.productCode, stockKey.productCode);
	}
	
}
