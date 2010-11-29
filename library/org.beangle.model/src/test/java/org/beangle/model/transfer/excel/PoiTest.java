/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormat;

public class PoiTest {
	public static void main(String[] args) throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("new sheet");

		// Create a row and put some cells in it. Rows are 0 based.
		HSSFRow row = sheet.createRow(0);

		// Create a cell and put a date value in it. The first cell is not
		// styled as a date.
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(new Date());

		// we style the second cell as a date (and time). It is important to
		// create a new cell style from the workbook
		// otherwise you can end up modifying the built in style and effecting
		// not only this cell but other cells.
		HSSFCellStyle cellStyle = wb.createCellStyle();
		DataFormat df = wb.createDataFormat();
		// cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
		cellStyle.setDataFormat(df.getFormat("YYYY-MM-DD HH:MM:SS"));
		cell = row.createCell(1);
		cell.setCellValue(new Date());
		cell.setCellStyle(cellStyle);
		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("src/test/resources/workbook.xls");
		wb.write(fileOut);
		fileOut.close();
	}
}
