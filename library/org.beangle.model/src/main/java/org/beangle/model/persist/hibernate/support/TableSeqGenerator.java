/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.hibernate.support;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.hibernate.dialect.Dialect;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.type.Type;

/**
 * 按照表明进行命名序列<br>
 * 依据命名模式进行，默认模式{table}_seq<br>
 * 该生成器可以
 * 
 * <pre>
 * 1)具有较好的数据库移植性，支持没有sequence的数据库。
 * 2)可以通过设置优化起进行优化
 * 3)可以按照表名进行自动命名序列名，模式{table}_seq
 * </pre>
 * 
 * @author chaostone
 */
public class TableSeqGenerator extends SequenceStyleGenerator {

	/** 序列命名模式 */
	private String sequencePattern = "seq_{table}";

	/**
	 * If the parameters do not contain a {@link SequenceGenerator#SEQUENCE} name, we assign one
	 * based on the table name.
	 */
	public void configure(Type type, Properties params, Dialect dialect) {
		if (StringUtils.isEmpty(params.getProperty(SEQUENCE_PARAM))) {
			String tableName = params.getProperty(PersistentIdentifierGenerator.TABLE);
			String pk = params.getProperty(PersistentIdentifierGenerator.PK);
			if (tableName != null) {
				String seqName = StringUtils.replace(sequencePattern, "{table}", tableName);
				seqName = StringUtils.replace(seqName, "{pk}", pk);
				params.setProperty(SEQUENCE_PARAM, seqName);
			}
		}
		super.configure(type, params, dialect);
	}

	public String getSequencePattern() {
		return sequencePattern;
	}

	public void setSequencePattern(String sequencePattern) {
		this.sequencePattern = sequencePattern;
	}

}
