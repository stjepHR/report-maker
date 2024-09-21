# Report Maker Documentation

This document describes the usage and data formats of the Report Maker application.

# Running the application

## Using JAR

Download the JAR file from the [target directory](https://github.com/stjepHR/report-maker/blob/main/target/).

Run in console using command:
```
java -jar report-maker-1.0.jar file1 file2 file3 file4 config.properties
```

Where:
```
file1 represents input Products file (artikli.txt)
file2 represents input Stores file (pm.txt)
file3 represents input Stock file (stanja.txt)
file4 represents input Price List file (cjenik.txt)
file5 represents config.properties file to be read as Java Properties
```

Sample input data files and a `config.properties` file from [resources directory](https://github.com/stjepHR/report-maker/tree/main/src/main/resources) can be used.

## Example usage

```
java -jar report-maker-1.0.jar artikli.txt pm.txt stanja.txt cjenik.txt config.properties
```

# Building the application

## Using Maven

1. Checkout the project from the repository.

2. If needed, do the desired changes on the source code.

3. In the console, navigate to the project root directory and run:

```
mvn clean package
```

4. The resulting JAR file will be located in target/ directory.

To run it from the root directory, use:
```
java -jar target/report-maker-1.0.jar file1 file2 file3 file4 config.properties
```

as described in the [Running the Application](#running-the-application) chapter.

# Data Formats

## Input data files
Code page of all input data files is Windows 1250. 

New line separator is CRLF (Windows convention).

Column separator is pipe sign („|“).

Decimal separator is coma sign (",").

## Input config.properties file

The file supports usage of values:

```
outputFileEncoding
outputFileNewLineCharacter
outputFileColumnDelimiter
outputFile1Path
outputFile2Path
exchangeRateListURL
foreignCurrency
```

Note that the `foreignCurrency` value must match the concatenated currency code value from the given [exchange rate list](https://www.hnb.hr/tecajn-eur/htecajn.htm). If the value is not present, the application will generate an empty column in the place of foreign currency related calculations.

### Example setup

```
outputFileEncoding=UTF-8
outputFileNewLineCharacter=\n
outputFileColumnDelimiter=\t
outputFile1Path=vrijednost zalihe - artikli.txt
outputFile2Path=vrijednost zalihe - PM.txt
exchangeRateListURL=https://www.hnb.hr/tecajn-eur/htecajn.htm
foreignCurrency=756CHF
```
