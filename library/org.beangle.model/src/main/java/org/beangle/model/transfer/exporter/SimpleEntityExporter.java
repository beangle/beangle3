/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.exporter;

import org.apache.commons.lang.StringUtils;
import org.beangle.model.transfer.TransferMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleEntityExporter extends SimpleItemExporter {

	private static final Logger logger = LoggerFactory.getLogger(SimpleEntityExporter.class);
	/**
	 * 导入属性
	 */
	protected String[] attrs;

	/**
	 * 属性提取器
	 */
	protected PropertyExtractor propertyExtractor;

	@Override
	protected boolean beforeExport() {
		if (null == attrs) {
			String keys = context.get("keys", String.class);
			if (StringUtils.isNotBlank(keys)) {
				attrs = StringUtils.split(keys, ",");
			}
		}
		if (null == propertyExtractor) {
			propertyExtractor = context.get("extractor", PropertyExtractor.class);
		}
		if (null == attrs || null == propertyExtractor) {
			logger.debug("attrs or propertyExtractor is null,transfer data as array.");
		}
		return super.beforeExport();
	}

	/**
	 * 转换单个实体
	 */
	public void transferItem() {
		if (null == attrs) {
			super.transferItem();
			return;
		}
		Object[] values = new Object[attrs.length];
		for (int i = 0; i < values.length; i++) {
			try {
				values[i] = propertyExtractor.getPropertyValue(getCurrent(), attrs[i]);
			} catch (Exception e) {
				transferResult.addFailure(TransferMessage.ERROR_ATTRS_EXPORT, "occur in get property :"
						+ attrs[i] + " and exception:" + e.getMessage());
			}
		}
		writer.write(values);
	}

	public PropertyExtractor getPropertyExtractor() {
		return propertyExtractor;
	}

	public void setPropertyExtractor(PropertyExtractor propertyExporter) {
		this.propertyExtractor = propertyExporter;
	}

	public String[] getAttrs() {
		return attrs;
	}

	public void setAttrs(String[] attrs) {
		this.attrs = attrs;
	}
}
