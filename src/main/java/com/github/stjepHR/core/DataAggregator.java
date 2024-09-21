package com.github.stjepHR.core;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.stjepHR.data.DataStorage;
import com.github.stjepHR.model.ExchangeRate;
import com.github.stjepHR.model.Product;
import com.github.stjepHR.model.StockKey;
import com.github.stjepHR.model.Store;

public class DataAggregator {

	private static final Logger logger = LoggerFactory.getLogger(DataAggregator.class);
	
	/**
	 * Prepares output rows according to the File 1 requirements specification.
	 * @param dataStorage
	 * @param exchangeRate
	 * @param columnDelimiter
	 * @param decimalFormatter
	 * @return List of Strings (rows) that can be passed to DataWriter.
	 */
	public static List<String> prepareFile1Rows(
			DataStorage dataStorage,
			ExchangeRate exchangeRate,
			String columnDelimiter,
			DecimalFormat decimalFormatter
			) {
		List<String> fileRows = new ArrayList<>();
		for (Map.Entry<String, Product> entry : dataStorage.getProductMap().entrySet()) {
			StringBuilder row = new StringBuilder();

			// Product code
			row.append(entry.getKey());
			row.append(columnDelimiter);
			
			// Product name
			row.append(entry.getValue().getProductName());
			row.append(columnDelimiter);

			// Product price (euros)
			Double productPrice = (dataStorage.getPriceListMap().get(entry.getKey()) != null) ?
					dataStorage.getPriceListMap().get(entry.getKey()).getProductPrice() : null;
			if (productPrice != null) row.append(decimalFormatter.format(productPrice));
			row.append(columnDelimiter);
			
			// Product stock in all stores
			Double productStockInAllStores = (dataStorage.getProductStockMap().get(entry.getKey()) != null) ?
					dataStorage.getProductStockMap().get(entry.getKey()).stream().mapToDouble(Double::doubleValue).sum() : null; 
			if (productStockInAllStores != null) row.append(decimalFormatter.format(productStockInAllStores));
			row.append(columnDelimiter);

			// Product measurement unit
			row.append(entry.getValue().getProductUnit());
			row.append(columnDelimiter);
			
			// Total value of product in all stores (euros)
			if (productPrice != null && productStockInAllStores != null) {
				Double totalValueEuro = productPrice * productStockInAllStores;
				row.append(decimalFormatter.format(totalValueEuro));
				row.append(columnDelimiter);
				// Total value of product in all stores (foreign currency)
				if (exchangeRate != null && exchangeRate.getRateMiddle() != null) {
					Double totalValueForeignCurrency = totalValueEuro * exchangeRate.getRateMiddle();
					row.append(decimalFormatter.format(totalValueForeignCurrency));
				}
			}
			row.append(columnDelimiter);
			
			// Number of stores containing the product
			if (dataStorage.getProductStockMap().get(entry.getKey()) != null) row.append(dataStorage.getProductStockMap().get(entry.getKey()).size());
			
			fileRows.add(row.toString());
		}
		logger.debug("Aggregated " + fileRows.size() + " output rows for file 1");
		return fileRows;
	}
	
	/**
	 * Prepares output rows according to the File 2 requirements specification.
	 * @param dataStorage
	 * @param exchangeRate
	 * @param columnDelimiter
	 * @param decimalFormatter
	 * @return List of Strings (rows) that can be passed to DataWriter.
	 */
	public static List<String> prepareFile2Rows(
			DataStorage dataStorage,
			ExchangeRate exchangeRate,
			String columnDelimiter,
			DecimalFormat decimalFormatter
			) {
		List<String> fileRows = new ArrayList<>();
		for (Map.Entry<String, Store> entry : dataStorage.getStoreMap().entrySet()) {
			StringBuilder row = new StringBuilder();

			// Store code
			row.append(entry.getKey());
			row.append(columnDelimiter);
			
			// Store name
			row.append(entry.getValue().getStoreName());
			row.append(columnDelimiter);

			// Total value of products in store (euros)
			List<String> allProducts = dataStorage.getStoreProductMap().get(entry.getKey());
			if (allProducts != null) {
				Double sumValuesEuro = 0d;
				for (String productCode : allProducts) {
					sumValuesEuro += (dataStorage.getStockMap().get(new StockKey(productCode, entry.getKey())).getStoreProductQuantity() * dataStorage.getPriceListMap().get(productCode).getProductPrice());
				}
				row.append(decimalFormatter.format(sumValuesEuro));
				row.append(columnDelimiter);
				// Total value of products in store (foreign currency)
				if (exchangeRate != null && exchangeRate.getRateMiddle() != null) {
					Double sumValuesForeignCurrency = sumValuesEuro * exchangeRate.getRateMiddle();
					row.append(decimalFormatter.format(sumValuesForeignCurrency));
				}
			}
			row.append(columnDelimiter);
			
			// Number of *distinct* products in the store
			if (dataStorage.getStoreProductMap().get(entry.getKey()) != null) row.append(dataStorage.getStoreProductMap().get(entry.getKey()).size());
			
			fileRows.add(row.toString());
		}
		logger.debug("Aggregated " + fileRows.size() + " output rows for file 2");
		return fileRows;
	}
	
}
