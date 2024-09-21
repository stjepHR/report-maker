package com.github.stjepHR.model;

public class ExchangeRate {

	//private String currencyCodeNumerical;
	//private String currencyCodeAlphanumerical;
	private String currencyCode;
	private Double rateBuying;
	private Double rateSelling;
	private Double rateMiddle;
	
	public ExchangeRate(String currencyCode, Double rateBuying,
			Double rateMiddle, Double rateSelling) {
		super();
		this.currencyCode = currencyCode;
		this.rateBuying = rateBuying;
		this.rateMiddle = rateMiddle;
		this.rateSelling = rateSelling;
	}
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public Double getRateBuying() {
		return rateBuying;
	}
	public void setRateBuying(Double rateBuying) {
		this.rateBuying = rateBuying;
	}
	public Double getRateSelling() {
		return rateSelling;
	}
	public void setRateSelling(Double rateSelling) {
		this.rateSelling = rateSelling;
	}
	public Double getRateMiddle() {
		return rateMiddle;
	}
	public void setRateMiddle(Double rateMiddle) {
		this.rateMiddle = rateMiddle;
	}
	
}
