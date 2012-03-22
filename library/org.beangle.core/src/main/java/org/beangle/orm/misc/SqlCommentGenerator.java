/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.orm.misc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.collection.CollectUtils;
import org.beangle.lang.StrUtils;
import org.beangle.orm.DefaultTableNamingStrategy;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.RootDoc;

/**
 * 扫描@Entity头和字段的注释,生成sql语句
 * <p>
 * pom文件中添加一下配置
 * 
 * <pre>
 * &lt;plugin&gt;
 *  &lt;groupId&gt;org.apache.maven.plugins&lt;/groupId&gt;
 *  &lt;artifactId&gt;maven-javadoc-plugin&lt;/artifactId&gt;
 *  &lt;version&gt;2.7&lt;/version&gt;
 *  &lt;inherited&gt;false&lt;/inherited&gt;
 *  &lt;configuration&gt;
 *   &lt;charset&gt;UTF-8&lt;/charset&gt;
 *   &lt;doclet&gt;org.beangle.orm.misc.SqlCommentGenerator&lt;/doclet&gt;
 *   &lt;docletArtifact&gt;
 *    &lt;groupId&gt;org.beangle&lt;/groupId&gt;
 *    &lt;artifactId&gt;beangle-core&lt;/artifactId&gt;
 *    &lt;version&gt;2.5.0-SNAPSHOT&lt;/version&gt;
 *   &lt;/docletArtifact&gt;
 *   &lt;additionalparam&gt;-config /your/path/to/table.properties&lt;/additionalparam&gt;
 *   &lt;useStandardDocletOptions&gt;false&lt;/useStandardDocletOptions&gt;
 *  &lt;/configuration&gt;
 * &lt;/plugin&gt;
 * </pre>
 * 
 * @author chaostone
 * @version $Id: ListTags.java Jul 30, 2011 12:42:54 AM chaostone $
 */

public class SqlCommentGenerator {

	private static final String CONFIG = "config";
	private static final String SQLFILE = "file";

	private static DefaultTableNamingStrategy strategy;
	private static Set<String> primaryTypes = CollectUtils.newHashSet("String", "int", "long", "float",
			"double", "boolean");
	// reserved types
	private static Set<String> collectionTypes = CollectUtils.newHashSet("Set", "Map", "List");

