package com.github.stjepHR.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPUtils {

	private static final Logger logger = LoggerFactory.getLogger(HTTPUtils.class);
	
	/**
	 * Fetches text data from given URL using HTTP GET method.
	 * @param siteURL Given site URL.
	 * @return String containing fetched text.
	 */
	public static String getTextDataFromURL(String siteURL) {
		logger.debug("Fetching data from " + siteURL);
		String fetchedText = "";
		
		try {
			URL url = new URL(siteURL);
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			logger.debug("HTTP connection open");
			connection.setRequestMethod("GET");
			
			Integer responseCode = connection.getResponseCode();
			if (responseCode.equals(HttpURLConnection.HTTP_OK)) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuilder content = new StringBuilder();
				
				while ((inputLine = in.readLine()) != null) {
					content.append(inputLine).append("\n");
				}
				
				fetchedText = content.toString();
				
				in.close();
				
			}
			
			connection.disconnect();
			logger.debug("HTTP connection closed");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("Fetch data OK");
		return fetchedText;
	}
	
}
