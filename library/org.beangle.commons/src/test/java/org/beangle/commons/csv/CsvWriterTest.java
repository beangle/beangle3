/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.csv;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

public class CsvWriterTest {

	private String invokeWriter(String[] args) throws IOException {
		StringWriter sw = new StringWriter();
		CsvWriter csvw = new CsvWriter(sw, new CsvFormat.Builder().delimiter('\'').build());
		csvw.write(args);
		return sw.toString();
	}

	private String invokeNoEscapeWriter(String[] args) throws IOException {
		StringWriter sw = new StringWriter();
		CsvWriter csvw = new CsvWriter(sw, new CsvFormat.Builder().delimiter('\'')
				.escape(CsvWriter.NO_ESCAPE_CHARACTER).build());
		csvw.write(args);
		return sw.toString();
	}

	@Test
	public void correctlyParseNullString() {
		StringWriter sw = new StringWriter();
		CsvWriter csvw = new CsvWriter(sw, new CsvFormat.Builder().escape('\'').build());
		csvw.write((String[]) null);
		assertEquals(0, sw.toString().length());
	}

	/**
	 * Tests parsing individual lines.
	 * 
	 * @throws IOException
	 *             if the reader fails.
	 */
	@Test
	public void testParseLine() throws IOException {

		// test normal case
		String[] normal = { "a", "b", "c" };
		String output = invokeWriter(normal);
		assertEquals(output, "'a','b','c'\n");

		// test quoted commas
		String[] quoted = { "a", "b,b,b", "c" };
		output = invokeWriter(quoted);
		assertEquals("'a','b,b,b','c'\n", output);

		// test empty elements
		String[] empty = {,};
		output = invokeWriter(empty);
		assertEquals("\n", output);

		// test multiline quoted
		String[] multiline = { "This is a \n multiline entry", "so is \n this" };
		output = invokeWriter(multiline);
		assertEquals("'This is a \n multiline entry','so is \n this'\n", output);

		// test quoted line
		String[] quoteLine = { "This is a \" multiline entry", "so is \n this" };
		output = invokeWriter(quoteLine);
		assertEquals("'This is a \" multiline entry','so is \n this'\n", output);

	}

	@Test
	public void parseLineWithBothEscapeAndQuoteChar() throws IOException {
		// test quoted line
		String[] quoteLine = { "This is a 'multiline' entry", "so is \n this" };
		String output = invokeWriter(quoteLine);
		assertEquals("'This is a \\'multiline\\' entry','so is \n this'\n", output);
	}

	/**
	 * Tests parsing individual lines.
	 * 
	 * @throws IOException
	 *             if the reader fails.
	 */
	@Test
	public void testParseLineWithNoEscapeChar() throws IOException {

		// test normal case
		String[] normal = { "a", "b", "c" };
		String output = invokeNoEscapeWriter(normal);
		assertEquals("'a','b','c'\n", output);

		// test quoted commas
		String[] quoted = { "a", "b,b,b", "c" };
		output = invokeNoEscapeWriter(quoted);
		assertEquals("'a','b,b,b','c'\n", output);

		// test empty elements
		String[] empty = {,};
		output = invokeNoEscapeWriter(empty);
		assertEquals("\n", output);

		// test multiline quoted
		String[] multiline = { "This is a \n multiline entry", "so is \n this" };
		output = invokeNoEscapeWriter(multiline);
		assertEquals("'This is a \n multiline entry','so is \n this'\n", output);

	}

	@Test
	public void parseLineWithNoEscapeCharAndQuotes() throws IOException {
		String[] quoteLine = { "This is a \" 'multiline' entry", "so is \n this" };
		String output = invokeNoEscapeWriter(quoteLine);
		assertEquals("'This is a \" 'multiline' entry','so is \n this'\n", output);
	}

	/**
	 * Test parsing from to a list.
	 * 
	 * @throws IOException
	 *             if the reader fails.
	 */
	@Test
	public void testParseAll() throws IOException {

		List<String[]> allElements = new ArrayList<String[]>();
		String[] line1 = "Name#Phone#Email".split("#");
		String[] line2 = "Glen#1234#glen@abcd.com".split("#");
		String[] line3 = "John#5678#john@efgh.com".split("#");
		allElements.add(line1);
		allElements.add(line2);
		allElements.add(line3);

		StringWriter sw = new StringWriter();
		CsvWriter csvw = new CsvWriter(sw);
		csvw.write(allElements);

		String result = sw.toString();
		String[] lines = result.split("\n");

		assertEquals(3, lines.length);

	}

