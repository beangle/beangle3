/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.transfer.exporter;

import org.apache.commons.lang.StringUtils;

public class SimpleItemExporter extends AbstractItemExporter {
	/** 导出属性对应的标题 */
	protected String[] titles;

	public String[] getTitles() {
		return titles;
	}

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	@Override
	protected boolean beforeExport() {
		if (null == titles) {
			String contextTitle = context.get("titles", String.class);
			if (null != contextTitle) {
				titles = StringUtils.split(contextTitle, ",");
			}
		}
		if (null == titles || titles.length == 0) return false;
		writer.writeTitle(null, titles);
		return true;
	}

}
