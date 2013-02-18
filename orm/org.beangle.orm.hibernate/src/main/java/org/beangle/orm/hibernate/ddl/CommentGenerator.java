/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.orm.hibernate.ddl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.persistence.*;

import org.beangle.commons.lang.Strings;
import org.beangle.orm.hibernate.DefaultTableNamingStrategy;

import com.sun.javadoc.*;
import com.sun.javadoc.AnnotationDesc.ElementValuePair;

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
 *          &lt;doclet&gt;org.beangle.orm.hibernate.ddl.CommentGenerator&lt;/doclet&gt;
 *          &lt;docletArtifact&gt;
 *            &lt;groupId&gt;org.beangle.orm&lt;/groupId&gt;
 *            &lt;artifactId&gt;beangle-orm-hibernate&lt;/artifactId&gt;
 *            &lt;version&gt;${beangle.version}&lt;/version&gt;
 *          &lt;/docletArtifact&gt;
 *          &lt;additionalparam&gt;
 *            -file /path/to/your/comment.sql
 *            -config /path/to/your/META-INF/beangle/table.properties
 *          &lt;/additionalparam&gt;
 *          &lt;useStandardDocletOptions&gt;false&lt;/useStandardDocletOptions&gt;
 *        &lt;/configuration&gt;
 *      &lt;/plugin&gt;
 *    &lt;/plugins&gt;
 *  &lt;/build&gt;
 * </pre>
 * 
 * @author chaostone
 * @since 3.2.0
 * @see https://github.com/beangle/library/wiki/Sql-Comment-Generator
 * @see http://docs.oracle.com/javase/1.4.2/docs/tooldocs/javadoc/overview.html
 * @see http://docs.oracle.com/javase/7/docs/jdk/api/javadoc/doclet/index.html
 */
public class CommentGenerator {

  private static String file;
  private static FileWriter writer;
  private static String config;

  public static boolean start(RootDoc root) throws Exception {
    readOptions(root.options());
    writer = new FileWriter(file);
    DefaultTableNamingStrategy namingStrategy = new DefaultTableNamingStrategy();
    namingStrategy.addConfig(new File(config).toURI().toURL());
    for (ClassDoc classDoc : root.classes()) {

      if (isAnnotationPresent(classDoc, Entity.class)) {
        String className = classDoc.qualifiedTypeName();
        String tableName = getAnnotationValue(classDoc, Table.class, "name");
        if (null == tableName) tableName = namingStrategy.classToTableName(className);

        String tableComment = classDoc.commentText();
        if (Strings.isNotBlank(tableComment)) comment(tableName, Strings.split(tableComment, "\n")[0]);
        else missingComment(classDoc.qualifiedTypeName());

        ClassDoc curClassDoc = classDoc;
        while (null != curClassDoc) {
          for (FieldDoc fieldDoc : curClassDoc.fields(false)) {
            if (fieldDoc.isTransient() || fieldDoc.isStatic()) continue;

            if (isAnnotationPresent(fieldDoc, ManyToMany.class, OneToMany.class)) continue;

            String columnName = Strings.unCamel(fieldDoc.name(), '_');
            String columnComment = fieldDoc.commentText();
            if (Strings.isBlank(columnComment)) {
              missingComment(className
                  + (classDoc.equals(curClassDoc) ? "." : "(" + curClassDoc.qualifiedTypeName() + ").")
                  + fieldDoc.name());
            } else {
              columnComment = Strings.split(columnComment, "\n")[0];
              if (isAnnotationPresent(fieldDoc, ManyToOne.class, OneToOne.class)) {
                String joinColumn = getAnnotationValue(fieldDoc, JoinColumn.class, "name");
                if (null == joinColumn) {
                  columnName = columnName + "_id";
                  columnComment = columnComment + "ID";
                } else columnName = joinColumn;
              }
              comment(tableName, columnName, columnComment);
            }
          }
          curClassDoc = curClassDoc.superclass();
        }
      }
    }
    writer.close();
    return true;
  }

  private static boolean isAnnotationPresent(ProgramElementDoc doc, Class<?>... types) {
    for (AnnotationDesc ad : doc.annotations()) {
      for (Class<?> type : types)
        if (ad.annotationType().qualifiedTypeName().equals(type.getName())) return true;
    }
    return false;
  }

  private static String getAnnotationValue(ProgramElementDoc doc, Class<?> type, String attrName) {
    for (AnnotationDesc ad : doc.annotations()) {
      if (ad.annotationType().qualifiedTypeName().equals(type.getName())) {
        for (ElementValuePair p : ad.elementValues())
          if (attrName.equals(p.element().name())) return String.valueOf(p.value().value());
      }
    }
    return null;
  }

  private static void comment(String table, String comment) throws IOException {
    writer.append("comment on table " + table + " is '" + comment + "';\n");
  }

  private static void comment(String tableName, String columnName, String comment) throws IOException {
    writer.append("commment on column " + tableName + "." + columnName + " is '" + comment + "';\n");
  }

  private static void missingComment(String elementName) throws IOException {
    writer.append("--missing comment " + elementName + "\n");
  }

  /**
   * Every option's args length(include themself)
   * <p>
   * It will be invoke by javadoc
   */
  public static int optionLength(String option) {
    if (option.equals("-file") || option.equals("-config")) return 2;
    else return 0;
  }

  private static void readOptions(String[][] options) {
    for (int i = 0; i < options.length; i++) {
      String[] opt = options[i];
      if (opt[0].equals("-file")) file = opt[1];
      else if (opt[0].equals("-config")) config = opt[1];
    }
  }

  /**
   * Validate option .
   * <p>
   * It will be invoke by javadoc
   */
  public static boolean validOptions(String options[][], DocErrorReporter reporter) {
    readOptions(options);
    if (null == file || null == config) {
      reporter
          .printError("Usage: javadoc -file your file -config /path/to/your/config -doclet CommentGenerator ...");
      return false;
    }
    return true;
  }

}
