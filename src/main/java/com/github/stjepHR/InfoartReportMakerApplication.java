package com.github.stjepHR;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.stjepHR.core.DataAggregator;
import com.github.stjepHR.data.DataStorage;
import com.github.stjepHR.model.ExchangeRate;
import com.github.stjepHR.utils.DataReader;
import com.github.stjepHR.utils.DataUtils;
import com.github.stjepHR.utils.DataWriter;
import com.github.stjepHR.utils.HTTPUtils;

/**
 * Main orchestrator class.
 */
public class InfoartReportMakerApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(InfoartReportMakerApplication.class);
	
	public static void main(String[] args) {

		// Default values in case there is no configuration file or property
		String outputFileEncoding = "UTF-8";
		String outputFileNewLineCharacter = "\n";
		String outputFileColumnDelimiter = "\t";
		String outputFile1Path = "src/main/resources/file1.txt";
		String outputFile2Path = "src/main/resources/file2.txt";
		String exchangeRateListURL = "https://www.hnb.hr/tecajn-eur/htecajn.htm";
		String foreignCurrency = "";
		
		Properties properties = new Properties();
		
		if (args.length > 4) {
			try (FileInputStream input = new FileInputStream(args[4])) {
				properties.load(input);
				logger.debug("Reading config.properties file");
				if (properties.getProperty("outputFileEncoding") != null) 
					outputFileEncoding = properties.getProperty("outputFileEncoding");
				if (properties.getProperty("outputFileNewLineCharacter") != null)
					outputFileNewLineCharacter = properties.getProperty("outputFileNewLineCharacter");
				if (properties.getProperty("outputFileColumnDelimiter") != null)
					outputFileColumnDelimiter = properties.getProperty("outputFileColumnDelimiter");
				if (properties.getProperty("outputFile1Path") != null)
					outputFile1Path = properties.getProperty("outputFile1Path");
				if (properties.getProperty("outputFile2Path") != null)
					outputFile2Path = properties.getProperty("outputFile2Path");
				if (properties.getProperty("exchangeRateListURL") != null)
					exchangeRateListURL = properties.getProperty("exchangeRateListURL");
				if (properties.getProperty("foreignCurrency") != null)
					foreignCurrency =  properties.getProperty("foreignCurrency");
			} catch (FileNotFoundException e) {
				logger.error("Non critical error: Configuration file not found");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		logger.info("Reading files...");
		DataReader.setInputCodePage("Windows-1250");
		List<String> productFileLines = DataReader.readFile(args[0]);
		List<String> storeFileLines = DataReader.readFile(args[1]);
		List<String> stockFileLines = DataReader.readFile(args[2]);
		List<String> priceListFileLines = DataReader.readFile(args[3]);
		
		DataStorage dataStorage = new DataStorage();

		dataStorage.generateStockMaps(stockFileLines);
		dataStorage.generateProductMap(productFileLines);
		dataStorage.generatePriceListMap(priceListFileLines);
		dataStorage.generateStoreMap(storeFileLines);
		
		String exchangeRateList = HTTPUtils.getTextDataFromURL(exchangeRateListURL);
		Map<String, ExchangeRate> exchangeRateListMap = DataUtils.parseExchangeRateList(exchangeRateList);
		ExchangeRate exchangeRate = exchangeRateListMap.get(foreignCurrency);
		if (exchangeRate != null) logger.debug("Fetched foreign currency: " + exchangeRate.getCurrencyCode());
		
		DecimalFormat decimalFormatter = DataUtils.getNumericalFormatter();
		
		List<String> file1Rows = DataAggregator.prepareFile1Rows(
				dataStorage,
				exchangeRate,
				outputFileColumnDelimiter,
				decimalFormatter
		);
		
		List<String> file2Rows = DataAggregator.prepareFile2Rows(
				dataStorage,
				exchangeRate,
				outputFileColumnDelimiter,
				decimalFormatter
		);

		DataWriter.writeFile(outputFile1Path, file1Rows, outputFileEncoding, outputFileNewLineCharacter);
		DataWriter.writeFile(outputFile2Path, file2Rows, outputFileEncoding, outputFileNewLineCharacter);
	
		logger.info("Done.");
	}
}
