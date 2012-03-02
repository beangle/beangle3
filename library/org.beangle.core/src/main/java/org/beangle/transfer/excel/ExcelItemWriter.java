/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.transfer.excel;

import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.beangle.transfer.exporter.Context;
import org.beangle.transfer.io.AbstractItemWriter;
import org.beangle.transfer.io.TransferFormats;

public class ExcelItemWriter extends AbstractItemWriter {

	protected int countPerSheet = 50000;

	protected HSSFWorkbook workbook = new HSSFWorkbook(); // 建立新HSSFWorkbook对象

	protected int index = 0;

	protected HSSFSheet sheet;

	protected HSSFCellStyle dateStyle = null;

	protected HSSFCellStyle timeStyle = null;

	private Object title;

	public ExcelItemWriter() {
		super();
	}

	public ExcelItemWriter(OutputStream outputStream) {
		this();
		this.outputStream = outputStream;
	}

	public int getCountPerSheet() {
		return countPerSheet;
	}

	public void setCountPerSheet(int dataNumPerSheet) {
		this.countPerSheet = dataNumPerSheet;
	}

	public void write(Object obj) {
		if (index + 1 >= countPerSheet) {
			writeTitle(null, title);
		}
		writeItem(obj);
		index++;
	}

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

	public String getFormat() {
		return TransferFormats.XLS;
	}

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
						cell.setCellValue(new HSSFRichTextString((values[i] == null) ? "" : values[i]
								.toString()));
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

	public void close() {
		try {
			workbook.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 设置每个sheet多少条记录
	 */
	public void setContext(Context context) {
		super.setContext(context);
		if (null != context) {
			Object count = context.getDatas().get("countPerSheet");
			if (null != count && NumberUtils.isNumber(count.toString())) {
				int countParam = NumberUtils.toInt(count.toString());
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

	protected String getDateFormat() {
		return "YYYY-MM-DD";
	}

	protected String getDateTimeFormat() {
		return "YYYY-MM-DD HH:MM:SS";
	}

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
