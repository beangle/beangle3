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

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.beangle.commons.lang.Numbers;
import org.beangle.commons.transfer.exporter.Context;
import org.beangle.commons.transfer.io.AbstractItemWriter;
import org.beangle.commons.transfer.io.TransferFormat;

import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * ExcelItemWriter class.
 * </p>
 *
 * @author chaostone
 * @version $Id: $
 */
public class ExcelItemWriter extends AbstractItemWriter {

  protected int countPerSheet = 50000;

  protected SXSSFWorkbook workbook = new SXSSFWorkbook(); // 建立新SXSSFWorkbook对象

  protected int index = 0;

  protected SXSSFSheet sheet;

  protected CellStyle dateStyle = null;

  protected CellStyle timeStyle = null;

  private Object title;

  /**
   * <p>
   * Constructor for ExcelItemWriter.
   * </p>
   */
  public ExcelItemWriter() {
    super();
  }

  /**
   * <p>
   * Constructor for ExcelItemWriter.
   * </p>
   *
   * @param outputStream a {@link java.io.OutputStream} object.
   */
  public ExcelItemWriter(OutputStream outputStream) {
    this();
    this.outputStream = outputStream;
  }

  /**
   * <p>
   * Getter for the field <code>countPerSheet</code>.
   * </p>
   *
   * @return a int.
   */
  public int getCountPerSheet() {
    return countPerSheet;
  }

  /**
   * <p>
   * Setter for the field <code>countPerSheet</code>.
   * </p>
   *
   * @param dataNumPerSheet a int.
   */
  public void setCountPerSheet(int dataNumPerSheet) {
    this.countPerSheet = dataNumPerSheet;
  }

  /**
   * {@inheritDoc}
   */
  public void write(Object obj) {
    if (index + 1 >= countPerSheet) {
      writeTitle(null, title);
    }
    writeItem(obj);
    index++;
  }

  /**
   * {@inheritDoc}
   */
  public void writeTitle(String titleName, Object data) {
    if (null != titleName) {
      sheet = workbook.createSheet(titleName);
    } else {
      sheet = workbook.createSheet();
    }
    title = data;
    index = 0;
    writeItem(data);
    SXSSFRow titleRow = sheet.getRow(index);
    CellStyle titleStyle = getTitleStyle();
    for (int i = 0; i < titleRow.getLastCellNum(); i++) {
      titleRow.getCell(i).setCellStyle(titleStyle);
    }
    index++;
  }

  /**
   * <p>
   * getFormat.
   * </p>
   *
   * @return a {@link java.lang.String} object.
   */
  public TransferFormat getFormat() {
    return TransferFormat.Xls;
  }

  /**
   * <p>
   * writeItem.
   * </p>
   *
   * @param datas a {@link java.lang.Object} object.
   */
  protected void writeItem(Object datas) {
    SXSSFRow row = sheet.createRow(index); // 建立新行
    if (datas != null) {
      if (datas.getClass().isArray()) {
        Object[] values = (Object[]) datas;
        for (int i = 0; i < values.length; i++) {
          SXSSFCell cell = row.createCell(i);
          if (values[i] instanceof Number) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(((Number) values[i]).doubleValue());
          } else if (values[i] instanceof java.sql.Date) {
            cell.setCellValue((Date) values[i]);
            cell.setCellStyle(getDateStyle());
          } else if (values[i] instanceof java.util.Date) {
            cell.setCellValue((Date) values[i]);
            cell.setCellStyle(getTimeStyle());
          } else if (values[i] instanceof Calendar) {
            cell.setCellValue((Calendar) values[i]);
            cell.setCellStyle(getTimeStyle());
          } else {
            cell.setCellValue(new XSSFRichTextString((values[i] == null) ? "" : values[i].toString()));
          }
        }
      } else {
        SXSSFCell cell = row.createCell(0);
        if (datas instanceof Number) {
          cell.setCellType(CellType.NUMERIC);
        }
        cell.setCellValue(new XSSFRichTextString(datas.toString()));
      }
    }
  }

  /**
   * <p>
   * close.
   * </p>
   */
  public void flush() {
    try {
      workbook.write(outputStream);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
    }
    workbook.dispose();
  }

  /**
   * {@inheritDoc} 设置每个sheet多少条记录
   */
  public void setContext(Context context) {
    super.setContext(context);
    if (null != context) {
      Object count = context.getDatas().get("countPerSheet");
      if (null != count && Numbers.isDigits(count.toString())) {
        int countParam = Numbers.toInt(count.toString());
        if (countParam > 0) this.countPerSheet = countParam;
      }
    }
  }

  private CellStyle getDateStyle() {
    if (null == dateStyle) {
      dateStyle = workbook.createCellStyle();
      dateStyle.setDataFormat(workbook.createDataFormat().getFormat(getDateFormat()));
    }
    return dateStyle;
  }

  private CellStyle getTimeStyle() {
    if (null == timeStyle) {
      timeStyle = workbook.createCellStyle();
      timeStyle.setDataFormat(workbook.createDataFormat().getFormat(getDateTimeFormat()));
    }
    return timeStyle;
  }

  /**
   * <p>
   * getDateFormat.
   * </p>
   *
   * @return a {@link java.lang.String} object.
   */
  protected String getDateFormat() {
    return "YYYY-MM-DD";
  }

  /**
   * <p>
   * getDateTimeFormat.
   * </p>
   *
   * @return a {@link java.lang.String} object.
   */
  protected String getDateTimeFormat() {
    return "YYYY-MM-DD HH:MM:SS";
  }

  /**
   * getTitleStyle.
   */
  protected CellStyle getTitleStyle() {
    XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
    style.setAlignment(HorizontalAlignment.CENTER); // 左右居中
    style.setVerticalAlignment(VerticalAlignment.CENTER); // 上下居中
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    byte[] rgb = new byte[]{(byte) 221, (byte) 217, (byte) 196};
    style.setFillForegroundColor(new XSSFColor(rgb, new DefaultIndexedColorMap()));
    return style;
  }
}
