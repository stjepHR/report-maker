package com.github.stjepHR.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.stjepHR.model.PriceList;
import com.github.stjepHR.model.Product;
import com.github.stjepHR.model.Stock;
import com.github.stjepHR.model.StockKey;
import com.github.stjepHR.model.Store;
import com.github.stjepHR.utils.DataUtils;

/**
 * Centralized storage class which instance is intended to hold the HashMaps as input data structures.
 */
public class DataStorage {

	private static final Logger logger = LoggerFactory.getLogger(DataStorage.class);
	
	private Map<String, Product> productMap;
	private Map<String, PriceList> priceListMap;
	private Map<String, Store> storeMap;
	private Map<String, List<Double>> productStockMap;
	private Map<String, List<String>> storeProductMap;
	private Map<StockKey, Stock> stockMap;
	public Map<String, Product> getProductMap() {
		return productMap;
	}
	public void setProductMap(Map<String, Product> productMap) {
		this.productMap = productMap;
	}
	public Map<String, PriceList> getPriceListMap() {
		return priceListMap;
	}
	public void setPriceListMap(Map<String, PriceList> priceListMap) {
		this.priceListMap = priceListMap;
	}
	public Map<String, Store> getStoreMap() {
		return storeMap;
	}
	public void setStoreMap(Map<String, Store> storeMap) {
		this.storeMap = storeMap;
	}
	public Map<String, List<Double>> getProductStockMap() {
		return productStockMap;
	}
	public void setProductStockMap(Map<String, List<Double>> productStockMap) {
		this.productStockMap = productStockMap;
	}
	public Map<String, List<String>> getStoreProductMap() {
		return storeProductMap;
	}
	public void setStoreProductMap(Map<String, List<String>> storeProductMap) {
		this.storeProductMap = storeProductMap;
	}
	public Map<StockKey, Stock> getStockMap() {
		return stockMap;
	}
	public void setStockMap(Map<StockKey, Stock> stockMap) {
		this.stockMap = stockMap;
	}
	
	/**
	 * Method generates Product HashMap from input text rows.
	 * HashMap is set to DataStorage.
	 * @param lines Text lines from input products file. 
	 */
	public void generateProductMap(List<String> lines) {
		Map<String, Product> productMap = new HashMap<>();
		for (String line : lines) {
			String[] parts = line.split("\\|");
			String productCode = parts[0];
			String productName = parts[1];
			String productUnit = parts[2];
			Product product = new Product(productCode, productName, productUnit);
			productMap.put(productCode, product);
		}
		logger.debug("Generated product map, size " + productMap.size());
		productMap = DataUtils.sortHashMap(productMap);
		setProductMap(productMap);
	}

	/**
	 * Method generates PriceList HashMap from input text rows.
	 * HashMap is set to DataStorage.
	 * @param lines Text lines from input prices file. 
	 */
	public void generatePriceListMap(List<String> lines) {
		Map<String, PriceList> priceListMap = new HashMap<>();
		for (String line : lines) {
			String[] parts = line.split("\\|");
			String productCode = parts[0];
			Double productPrice = Double.valueOf(parts[1].replace(',', '.'));
			PriceList priceList = new PriceList(productCode, productPrice);
			priceListMap.put(productCode, priceList);
		}
		logger.debug("Generated price list map, size " + priceListMap.size());
		setPriceListMap(priceListMap);
	}
	
	/**
	 * Method generates Store HashMap from input text rows.
	 * HashMap is set to DataStorage.
	 * @param lines Text lines from input stores file. 
	 */
	public void generateStoreMap(List<String> lines) {
		Map<String, Store> storeMap = new HashMap<>();
		for (String line : lines) {
			String[] parts = line.split("\\|");
			String storeCode = parts[0];
			String storeName = parts[1];
			Store store = new Store(storeCode, storeName);
			storeMap.put(storeCode, store);
		}
		logger.debug("Generated store map, size " + storeMap.size());
		storeMap = DataUtils.sortHashMap(storeMap);
		setStoreMap(storeMap);
	}
	
	/**
	 * Method generates Stock related HashMaps from input text rows.
	 * HashMaps are set to DataStorage.
	 * @param lines Text lines from input stocks file. 
	 */
	public void generateStockMaps(List<String> lines) {
		Map<String, List<Double>> productStockMap = new HashMap<>();
		Map<String, List<String>> storeProductMap = new HashMap<>();
		Map<StockKey, Stock> stockMap = new HashMap<>();
		for (String line : lines) {
			String[] parts = line.split("\\|");
			String productCode = parts[0];
			String storeCode = parts[1];
			Double storeProductQuantity = Double.valueOf(parts[2].replace(',', '.'));

			List<Double> currentStock;
			currentStock = productStockMap.get(productCode);
			if (currentStock != null) {
				currentStock.add(storeProductQuantity);
				productStockMap.put(productCode, currentStock);
			} else {
				productStockMap.put(productCode, new ArrayList<Double>(Arrays.asList(storeProductQuantity)));
			}
			
			List<String> currentProducts;
			currentProducts = storeProductMap.get(storeCode);
			if (currentProducts != null) {
				currentProducts.add(productCode);
				storeProductMap.put(storeCode, currentProducts);
			} else {
				storeProductMap.put(storeCode, new ArrayList<String>(Arrays.asList(productCode)));
			}
			
			StockKey stockKey = new StockKey(productCode, storeCode);
			Stock stock = new Stock(productCode, storeCode, storeProductQuantity);
			stockMap.put(stockKey, stock);
		}
		logger.debug("Generated product stock map, size " + productStockMap.size());
		logger.debug("Generated store product map, size " + storeProductMap.size());
		logger.debug("Generated stock map, size " + stockMap.size());
		setProductStockMap(productStockMap);
		setStoreProductMap(storeProductMap);
		setStockMap(stockMap);
	}
}