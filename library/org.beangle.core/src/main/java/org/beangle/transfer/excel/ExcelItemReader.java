/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.transfer.excel;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.beangle.collection.CollectUtils;
import org.beangle.transfer.io.ItemReader;
import org.beangle.transfer.io.TransferFormats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Excel的每行一条数据的读取器
 * 
 * @author chaostone
 */
public class ExcelItemReader implements ItemReader {

	public static final Logger logger = LoggerFactory.getLogger(ExcelItemReader.class);

	/** 标题缺省所在行 */
	public static int DEFAULT_HEADINDEX = 0;

	public static final NumberFormat numberFormat;

	static {
		numberFormat = NumberFormat.getInstance();
		numberFormat.setGroupingUsed(false);
	}
	public static final int sheetNum = 0;

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

	public ExcelItemReader() {
	}

	public ExcelItemReader(InputStream is) {
		this(is, DEFAULT_HEADINDEX);
	}

	public ExcelItemReader(InputStream is, int headIndex) {
		try {
			init(new HSSFWorkbook(is), headIndex, headIndex + 1);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

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

	public void setWorkbook(HSSFWorkbook wb) {
		this.workbook = wb;
	}

	/**
	 * 描述放在第一行
	 */
	public String[] readDescription() {
		if (workbook.getNumberOfSheets() < 1) {
			return new String[0];
		} else {
			HSSFSheet sheet = workbook.getSheetAt(0);
			return readLine(sheet, 0);
		}
	}

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
	 * @param sheet
	 * @param rowIndex
	 * @return
	 */
	protected String[] readLine(HSSFSheet sheet, int rowIndex) {
		HSSFRow row = sheet.getRow(rowIndex);
		logger.debug("values count:{}", row.getLastCellNum());
		List<String> attrList = CollectUtils.newArrayList();
		for (int i = 0; i < row.getLastCellNum(); i++) {
			HSSFCell cell = row.getCell(i);
			if (null != cell) {
				String attr = cell.getRichStringCellValue().getString();
				if (StringUtils.isEmpty(attr)) {
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
	 * @see 取cell单元格中的数据
	 * @param cell
	 * @param objClass
	 * @return
	 */
	public static Object getCellValue(HSSFCell cell) {
		if ((cell == null)) return null;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_BLANK:
			return null;
		case HSSFCell.CELL_TYPE_STRING:
			return StringUtils.trim(cell.getRichStringCellValue().getString());
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

	public String getFormat() {
		return TransferFormats.XLS;
	}

	public int getHeadIndex() {
		return headIndex;
	}

	public void setHeadIndex(int headIndex) {
		if (this.dataIndex == this.headIndex + 1) {
			setDataIndex(headIndex + 1);
		}
		this.headIndex = headIndex;
	}

	public int getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(int dataIndex) {
		if (this.dataIndex == this.indexInSheet) {
			this.dataIndex = dataIndex;
			this.indexInSheet = dataIndex;
		}
	}

}
