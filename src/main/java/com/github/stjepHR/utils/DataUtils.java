package com.github.stjepHR.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import com.github.stjepHR.model.ExchangeRate;

public class DataUtils {

	/**
	 * Sorts the input Map by generating a corresponding TreeMap object.
	 * @param <K> Original Map key.
	 * @param <V> Original Map value.
	 * @param originalMap Map to be sorted.
	 * @return Sorted Map in form of TreeMap.
	 */
	public static <K, V> TreeMap<K, V> sortHashMap(Map<K, V> originalMap) {
		return new TreeMap<>(originalMap);
	}
	
	/**
	 * Parses given exchange rate table in String type.
	 * Stores each row as an ExchangeRate object in the HashMap. 
	 * @param exchangeRateList Input exchange rate table.
	 * @return HashMap where Key is Currency code and value is ExchangeRate object. 
	 */
	public static HashMap<String, ExchangeRate> parseExchangeRateList(String exchangeRateList) {
		HashMap<String, ExchangeRate> exchangeRateListMap = new HashMap<>();
		String[] exchangeRateListLines = exchangeRateList.split("\\r?\\n");
		for (int i = 1; i < exchangeRateListLines.length; i++) {
			String line = exchangeRateListLines[i];
			String[] lineParts = line.trim().split("\\s+");
			
			if (lineParts.length == 4) {
				ExchangeRate exchangeRate = new ExchangeRate(
						lineParts[0], 
						Double.valueOf(lineParts[1].replace(",", ".")), 
						Double.valueOf(lineParts[2].replace(",", ".")), 
						Double.valueOf(lineParts[3].replace(",", "."))
						);
				exchangeRateListMap.put(lineParts[0], exchangeRate);
			}
		}
		return exchangeRateListMap;
	}

	/**
	 * Generates a numerical formatter for standardized output.
	 * @return DecimalFormat object.
	 */
	public static DecimalFormat getNumericalFormatter() {
		DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.getDefault());
		dfs.setGroupingSeparator('.');
		dfs.setDecimalSeparator(',');
		DecimalFormat df = new DecimalFormat("#,##0.00", dfs);
		return df;
	}

}