	/**
	 * Tests the option of having omitting quotes in the output stream.
	 * 
	 * @throws IOException
	 *             if bad things happen
	 */
	@Test
	public void testNoQuoteChars() throws IOException {
		String[] line = { "Foo", "Bar", "Baz" };
		StringWriter sw = new StringWriter();
		CsvWriter csvw = new CsvWriter(sw, new CsvFormat.Builder().delimiter(
				CsvWriter.NO_QUOTE_CHARACTER).build());
		csvw.write(line);
		String result = sw.toString();

		assertEquals("Foo,Bar,Baz\n", result);
	}

	/**
	 * Tests the option of having omitting quotes in the output stream.
	 * 
	 * @throws IOException
	 *             if bad things happen
	 */
	@Test
	public void testNoQuoteCharsAndNoEscapeChars() throws IOException {

		String[] line = { "Foo", "Bar", "Baz" };
		StringWriter sw = new StringWriter();
		CsvWriter csvw = new CsvWriter(sw, new CsvFormat.Builder()
				.delimiter(CsvWriter.NO_QUOTE_CHARACTER).escape(CsvWriter.NO_ESCAPE_CHARACTER)
				.build());
		csvw.write(line);
		String result = sw.toString();

		assertEquals("Foo,Bar,Baz\n", result);
	}

	/**
	 * Test null values.
	 * 
	 * @throws IOException
	 *             if bad things happen
	 */
	@Test
	public void testNullValues() throws IOException {

		String[] line = { "Foo", null, "Bar", "baz" };
		StringWriter sw = new StringWriter();
		CsvWriter csvw = new CsvWriter(sw);
		csvw.write(line);
		String result = sw.toString();

		assertEquals("\"Foo\",,\"Bar\",\"baz\"\n", result);

	}

	@Test
	public void testStreamFlushing() throws IOException {

		String WRITE_FILE = "myfile.csv";

		String[] nextLine = new String[] { "aaaa", "bbbb", "cccc", "dddd" };

		FileWriter fileWriter = new FileWriter(WRITE_FILE);
		CsvWriter writer = new CsvWriter(fileWriter);

		writer.write(nextLine);

		// If this line is not executed, it is not written in the file.
		writer.close();

	}

	@Test
	public void testAlternateEscapeChar() {
		String[] line = { "Foo", "bar's" };
		StringWriter sw = new StringWriter();
		CsvWriter csvw = new CsvWriter(sw, new CsvFormat.Builder().escape('\'').build());
		csvw.write(line);
		assertEquals("\"Foo\",\"bar's\"\n", sw.toString());
	}

	@Test
	public void testNoQuotingNoEscaping() {
		String[] line = { "\"Foo\",\"Bar\"" };
		StringWriter sw = new StringWriter();
		CsvWriter csvw = new CsvWriter(sw, new CsvFormat.Builder()
				.delimiter(CsvWriter.NO_QUOTE_CHARACTER).escape(CsvWriter.NO_ESCAPE_CHARACTER)
				.build());
		csvw.write(line);
		assertEquals("\"Foo\",\"Bar\"\n", sw.toString());
	}

	@Test
	public void testNestedQuotes() {
		String[] data = new String[] { "\"\"", "test" };
		String oracle = "\"\"\"\",\"test\"\n";

		CsvWriter writer = null;
		File tempFile = null;
		FileWriter fwriter = null;

		try {
			tempFile = File.createTempFile("csvWriterTest", ".csv");
			tempFile.deleteOnExit();
			fwriter = new FileWriter(tempFile);
			writer = new CsvWriter(fwriter);
		} catch (IOException e) {
		}

		// write the test data:
		writer.write(data);

		try {
			writer.close();
		} catch (IOException e) {
		}

		try {
			fwriter.flush();
		} catch (IOException e) {
		}

		// read the data and compare.
		FileReader in = null;
		try {
			in = new FileReader(tempFile);
		} catch (FileNotFoundException e) {
		}

		StringBuilder fileContents = new StringBuilder(CsvWriter.INITIAL_STRING_SIZE);
		try {
			int ch;
			while ((ch = in.read()) != -1) {
				fileContents.append((char) ch);
			}
			in.close();
		} catch (IOException e) {
		}
		assertEquals(oracle, fileContents.toString());
	}

}
