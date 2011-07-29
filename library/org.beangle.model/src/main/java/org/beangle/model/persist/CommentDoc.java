/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.commons.text.inflector.Pluralizer;
import org.beangle.commons.text.inflector.lang.en.EnNounPluralizer;
import org.beangle.model.persist.hibernate.support.TableNameConfig;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.RootDoc;

/**
 * 扫描@Entity头和字段的注释,生成sql语句
 * 
 * @author chaostone
 * @version $Id: ListTags.java Jul 30, 2011 12:42:54 AM chaostone $
 */

public class CommentDoc {

	public static boolean start(RootDoc root) throws IOException {
		ClassDoc[] classes = root.classes();
		NamingStrategy strategy = new NamingStrategy();
		strategy.setTableNameConfig(new SimpleTableNameConfig());

		// out files
		String commentFileName = System.getProperty("java.io.tmpdir") + "/comment.sql";
		String commentLogFileName = System.getProperty("java.io.tmpdir") + "/comment_log.txt";
		BufferedWriter out = new BufferedWriter(new FileWriter(commentFileName));
		BufferedWriter log = new BufferedWriter(new FileWriter(commentLogFileName));

		// reserved types
		Set<String> multiTypes = CollectUtils.newHashSet("Set", "Map", "List");
		Set<String> scalaTypes = CollectUtils.newHashSet("String", "Integer", "int", "Long", "long", "Float",
				"float", "Double", "double", "Date", "DateTime", "Calendar", "BigDecimal", "Boolean",
				"boolean");

		// nocomment
		int nocomment = 0;
		StringBuilder nocomments = new StringBuilder();

		for (int i = 0; i < classes.length; i++) {
			if (!isEntity(classes[i])) continue;
			ClassDoc classDoc = classes[i];

			// generate table name
			strategy.generatePrefix(classDoc.name());
			String tableName = getTableName(classDoc);
			if (null != tableName) {
				tableName = strategy.tableName(tableName);
			} else {
				tableName = strategy.classToTableName(classDoc.name());
			}

			// find table comment
			String tableComment = processComment(classDoc.commentText());
			if (null != tableComment) {
				out.write("\ncomment on table " + tableName + " is '" + tableComment + "';\n");
			} else {
				nocomments.append(classDoc.name()).append('\n');
				nocomment++;
			}

			// process classDoc and it's superClassDoc
			while (null != classDoc) {
				if (classDoc.simpleTypeName().equals("Object")) break;
				FieldDoc[] fields = classDoc.fields(false);
				for (int j = 0; j < fields.length; j++) {
					FieldDoc field = fields[j];
					if (field.isTransient() || field.isStatic() || field.isEnum()) continue;
					String simpleTypeName = field.type().simpleTypeName();
					if (multiTypes.contains(simpleTypeName)) continue;

					String columnComment = processComment(fields[j].commentText());

					if (null != columnComment) {
						String columnName = StrUtils.unCamel(fields[j].name(), '_', true);
						if (!scalaTypes.contains(simpleTypeName)) {
							columnName = columnName + "_id";
							columnComment = columnComment + "ID";
						}
						out.write("comment on column " + tableName + "." + columnName + " is '"
								+ columnComment + "';\n");
					} else {
						if (null != fields[j].position() && fields[j].position().column() > 0) {
							nocomments.append(classDoc.name()).append('.').append(fields[j].name())
									.append('\n');
							nocomment++;
						}
					}
				}
				classDoc = (null == classDoc.superclassType()) ? null : classDoc.superclassType()
						.asClassDoc();
			}
		}

		// summary
		if (nocomment > 0) log.write("Find " + nocomment + " properties without comment.\n");
		else log.write("Congratulations! All entity properties have valid comment.\n");
		log.write(nocomments.toString());

		// cleanup
		out.close();
		log.close();
		System.out.println("Generated comment sqlfile:" + commentFileName);
		System.out.println("Generated comment logfile:" + commentLogFileName);
		return true;
	}

	private static String processComment(String comment) {
		if (StringUtils.isEmpty(comment)) return null;
		int newlineIndex = comment.indexOf('\n');
		if (newlineIndex > 0) {
			comment = comment.substring(0, newlineIndex);
		}
		return comment.trim();
	}

	private static boolean isEntity(ClassDoc classDoc) {
		AnnotationDesc[] anns = classDoc.annotations();
		if (null == anns || anns.length == 0) return false;
		for (AnnotationDesc anno : anns) {
			if ("Entity".equals(anno.annotationType().name())) return true;
		}
		return false;
	}

	private static String getTableName(ClassDoc classDoc) {
		AnnotationDesc[] anns = classDoc.annotations();
		if (null == anns || anns.length == 0) return null;
		for (AnnotationDesc anno : anns) {
			if ("Table".equals(anno.annotationType().name())) {
				AnnotationDesc.ElementValuePair[] pairs = anno.elementValues();
				if (null != pairs && pairs.length > 0) {
					for (AnnotationDesc.ElementValuePair pair : pairs) {
						if (pair.element().name().equals("name")) return pair.value().value().toString();
					}
				}
			}
		}
		return null;
	}
}

class NamingStrategy {

	private Pluralizer pluralizer = new EnNounPluralizer();

	private TableNameConfig tableNameConfig;

	private String tblPrefix;

	public String classToTableName(String className) {
		if (className.endsWith("Bean")) {
			className = StringUtils.substringBeforeLast(className, "Bean");
		}
		String tableName = addUnderscores(unqualify(className));

		if (null != pluralizer) {
			tableName = pluralizer.pluralize(tableName);
		}
		if (null != tblPrefix) {
			tableName = tblPrefix + tableName;
		}
		return tableName;
	}

	public void generatePrefix(String className) {
		tblPrefix = tableNameConfig.getPrefix(className);
	}

	public String tableName(String tableName) {
		String newName = tableName;
		if (null != tblPrefix) {
			if (!tableName.startsWith(tblPrefix)) {
				newName = tblPrefix + tableName;
			}
		}
		return newName;
	}

	public void setPluralizer(Pluralizer pluralizer) {
		this.pluralizer = pluralizer;
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

}

class SimpleTableNameConfig implements TableNameConfig {
	public String getSchema(String packageName) {
		return null;
	}

	public String getPrefix(String packageName) {
		return "se_";
	}
}
