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
package org.beangle.commons.transfer.dbf;

import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.beangle.commons.lang.SystemInfo;
import org.testng.annotations.Test;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

@Test
public class DBFItemWriterTest {

  public void testWriter() throws Exception {
    DBFItemWriter writer = new DBFItemWriter();

    String dbfile = SystemInfo.getTmpDir() + "/test.dbf";
    FileOutputStream fos = new FileOutputStream(dbfile);

    writer.setContext(null);
    writer.setOutputStream(fos);
    writer.writeTitle("table1", new Object[] { "code", "name" });
    Object[][] datas = new Object[][] { { "001", "apple" }, { "002", "banana" }, { "003", "香蕉香蕉香蕉" },
        { "004", "long word of unknow catalog fruit" } };
    for (Object[] data : datas) {
      writer.write(data);
    }
    writer.close();
    fos.close();

    InputStream inputStream = new FileInputStream(dbfile);
    DBFReader reader = new DBFReader(inputStream);
    reader.setCharactersetName("gbk");
    int numberOfFields = reader.getFieldCount();

    assertEquals(2, numberOfFields);
    DBFField field0 = reader.getField(0);
    assertEquals("code", field0.getName());
    DBFField field1 = reader.getField(1);
    assertEquals("name", field1.getName());
    Object[] rowObjects;
    int i = 0;
    while ((rowObjects = reader.nextRecord()) != null) {
      // assertEquals(rowObjects[0].toString().trim(), datas[i][0]);
      // assertEquals(rowObjects[1].toString().trim(), datas[i][1]);
      i++;
    }
    inputStream.close();

  }
}
