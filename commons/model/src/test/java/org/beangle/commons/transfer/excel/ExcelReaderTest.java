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
