package com.github.stjepHR.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataWriter {
	
	private static final Logger logger = LoggerFactory.getLogger(DataWriter.class);
	
	/**
	 * Used for generating output files.
	 * @param filePath Specifies output file location and name.
	 * @param fileRows List of Strings representing rows to be written to file.
	 * @param charset Desired output file character set.
	 * @param newLineCharacter Desired 'new line' character.
	 */
	public static void writeFile(String filePath, List<String> fileRows, String charset, String newLineCharacter) {
		logger.debug("Writing to file: " + filePath);
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), charset))) {
			for (String row : fileRows) {
				writer.write(row);
				writer.write(newLineCharacter);
			}
			logger.debug("File write OK");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}