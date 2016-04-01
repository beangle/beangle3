/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.entity.comment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Locale;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import org.beangle.commons.lang.Strings;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.ProgramElementDoc;
import com.sun.javadoc.RootDoc;

/**
 * Sql comments generator,It convert field's javadoc comment to sql comment.
 * <p>
 * Usage:
 * 
 * <pre>
 * &lt;build&gt;
 *   &lt;plugins&gt;
 *    &lt;plugin&gt;
 *        &lt;groupId&gt;org.apache.maven.plugins&lt;/groupId&gt;
 *        &lt;artifactId&gt;maven-javadoc-plugin&lt;/artifactId&gt;
 *        &lt;version&gt;2.9&lt;/version&gt;
 *        &lt;configuration&gt;
 *          &lt;charset&gt;UTF-8&lt;/charset&gt;
 *          &lt;doclet&gt;org.beangle.commons.entity.comment.CommentGenerator&lt;/doclet&gt;
 *          &lt;docletArtifact&gt;
 *            &lt;groupId&gt;org.beangle.commons&lt;/groupId&gt;
 *            &lt;artifactId&gt;beangle-commons-model&lt;/artifactId&gt;
 *            &lt;version&gt;${beangle.version}&lt;/version&gt;
 *          &lt;/docletArtifact&gt;
 *          &lt;locale&gt;
 *          zh_CN
 *          &lt;/locale&gt;
 *          &lt;useStandardDocletOptions&gt;false&lt;/useStandardDocletOptions&gt;
 *        &lt;/configuration&gt;
 *      &lt;/plugin&gt;
 *    &lt;/plugins&gt;
 *  &lt;/build&gt;
 * </pre>
 * 
 * @author chaostone
 * @since 3.2.0
 * @see https://github.com/beangle/library/wiki/Entity-Comment-Generator
 * @see http://docs.oracle.com/javase/1.4.2/docs/tooldocs/javadoc/overview.html
 * @see http://docs.oracle.com/javase/7/docs/jdk/api/javadoc/doclet/index.html
 */
public class Generator {

  private static String dir;
  private static OutputStreamWriter writer;
  private static OutputStreamWriter localeWriter;

  public static boolean start(RootDoc root) throws Exception {
    String userdir = System.getProperty("user.dir");
    dir = Strings.substringBefore(userdir, "/site/apidocs") + "/comment/";
    System.out.println("Entity comments will be generated in " + dir);
    Locale locale = Locale.getDefault();
    for (ClassDoc classDoc : root.classes()) {
      if (isAnnotationPresent(classDoc, Entity.class, MappedSuperclass.class, Embeddable.class)) {
        String className = classDoc.qualifiedTypeName();
        String packageName = Strings.substringBeforeLast(className, ".") + ".package";
        String shortName = Strings.substringAfterLast(className, ".");
        File file = new File(dir + Strings.replace(packageName, ".", "/") + ".en_US");
        if (!file.exists()) {
          file.getParentFile().mkdirs();
          file.createNewFile();
        }
        writer = new OutputStreamWriter(new FileOutputStream(file, true), "utf-8");
        if (!locale.toString().equals("en_US")) {
          File localeFile = new File(dir + Strings.replace(packageName, ".", "/") + "." + locale);
          localeWriter = new OutputStreamWriter(new FileOutputStream(localeFile, true), "utf-8");
          if (!localeFile.exists()) {
            localeFile.getParentFile().mkdirs();
            localeFile.createNewFile();
          }
        }
        String tableComment = classDoc.commentText();
        if (Strings.isNotBlank(tableComment)) comment(shortName, Strings.split(tableComment, "\n")[0]);
        else missingComment(shortName);

        for (FieldDoc fieldDoc : classDoc.fields(false)) {
          if (fieldDoc.isTransient() || fieldDoc.isStatic()) continue;

          String fieldName = fieldDoc.name();
          String fieldComment = fieldDoc.commentText();
          if (Strings.isBlank(fieldComment)) {
            missingComment(shortName + "." + fieldDoc.name());
          } else {
            fieldComment = Strings.split(fieldComment, "\n")[0];
            comment(shortName, fieldName, fieldComment);
          }
        }
        writer.close();
        if (null != localeWriter) localeWriter.close();
      }
    }
    return true;
  }

  private static String uncamel(String name) {
    String[] names = Strings.split(Strings.unCamel(name, ' '), ' ');
    int i = 0;
    while (i < names.length) {
      names[i] = Strings.capitalize(names[i]);
      i++;
    }
    return Strings.join(names, " ");
  }

  private static boolean isAnnotationPresent(ProgramElementDoc doc, Class<?>... types) {
    for (AnnotationDesc ad : doc.annotations()) {
      for (Class<?> type : types)
        if (ad.annotationType().qualifiedTypeName().equals(type.getName())) return true;
    }
    return false;
  }

  private static void comment(String shortName, String comment) throws IOException {
    String newComment = Strings.replace(comment, "<br>", "");
    writer.append("\n" + shortName + "=" + uncamel(Strings.replace(shortName, "Bean", "")) + "\n");
    if (null != localeWriter) localeWriter.append("\n" + shortName + "=" + newComment + "\n");
  }

  private static void comment(String shortName, String columnName, String comment) throws IOException {
    String newComment = Strings.replace(comment, "<br>", "");
    writer.append(shortName + "." + columnName + "=" + uncamel(columnName) + "\n");
    if (null != localeWriter) localeWriter.append(shortName + "." + columnName + "=" + newComment + "\n");
  }

  private static void missingComment(String shortName) throws IOException {
    writer.append("\n##missing  " + shortName + "\n");
    if (null != localeWriter) localeWriter.append("\n##missing  " + shortName + "\n");
  }

  /**
   * Every option's args length(include themself)
   * <p>
   * It will be invoke by javadoc
   */
  public static int optionLength(String option) {
    return 0;
  }

  /**
   * Validate option .
   * <p>
   * It will be invoke by javadoc
   */
  public static boolean validOptions(String options[][], DocErrorReporter reporter) {
    return true;
  }

}
