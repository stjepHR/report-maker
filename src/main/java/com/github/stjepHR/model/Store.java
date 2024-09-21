package com.github.stjepHR.model;

public class Store {
	
	private String storeCode;
	private String storeName;

	public Store(String storeCode, String storeName) {
		super();
		this.storeCode = storeCode;
		this.storeName = storeName;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	@Override
	public String toString() {
		return "Store [storeCode=" + storeCode + ", storeName=" + storeName + "]";
	}

}
