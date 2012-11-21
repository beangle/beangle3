/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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

import org.apache.commons.lang3.SystemUtils;
import org.testng.annotations.Test;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

@Test
public class DBFItemWriterTest {

  public void testWriter() throws Exception {
    DBFItemWriter writer = new DBFItemWriter();

    String dbfile = SystemUtils.getJavaIoTmpDir() + "/test.dbf";
    FileOutputStream fos = new FileOutputStream(dbfile);

    writer.setContext(null);
    writer.setOutputStream(fos);
    writer.writeTitle("table1", new Object[] { "code", "name" });
    writer.write(new Object[] { "001", "apple" });
    writer.write(new Object[] { "002", "banana" });
    writer.write(new Object[] { "003", "香蕉香蕉香蕉" });
    writer.write(new Object[] { "004", "long word of unknow catalog fruit" });
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
    while ((rowObjects = reader.nextRecord()) != null) {
      for (int i = 0; i < rowObjects.length; i++) {
        System.out.println(rowObjects[i]);
      }
    }
    inputStream.close();

  }
}
