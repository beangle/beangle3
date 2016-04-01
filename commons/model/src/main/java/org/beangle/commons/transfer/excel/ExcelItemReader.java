/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.transfer.io.ItemReader;
import org.beangle.commons.transfer.io.TransferFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Excel的每行一条数据的读取器
 * 
 * @author chaostone
 * @version $Id: $
 */
public class ExcelItemReader implements ItemReader {

  /** Constant <code>logger</code> */
  public static final Logger logger = LoggerFactory.getLogger(ExcelItemReader.class);

  /** 标题缺省所在行 */
  public static int DEFAULT_HEADINDEX = 0;

  /** Constant <code>numberFormat</code> */
  public static final NumberFormat numberFormat;

  static {
    numberFormat = NumberFormat.getInstance();
    numberFormat.setGroupingUsed(false);
  }
  /** Constant <code>sheetNum=0</code> */
  public final int sheetNum = 0;

  /** 标题所在行 */
  private int headIndex;

  /** 数据起始行 */
  private int dataIndex;

  /**
   * 下一个要读取的位置 标题行和代码行分别默认占据0,1
   */
  private int indexInSheet;

  /**
   * 属性的个数，0表示在读取值的是否不做读限制
   */
  private int attrCount = 0;

  /**
   * 读取的工作表
   */
  private HSSFWorkbook workbook;

  /**
   * <p>
   * Constructor for ExcelItemReader.
   * </p>
   */
  public ExcelItemReader() {
  }

  /**
   * <p>
   * Constructor for ExcelItemReader.
   * </p>
   * 
   * @param is a {@link java.io.InputStream} object.
   */
  public ExcelItemReader(InputStream is) {
    this(is, DEFAULT_HEADINDEX);
  }

  /**
   * <p>
   * Constructor for ExcelItemReader.
   * </p>
   * 
   * @param is a {@link java.io.InputStream} object.
   * @param headIndex a int.
   */
  public ExcelItemReader(InputStream is, int headIndex) {
    try {
      init(new HSSFWorkbook(is), headIndex, headIndex + 1);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * <p>
   * Constructor for ExcelItemReader.
   * </p>
   * 
   * @param workbook a {@link org.apache.poi.hssf.usermodel.HSSFWorkbook} object.
   * @param headIndex a int.
   */
  public ExcelItemReader(HSSFWorkbook workbook, int headIndex) {
    init(workbook, headIndex, headIndex + 1);
  }

  private void init(HSSFWorkbook workbook, int headIndex, int dataIndex) {
    assert workbook != null;
    this.workbook = workbook;
    this.headIndex = headIndex;
    this.dataIndex = dataIndex;
    this.indexInSheet = dataIndex;
  }

  /**
   * <p>
   * Setter for the field <code>workbook</code>.
   * </p>
   * 
   * @param wb a {@link org.apache.poi.hssf.usermodel.HSSFWorkbook} object.
   */
  public void setWorkbook(HSSFWorkbook wb) {
    this.workbook = wb;
  }

  /**
   * 描述放在第一行
   * 
   * @return an array of {@link java.lang.String} objects.
   */
  public String[] readDescription() {
    if (workbook.getNumberOfSheets() < 1) {
      return new String[0];
    } else {
      HSSFSheet sheet = workbook.getSheetAt(0);
      return readLine(sheet, 0);
    }
  }

  /**
   * <p>
   * readTitle.
   * </p>
   * 
   * @return an array of {@link java.lang.String} objects.
   */
  public String[] readTitle() {
    if (workbook.getNumberOfSheets() < 1) {
      return new String[0];
    } else {
      HSSFSheet sheet = workbook.getSheetAt(0);
      String[] attrs = readLine(sheet, headIndex);
      attrCount = attrs.length;
      return attrs;
    }
  }

  /**
   * 遇到空白单元格停止的读行操作
   * 
   * @param sheet a {@link org.apache.poi.hssf.usermodel.HSSFSheet} object.
   * @param rowIndex a int.
   * @return an array of {@link java.lang.String} objects.
   */
  protected String[] readLine(HSSFSheet sheet, int rowIndex) {
    HSSFRow row = sheet.getRow(rowIndex);
    logger.debug("values count:{}", row.getLastCellNum());
    List<String> attrList = CollectUtils.newArrayList();
    for (int i = 0; i < row.getLastCellNum(); i++) {
      HSSFCell cell = row.getCell(i);
      if (null != cell) {
        String attr = cell.getRichStringCellValue().getString();
        if (Strings.isEmpty(attr)) {
          break;
        } else {
          attrList.add(attr.trim());
        }
      } else {
        break;
      }
    }
    String[] attrs = new String[attrList.size()];
    attrList.toArray(attrs);
    logger.debug("has attrs {}", attrs);
    return attrs;
  }

  /**
   * <p>
   * read.
   * </p>
   * 
   * @return a {@link java.lang.Object} object.
   */
  public Object read() {
    HSSFSheet sheet = workbook.getSheetAt(sheetNum);
    if (indexInSheet > sheet.getLastRowNum()) { return null; }
    HSSFRow row = sheet.getRow(indexInSheet);
    indexInSheet++;
    // 如果是个空行,返回空记录
    if (row == null) {
      return new Object[attrCount];
    } else {
      Object[] values = new Object[((attrCount != 0) ? attrCount : row.getLastCellNum())];
      for (int k = 0; k < values.length; k++) {
        values[k] = getCellValue(row.getCell(k));
      }
      return values;
    }
  }

  /**
   * 取cell单元格中的数据
   * 
   * @param cell a {@link org.apache.poi.hssf.usermodel.HSSFCell} object.
   * @return a {@link java.lang.Object} object.
   */
  public static Object getCellValue(HSSFCell cell) {
    if ((cell == null)) return null;
    switch (cell.getCellType()) {
    case HSSFCell.CELL_TYPE_BLANK:
      return null;
    case HSSFCell.CELL_TYPE_STRING:
      return Strings.trim(cell.getRichStringCellValue().getString());
    case HSSFCell.CELL_TYPE_NUMERIC:
      if (DateUtil.isCellDateFormatted(cell)) {
        return cell.getDateCellValue();
      } else {
        return numberFormat.format(cell.getNumericCellValue());
      }
    case HSSFCell.CELL_TYPE_BOOLEAN:
      return (cell.getBooleanCellValue()) ? Boolean.TRUE : Boolean.FALSE;
    default:
      // cannot handle HSSFCell.CELL_TYPE_ERROR,HSSFCell.CELL_TYPE_FORMULA
      return null;
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
    return TransferFormat.Xls;
  }

  /**
   * <p>
   * Getter for the field <code>headIndex</code>.
   * </p>
   * 
   * @return a int.
   */
  public int getHeadIndex() {
    return headIndex;
  }

  /** {@inheritDoc} */
  public void setHeadIndex(int headIndex) {
    if (this.dataIndex == this.headIndex + 1) {
      setDataIndex(headIndex + 1);
    }
    this.headIndex = headIndex;
  }

  /**
   * <p>
   * Getter for the field <code>dataIndex</code>.
   * </p>
   * 
   * @return a int.
   */
  public int getDataIndex() {
    return dataIndex;
  }

  /** {@inheritDoc} */
  public void setDataIndex(int dataIndex) {
    if (this.dataIndex == this.indexInSheet) {
      this.dataIndex = dataIndex;
      this.indexInSheet = dataIndex;
    }
  }

  @Override
  public void close() {
    this.workbook.cloneSheet(sheetNum);
  }

}
