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

import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;

@Test
public class ExcelItemWriterTest {

  public void testWrite() throws Exception {
    File file = new File("src/test/resources/tmp.xlsx");
    if (!file.exists()) {
      file.createNewFile();
    }
    FileOutputStream fos = new FileOutputStream(file);
    ExcelItemWriter writer = new ExcelItemWriter(fos);
    writer.writeTitle("人员信息", new String[]{"姓名", "性别", "身份证号", "政治面貌"});
    writer.write(new String[]{"张三", "男", "xxxx", "无党派人士"});
    writer.flush();
    fos.close();
    file.delete();
    file.deleteOnExit();
    System.out.println(file.exists());
  }

}
