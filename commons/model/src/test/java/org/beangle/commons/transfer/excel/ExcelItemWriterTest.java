/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class ExcelItemWriterTest {

  public void testWrite() throws IOException {
    File file = new File("src/test/resources/tmp.xls");
    if (!file.exists()) {
      file.createNewFile();
    }
    ExcelItemWriter writer = new ExcelItemWriter(new FileOutputStream(file));
    writer.writeTitle("人员信息", new String[] { "姓名", "性别", "身份证号", "政治面貌" });
    writer.write(new String[] { "张三", "男", "xxxx", "无党派人士" });
    writer.close();
  }

  @Test(dependsOnMethods = { "testWrite" })
  public void testRead() throws Exception {
    File file = new File("src/test/resources/tmp.xls");
    FileInputStream in = new FileInputStream(file);
    HSSFWorkbook wb = new HSSFWorkbook(in);
    HSSFCell cell = wb.getSheetAt(0).getRow(0).getCell(0);
    // HSSFFont f = cell.getCellStyle().getFont(wb);
    // Assert.assertEquals(f.getBoldweight(), HSSFFont.BOLDWEIGHT_BOLD);
    Assert.assertEquals(cell.getCellStyle().getAlignment(), HSSFCellStyle.ALIGN_CENTER);
    in.close();
    file.delete();
  }
}
