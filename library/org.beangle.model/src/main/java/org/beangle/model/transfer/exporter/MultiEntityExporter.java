/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.exporter;

import java.util.List;

import org.beangle.model.transfer.TransferMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 多个实体集合导出器。
 * <p>
 * 每个实体的数据List组成一个新的List，作为导出的items.
 * 
 * @author chaostone
 */
public class MultiEntityExporter extends AbstractItemExporter {
	private static final Logger logger = LoggerFactory.getLogger(MultiEntityExporter.class);
	/**
	 * 属性提取器
	 */
	protected PropertyExtractor propertyExtractor;

	protected List<Metadata> metadatas = null;

	public static class Metadata {
		String dateName;
		String[] attrs;
		String[] titles;

		public Metadata(String dateName, String[] attrs, String[] titles) {
			super();
			this.dateName = dateName;
			this.attrs = attrs;
			this.titles = titles;
		}
	}

	@SuppressWarnings("unchecked")
	protected boolean beforeExport() {
		if (null == propertyExtractor) {
			propertyExtractor = context.get("extractor", PropertyExtractor.class);
		}
		if (null == metadatas) {
			metadatas = (List<Metadata>) context.get("metadatas");
		}
		if (null == metadatas || null == propertyExtractor) {
			logger.error("without metadatas or propertyExtractor,exporter stopped!");
			return false;
		}
		return true;
	}

	public void transferItem() {
		Metadata metadata = metadatas.get(index);
		List<?> values = (List<?>) ((List<?>) context.get("items")).get(index);
		getWriter().writeTitle(metadata.dateName, metadata.titles);
		Object[] propValues = new Object[metadata.attrs.length];
		for (Object item : values) {
			for (int i = 0; i < propValues.length; i++) {
				try {
					propValues[i] = propertyExtractor.getPropertyValue(item, metadata.attrs[i]);
				} catch (Exception e) {
					transferResult.addFailure(TransferMessage.ERROR_ATTRS_EXPORT, "occur in get property :"
							+ metadata.attrs[i] + " and exception:" + e.getMessage());
				}
			}
			writer.write(propValues);
		}
	}

	public PropertyExtractor getPropertyExtractor() {
		return propertyExtractor;
	}

	public void setPropertyExtractor(PropertyExtractor propertyExporter) {
		this.propertyExtractor = propertyExporter;
	}
}
