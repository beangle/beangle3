/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.transfer.excel;

import java.io.FileInputStream;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class ExcelReaderTest {

	public void testReadCell() throws Exception {
		FileInputStream in = new FileInputStream("src/test/resources/date.xls");
		HSSFWorkbook wb = new HSSFWorkbook(in);
		HSSFCell dateCell = wb.getSheetAt(0).getRow(0).getCell(0);
		HSSFCell dateTimeCell = wb.getSheetAt(0).getRow(0).getCell(1);
		Object value = ExcelItemReader.getCellValue(dateCell);
		Assert.assertTrue(value instanceof Date);
		Object value2 = ExcelItemReader.getCellValue(dateTimeCell);
		Assert.assertTrue(value2 instanceof Date);
		in.close();
	}
}
