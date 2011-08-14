/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.comment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.lang.StrUtils;

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

public class I18nGenerator {

	public static boolean start(RootDoc root) throws IOException {
		ClassDoc[] classes = root.classes();

		// out files
		String enUsFile = System.getProperty("java.io.tmpdir") + "/i18n_en_US.txt";
		String zhCnFile = System.getProperty("java.io.tmpdir") + "/i18n_zh_CN.txt";
		String logFile = System.getProperty("java.io.tmpdir") + "/comment_log.txt";
		BufferedWriter enOut = new BufferedWriter(new FileWriter(enUsFile));
		BufferedWriter zhOut = new BufferedWriter(new FileWriter(zhCnFile));
		BufferedWriter log = new BufferedWriter(new FileWriter(logFile));

		// nocomment
		int nocomment = 0;
		StringBuilder nocomments = new StringBuilder();

		for (int i = 0; i < classes.length; i++) {
			if (!isEntity(classes[i])) continue;
			ClassDoc classDoc = classes[i];

			String className = getEntityName(classDoc);
			// find table comment
			String comments = processComment(classDoc.commentText());
			if (null != comments) {
				enOut.write("\nentity." + className + "=" + StrUtils.unCamel(StringUtils.capitalize(className), ' ',false) + "\n");
				zhOut.write("\nentity." + className + "=" + comments + "\n");
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
					String columnComment = processComment(fields[j].commentText());

					if (null != columnComment) {
						String columnName = StrUtils.unCamel(StringUtils.capitalize(fields[j].name()), ' ', false);
						enOut.write(className + "." + fields[j].name() + "=" + columnName + "\n");
						zhOut.write(className + "." + fields[j].name() + "=" + columnComment + "\n");
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
		enOut.close();
		zhOut.close();
		log.close();
		System.out.println("Generated file:" + enUsFile + "," + enUsFile);
		System.out.println("Generated comment logfile:" + logFile);
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

	private static String getEntityName(ClassDoc classDoc) {
		AnnotationDesc[] anns = classDoc.annotations();
		if (null == anns || anns.length == 0) return null;
		String entityName = null;
		for (AnnotationDesc anno : anns) {
			if ("Entity".equals(anno.annotationType().name())) {
				AnnotationDesc.ElementValuePair[] pairs = anno.elementValues();
				if (null != pairs && pairs.length > 0) {
					for (AnnotationDesc.ElementValuePair pair : pairs) {
						if (pair.element().name().equals("name")) {
							entityName = pair.value().value().toString();
							break;
						}
					}
				}
				break;
			}
		}
		if (null == entityName) {
			entityName = classDoc.qualifiedName();
		}
		entityName = StringUtils.substringAfterLast(entityName, ".");
		return StringUtils.uncapitalize(entityName);
	}

	private static boolean isEntity(ClassDoc classDoc) {
		AnnotationDesc[] anns = classDoc.annotations();
		if (null == anns || anns.length == 0) return false;
		for (AnnotationDesc anno : anns) {
			if ("Entity".equals(anno.annotationType().name())) return true;
		}
		return false;
	}

	/**
	 * Doclet method called by Javadoc to recognize
	 * custom parameters.
	 */
	public static int optionLength(String option) {
		return 0;
	}
}
