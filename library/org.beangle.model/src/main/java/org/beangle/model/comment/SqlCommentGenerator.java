/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.comment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.commons.text.inflector.Pluralizer;
import org.beangle.commons.text.inflector.lang.en.EnNounPluralizer;
import org.beangle.model.persist.hibernate.support.DefaultTableNameConfig;
import org.beangle.model.persist.hibernate.support.TableNameConfig;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.RootDoc;

/**
 * 扫描@Entity头和字段的注释,生成sql语句
 * 
 * @author chaostone
 * @version $Id: ListTags.java Jul 30, 2011 12:42:54 AM chaostone $
 */

public class SqlCommentGenerator {

	private static final String CONFIG = "config";
	private static final String SQLFILE = "file";

	private static DefaultTableNameConfig tableNameConfig;

	public static boolean start(RootDoc root) throws IOException {
		ClassDoc[] classes = root.classes();
		NamingStrategy strategy = new NamingStrategy();
		strategy.setTableNameConfig(tableNameConfig);

		// out files
		String commentFileName = getFileOption(root.options());
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

	/**
	 * Doclet method called by Javadoc to recognize
	 * custom parameters.
	 */
	public static int optionLength(String option) {
		if (option.equals("-" + CONFIG)) return 2;
		if (option.equals("-" + SQLFILE)) return 2;
		else return 0;
	}

	public static boolean validOptions(String options[][], DocErrorReporter reporter) throws IOException {
		System.out.println("start valid options...");
		tableNameConfig = new DefaultTableNameConfig();
		for (int i = 0; i < options.length; i++) {
			String[] opt = options[i];
			if (opt[0].equals("-file")) {
				FileUtils.touch(new File(opt[1]));
			} else if (opt[0].equals("-config")) {
				String fileName = opt[1];
				if (null == fileName) continue;
				File file = new File(fileName);
				if (!file.exists()) {
					reporter.printError("config file [" + fileName + "] not exists.");
					return false;
				} else {
					((DefaultTableNameConfig) tableNameConfig).addConfig(file.toURI().toURL());
				}
			}
		}
		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader()
				.getResources("/META-INF/beangle/table.properties");
		while (urls.hasMoreElements()) {
			tableNameConfig.addConfig(urls.nextElement());
		}
		if (tableNameConfig.getPatterns().isEmpty()) {
			reporter.printError("Cannot find table.properties in classpath or options. Using -config your/path/to/table.properties");
			return false;
		} else {
			System.out.println(tableNameConfig);
			return true;
		}
	}

	private static String getFileOption(String[][] options) {
		String tagName = null;
		for (int i = 0; i < options.length; i++) {
			String[] opt = options[i];
			if (opt[0].equals("-file")) {
				tagName = opt[1];
			}
		}
		if (null == tagName) tagName = System.getProperty("java.io.tmpdir") + "/comment.sql";
		return tagName;
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
