package com.github.stjepHR.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataReader {

	private static final Logger logger = LoggerFactory.getLogger(DataReader.class);
	
	public static String inputCodePage = "";

	public static void setInputCodePage(String inputCodePage) {
		DataReader.inputCodePage = inputCodePage;
	}
	
	/**
	 * Used for reading input text files.
	 * @param filePath Specifies input file location and name.
	 * @return List of Strings representing rows of the input file.
	 */
	public static List<String> readFile(String filePath) {
		logger.debug("Reading file: " + filePath);
		List<String> lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(filePath), inputCodePage))) {
					String line;
					while ((line = br.readLine()) != null) {
						lines.add(line);
					}
					logger.debug("File read OK");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

}