	public static boolean start(RootDoc root) throws IOException {
		ClassDoc[] classes = root.classes();

		// out files
		String commentFileName = getFileOption(root.options());
		String commentLogFileName = System.getProperty("java.io.tmpdir") + "/comment_log.txt";
		BufferedWriter out = new BufferedWriter(new FileWriter(commentFileName));
		BufferedWriter log = new BufferedWriter(new FileWriter(commentLogFileName));

		// nocomment
		int nocomment = 0;
		StringBuilder nocomments = new StringBuilder();

		for (int i = 0; i < classes.length; i++) {
			if (!isEntity(classes[i])) continue;
			ClassDoc classDoc = classes[i];

			// generate table name
			String tableName = getTableName(classDoc);
			if (null == tableName) {
				System.out.println("Cannot find tablenam for " + classDoc.qualifiedName());
				continue;
			}
			// find table comment
			String tableComment = processComment(classDoc.commentText());
			if (null != tableComment) {
				out.write("\ncomment on table " + tableName + " is '" + tableComment + "';\n");
			} else {
				nocomments.append(classDoc.qualifiedName()).append('\n');
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

					String columnComment = processComment(fields[j].commentText());
					if (null != columnComment) {
						if (collectionTypes.contains(simpleTypeName)) {
							String joinTable = collectionTable(classDoc, tableName, field);
							if (null != joinTable) {
								out.write("\ncomment on table " + joinTable + " is '" + tableComment + "-"
										+ columnComment + "';\n");
							}
						} else {
							String columnName = getColumnName(fields[j]);
							if (isEntity(field)) {
								columnName = columnName + "_id";
								columnComment = columnComment + "ID";
							}
							out.write("comment on column " + tableName + "." + columnName + " is '"
									+ columnComment + "';\n");
						}
					} else {
						if (null != fields[j].position() && fields[j].position().column() > 0) {
							nocomments.append(classDoc.qualifiedName()).append('.').append(fields[j].name())
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

	private static String collectionTable(ClassDoc classDoc, String tableName, FieldDoc fieldDoc) {
		String joinTable = null;
		AnnotationDesc[] anns = fieldDoc.annotations();
		if (null != anns && anns.length > 0) {
			boolean find = false;
			for (AnnotationDesc anno : anns) {
				if ("ManyToMany".equals(anno.annotationType().name())) {
					find = true;
					AnnotationDesc.ElementValuePair[] pairs = anno.elementValues();
					if (null != pairs && pairs.length > 0) {
						for (AnnotationDesc.ElementValuePair pair : pairs) {
							if (pair.element().name().equals("mappedBy")) {
								find = false;
								break;
							}
						}
					}
				}
				if ("JoinTable".equals(anno.annotationType().name())) {
					AnnotationDesc.ElementValuePair[] pairs = anno.elementValues();
					if (null != pairs && pairs.length > 0) {
						for (AnnotationDesc.ElementValuePair pair : pairs) {
							if (pair.element().name().equals("name")) {
								joinTable = pair.value().value().toString();
								break;
							}
						}
					}
				}
			}
			if (find && null == joinTable) {
				joinTable = strategy.collectionToTableName(classDoc.qualifiedName(), tableName,
						fieldDoc.name());
			}
		}
		return joinTable;
	}

	private static boolean isEntity(ClassDoc classDoc) {
		AnnotationDesc[] anns = classDoc.annotations();
		if (null == anns || anns.length == 0) return false;
		for (AnnotationDesc anno : anns) {
			if ("Entity".equals(anno.annotationType().name())) return true;
		}
		return false;
	}

	private static boolean isEntity(FieldDoc fieldDoc) {
		String className = fieldDoc.type().qualifiedTypeName();
		if (StringUtils.startsWith(className, "java.")) return false;
		if (primaryTypes.contains(className)) return false;
		AnnotationDesc[] anns = fieldDoc.annotations();
		if (null != anns && anns.length > 0) {
			for (AnnotationDesc anno : anns) {
				if ("Enumerated".equals(anno.annotationType().name())
						|| "Embedded".equals(anno.annotationType().name())) return false;
			}
		}
		return true;
	}

	private static String getTableName(ClassDoc classDoc) {
		AnnotationDesc[] anns = classDoc.annotations();
		if (null != anns && anns.length >= 0) {
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
		}
		return strategy.classToTableName(classDoc.qualifiedName());
	}

	private static String getColumnName(FieldDoc fieldDoc) {
		AnnotationDesc[] anns = fieldDoc.annotations();
		if (null != anns && anns.length >= 0) {
			for (AnnotationDesc anno : anns) {
				if ("Column".equals(anno.annotationType().name())) {
					AnnotationDesc.ElementValuePair[] pairs = anno.elementValues();
					if (null != pairs && pairs.length > 0) {
						for (AnnotationDesc.ElementValuePair pair : pairs) {
							if (pair.element().name().equals("name")) return pair.value().value().toString();
						}
					}
				}
			}
		}
		return StrUtils.unCamel(fieldDoc.name(), '_', true);
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
		strategy = new DefaultTableNamingStrategy();
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
					((DefaultTableNamingStrategy) strategy).addConfig(file.toURI().toURL());
				}
			}
		}
		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader()
				.getResources("/META-INF/beangle/table.properties");
		while (urls.hasMoreElements()) {
			strategy.addConfig(urls.nextElement());
		}
		if (strategy.getPatterns().isEmpty()) {
			reporter.printError("Cannot find table.properties in classpath or options. Using -config /your/path/to/table.properties");
			return false;
		} else {
			System.out.println(strategy);
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
