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
package org.beangle.commons.transfer.excel;

import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.beangle.commons.lang.Numbers;
import org.beangle.commons.transfer.exporter.Context;
import org.beangle.commons.transfer.io.AbstractItemWriter;
import org.beangle.commons.transfer.io.TransferFormat;

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

  protected HSSFWorkbook workbook = new HSSFWorkbook(); // 建立新HSSFWorkbook对象

  protected int index = 0;

  protected HSSFSheet sheet;

  protected HSSFCellStyle dateStyle = null;

  protected HSSFCellStyle timeStyle = null;

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

  /** {@inheritDoc} */
  public void write(Object obj) {
    if (index + 1 >= countPerSheet) {
      writeTitle(null, title);
    }
    writeItem(obj);
    index++;
  }

  /** {@inheritDoc} */
  public void writeTitle(String titleName, Object data) {
    if (null != titleName) {
      sheet = workbook.createSheet(titleName);
    } else {
      sheet = workbook.createSheet();
    }
    title = data;
    index = 0;
    writeItem(data);
    HSSFRow titleRow = sheet.getRow(index);
    HSSFCellStyle titleStyle = getTitleStyle();
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
    HSSFRow row = sheet.createRow(index); // 建立新行
    if (datas != null) {
      if (datas.getClass().isArray()) {
        Object[] values = (Object[]) datas;
        for (int i = 0; i < values.length; i++) {
          HSSFCell cell = row.createCell(i);
          if (values[i] instanceof Number) {
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
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
            cell.setCellValue(new HSSFRichTextString((values[i] == null) ? "" : values[i].toString()));
          }
        }
      } else {
        HSSFCell cell = row.createCell(0);
        // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        if (datas instanceof Number) {
          cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        }
        cell.setCellValue(new HSSFRichTextString(datas.toString()));
      }
    }
  }

  /**
   * <p>
   * close.
   * </p>
   */
  public void close() {
    try {
      workbook.write(outputStream);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
    }
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

  private HSSFCellStyle getDateStyle() {
    if (null == dateStyle) {
      dateStyle = workbook.createCellStyle();
      dateStyle.setDataFormat(workbook.createDataFormat().getFormat(getDateFormat()));
    }
    return dateStyle;
  }

  private HSSFCellStyle getTimeStyle() {
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
   * <p>
   * getTitleStyle.
   * </p>
   * 
   * @return a {@link org.apache.poi.hssf.usermodel.HSSFCellStyle} object.
   */
  protected HSSFCellStyle getTitleStyle() {
    HSSFCellStyle style = workbook.createCellStyle();
    // HSSFFont f = workbook.createFont();
    // f.setFontHeightInPoints((short ) 10 ); //字号
    // f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
    // style.setFont(f);
    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
    style.setFillPattern(HSSFCellStyle.FINE_DOTS);
    style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
    style.setFillBackgroundColor(HSSFColor.LIGHT_BLUE.index);
    return style;
  }
}
