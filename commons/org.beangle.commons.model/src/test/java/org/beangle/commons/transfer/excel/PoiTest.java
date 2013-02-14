/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.transfer.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormat;
import org.beangle.commons.lang.SystemInfo;
import org.testng.annotations.Test;

@Test
public class PoiTest {

  public void teset() throws IOException {
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
    FileOutputStream fileOut = new FileOutputStream(SystemInfo.getTmpDir() + "/workbook.xls");
    wb.write(fileOut);
    fileOut.close();
  }
}
