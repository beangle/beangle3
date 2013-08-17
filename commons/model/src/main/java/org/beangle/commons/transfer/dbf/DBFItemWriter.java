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

import org.beangle.commons.transfer.io.AbstractItemWriter;
import org.beangle.commons.transfer.io.TransferFormat;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;

/**
 * <p>
 * DBFItemWriter class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class DBFItemWriter extends AbstractItemWriter {
  DBFWriter writer;

  String charSet = "gbk";

  /**
   * <p>
   * Constructor for DBFItemWriter.
   * </p>
   */
  public DBFItemWriter() {
    super();
    writer = new DBFWriter();
    writer.setCharactersetName(charSet);
  }

  /** {@inheritDoc} */
  public void write(Object obj) {
    try {
      writer.addRecord((Object[]) obj);
    } catch (DBFException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /** {@inheritDoc} */
  public void writeTitle(String titleName, Object data) {
    Object[] attrs = (Object[]) data;
    try {
      DBFField fields[] = new DBFField[attrs.length];
      for (int i = 0; i < fields.length; i++) {
        fields[i] = new DBFField();
        fields[i].setName((String) attrs[i]);
        fields[i].setDataType(DBFField.FIELD_TYPE_C);
        // FIXME
        fields[i].setFieldLength(500);
      }
      writer.setFields(fields);
    } catch (DBFException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * <p>
   * close.
   * </p>
   */
  public void close() {
    try {
      writer.write(outputStream);
    } catch (DBFException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * <p>
   * getFormat.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public TransferFormat getFormat() {
    return TransferFormat.Dbf;
  }

}
