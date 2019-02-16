/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.transfer.excel;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
    HSSFCell date2Cell = wb.getSheetAt(0).getRow(0).getCell(2);
    Object value = ExcelItemReader.getCellValue(dateCell);
    Assert.assertTrue(value instanceof Date);
    Object value2 = ExcelItemReader.getCellValue(dateTimeCell);
    Assert.assertTrue(value2 instanceof Date);

    Object value3 = ExcelItemReader.getCellValue(date2Cell);
    Assert.assertTrue(value3 instanceof Date);
    Assert.assertTrue("2019-10-01".equals(new SimpleDateFormat("yyyy-MM-dd").format((Date) value3)));
    in.close();
    wb.close();
  }

  public void testReadTitles() throws Exception {
    FileInputStream in = new FileInputStream("src/test/resources/data.xls");
    HSSFWorkbook wb = new HSSFWorkbook(in);
    String[] titles = ExcelItemReader.readComments(wb.getSheetAt(0), 0);
    Assert.assertTrue(Arrays.toString(titles).equals("[code, name, mail]"));
    in.close();
    wb.close();
  }

}
