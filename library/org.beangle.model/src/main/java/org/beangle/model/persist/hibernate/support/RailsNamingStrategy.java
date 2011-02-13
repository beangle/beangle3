/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.hibernate.support;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.commons.text.inflector.Pluralizer;
import org.beangle.commons.text.inflector.lang.en.EnNounPluralizer;
import org.hibernate.AssertionFailure;
import org.hibernate.cfg.DefaultNamingStrategy;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类似Rails的数据库表名、列名命名策略
 * 
 * @see DefaultNamingStrategy the default strategy
 * @author chaostone
 */
public class RailsNamingStrategy implements NamingStrategy, Serializable {
	private static final long serialVersionUID = -2656604564223895758L;

	private static final Logger logger = LoggerFactory.getLogger(RailsNamingStrategy.class);
	/**
	 * 是否对表名进行复数化
	 */
	private boolean enablePluralize = true;

	private Pluralizer pluralizer = new EnNounPluralizer();

	private TableNameConfig tableNameConfig;

	private String tblPrefix;

	/**
	 * 根据实体名(entityName)命名表
	 * 
	 * @param className
	 */
	public String classToTableName(String className) {
		if (className.endsWith("Bean")) {
			className = StringUtils.substringBeforeLast(className, "Bean");
		}
		String tableName = addUnderscores(unqualify(className));
		if (enablePluralize) {
			tableName = pluralizer.pluralize(tableName);
		}
		tblPrefix = tableNameConfig.getPrefix(className);
		if (null != tblPrefix) {
			tableName = tblPrefix + tableName;
		}
		if (tableName.length() > 30) {
			logger.warn("{}'s length has greate more then 30, database will not be supported!", tableName);
		}
		return tableName;
	}

	/**
	 * 对自动起名和使体内集合配置的表名，添加前缀
	 * 
	 * <pre>
	 * 配置好的实体表名和关联表的名字都会经过此方法。
	 * </re>
	 */
	public String tableName(String tableName) {
		checkNewEntity();
		String newName = tableName;
		if (null != tblPrefix) {
			if (!tableName.startsWith(tblPrefix)) {
				newName = tblPrefix + tableName;
			}
		}
		return newName;
	}

	/**
	 * 对配置文件起好的列名,不进行处理
	 */
	public String columnName(String columnName) {
		return columnName;
	}

	/**
	 * 数据列的逻辑名
	 * 
	 * <pre>
	 * 如果有列名，不做处理，否则按照属性自动起名.
	 * 该策略保证columnName=logicalColumnName
	 * </pre>
	 */
	public String logicalColumnName(String columnName, String propertyName) {
		return StringHelper.isNotEmpty(columnName) ? columnName : propertyToColumnName(propertyName);
	}

	/**
	 * 根据属性名自动起名
	 * 
	 * <pre>
	 * 将混合大小写，带有.分割的属性描述，转换成下划线分割的名称。
	 * 属性名字包括：简单属性、集合属性、组合属性(component.name)
	 * </pre>
	 * 
	 * @param propertyName
	 */
	public String propertyToColumnName(String propertyName) {
		StringBuilder sb = new StringBuilder(addUnderscores(unqualify(propertyName)));
		if (!StringUtils.endsWithIgnoreCase(propertyName, "id") && isManyToOne()) {
			sb.append("_id");
		}
		return sb.toString();
	}

	public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity,
			String associatedEntityTable, String propertyName) {
		return tableName(ownerEntityTable + '_') + addUnderscores(unqualify(propertyName));
	}

	/**
	 * Return the argument
	 */
	public String joinKeyColumnName(String joinedColumn, String joinedTable) {
		return columnName(joinedColumn);
	}

	/**
	 * Return the property name or propertyTableName
	 */
	public String foreignKeyColumnName(String propertyName, String propertyEntityName,
			String propertyTableName, String referencedColumnName) {
		String header = null == propertyName ? propertyTableName : unqualify(propertyName);
		if (header == null) { throw new AssertionFailure("NamingStrategy not properly filled"); }
		return columnName(header) + "_" + referencedColumnName;
	}

	/**
	 * Returns either the table name if explicit or if there is an associated
	 * table, the concatenation of owner entity table and associated table
	 * otherwise the concatenation of owner entity table and the unqualified
	 * property name
	 */
	public String logicalCollectionTableName(String tableName, String ownerEntityTable,
			String associatedEntityTable, String propertyName) {
		if (tableName == null) {
			// use of a stringbuilder to workaround a JDK bug
			return new StringBuilder(ownerEntityTable).append("_")
					.append(associatedEntityTable == null ? unqualify(propertyName) : associatedEntityTable)
					.toString();
		} else {
			return tableName;
		}
	}

	/**
	 * Return the column name if explicit or the concatenation of the property
	 * name and the referenced column
	 */
	public String logicalCollectionColumnName(String columnName, String propertyName, String referencedColumn) {
		return StringHelper.isNotEmpty(columnName) ? columnName : unqualify(propertyName) + "_"
				+ referencedColumn;
	}

	public Pluralizer getPluralizer() {
		return pluralizer;
	}

	public void setPluralizer(Pluralizer pluralizer) {
		this.pluralizer = pluralizer;
	}

	public boolean isPluralizeTableName() {
		return enablePluralize;
	}

	public void setPluralizeTableName(boolean pluralizeTableName) {
		this.enablePluralize = pluralizeTableName;
	}

	public TableNameConfig getTableNameConfig() {
		return tableNameConfig;
	}

	public void setTableNameConfig(TableNameConfig tableNameConfig) {
		this.tableNameConfig = tableNameConfig;
	}

	protected static String addUnderscores(String name) {
		return StrUtils.unCamel(name.replace('.', '_'), '_');
	}

	protected static String unqualify(String qualifiedName) {
		int loc = qualifiedName.lastIndexOf('.');
		return (loc < 0) ? qualifiedName : qualifiedName.substring(loc + 1);
	}

	/**
	 * 检查当前堆栈是否正在为一个新的实体调用类名和表名转换
	 */
	private void checkNewEntity() {
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		if (trace.length > 4) {
			if (trace[3].getMethodName().equals("getClassTableName")
					|| trace[4].getMethodName().equals("getClassTableName")) {
				this.tblPrefix = null;
			}
		}
	}

	/**
	 * 检查是否为ManyToOne调用
	 * 
	 * @return
	 */
	private boolean isManyToOne() {
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		if (trace.length >= 7) {
			for (int i = 5; i <= 7; i++) {
				if (trace[i].getMethodName().equals("bindManyToOne")) { return true; }
			}
		}
		return false;
	}
}
