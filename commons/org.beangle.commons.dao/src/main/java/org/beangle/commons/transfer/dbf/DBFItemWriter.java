/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.dbf;

import org.beangle.commons.transfer.io.AbstractItemWriter;
import org.beangle.commons.transfer.io.TransferFormats;

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
        fields[i].setFieldLength(255);
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
  public String getFormat() {
    return TransferFormats.DBF;
  }

}
