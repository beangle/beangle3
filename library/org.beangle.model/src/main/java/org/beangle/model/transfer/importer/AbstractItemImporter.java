/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.importer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.transfer.ItemTransfer;
import org.beangle.model.transfer.io.ItemReader;
import org.beangle.model.transfer.io.Reader;

/**
 * 线性导入实现
 * 
 * @author chaostone
 */
public abstract class AbstractItemImporter extends AbstractImporter implements Importer,
		ItemTransfer {

	/** 属性说明[attr,description] */
	protected Map<String, String> descriptions = new HashMap<String, String>();

	/** 导入属性 */
	protected String[] attrs;

	/** 当前导入值[attr,value] */
	protected Map<String, Object> values = CollectUtils.newHashMap();

	public AbstractItemImporter() {
		super();
		this.prepare = new DescriptionAttrPrepare();
	}

	@Override
	public ItemReader getReader() {
		return (ItemReader) super.getReader();
	}

	/**
	 * 设置数据读取对象
	 */
	public void setReader(Reader reader) {
		if (reader instanceof ItemReader) {
			this.reader = (ItemReader) reader;
		} else {
			throw new RuntimeException("Expected LineReader but：" + reader.getClass().getName());
		}
	}

	/**
	 * 改变现有某个属性的值
	 * 
	 * @param attr
	 * @param value
	 * @return
	 */
	public void changeCurValue(String attr, Object value) {
		values.put(attr, value);
	}

	public boolean read() {
		Object[] curData = (Object[]) reader.read();
		if (null == curData) {
			setCurrent(null);
			setCurData(null);
			return false;
		} else {
			for (int i = 0; i < curData.length; i++) {
				values.put(attrs[i], curData[i]);
			}
			return true;
		}
	}

	public boolean isDataValid() {
		boolean valid = false;
		for (Object value : values.values()) {
			if (value instanceof String) {
				String tt = (String) value;
				if (StringUtils.isNotBlank(tt)) {
					valid = true;
					break;
				}
			} else {
				if (null != value) {
					valid = true;
					break;
				}
			}
		}
		return valid;
	}

	public Map<String, Object> getCurData() {
		return values;
	}

	public void setCurData(Map<String, Object> curData) {
		this.values = curData;
	}

	public String[] getAttrs() {
		return attrs;
	}

	public void setAttrs(String[] attrs) {
		this.attrs = attrs;
	}

	public Map<String, String> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(Map<String, String> descriptions) {
		this.descriptions = descriptions;
	}

	public String processAttr(String attr) {
		return attr;
	}
}
