/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.helper;

import java.net.URL;

import org.beangle.commons.lang.Strings;
import org.beangle.commons.transfer.csv.CsvItemWriter;
import org.beangle.commons.transfer.dbf.DBFItemWriter;
import org.beangle.commons.transfer.excel.ExcelItemWriter;
import org.beangle.commons.transfer.excel.ExcelTemplateWriter;
import org.beangle.commons.transfer.exporter.Context;
import org.beangle.commons.transfer.exporter.Exporter;
import org.beangle.commons.transfer.exporter.SimpleEntityExporter;
import org.beangle.commons.transfer.exporter.TemplateExporter;
import org.beangle.commons.transfer.exporter.TemplateWriter;
import org.beangle.commons.transfer.io.TransferFormats;
import org.beangle.commons.transfer.io.Writer;

import com.opensymphony.xwork2.util.ClassLoaderUtil;

public class ExportHelper {

  public static Exporter buildExporter(Context context) {
    Exporter exporter;
    String template = context.get("template", String.class);
    if (Strings.isNotBlank(template)) {
      exporter = new TemplateExporter();
    } else {
      exporter = new SimpleEntityExporter();
    }
    exporter.setWriter(getWriter(context));
    return exporter;
  }

  public static Writer getWriter(Context context) {
    String format = (String) context.get("format");
    if (format.equals(TransferFormats.XLS) || format.equals("excel")) {
      String template = (String) context.get("template");
      if (Strings.isEmpty(template)) {
        return new ExcelItemWriter();
      } else {
        TemplateWriter writer = new ExcelTemplateWriter();
        URL templateURL = ClassLoaderUtil.getResource(template, Exporter.class);
        if (null == templateURL) {
          throw new RuntimeException("Empty template path!");
        } else {
          writer.setTemplate(templateURL);
        }
        return writer;
      }
    } else if (format.equals(TransferFormats.CSV)) {
      return new CsvItemWriter();
    } else if (format.equals(TransferFormats.DBF)) {
      return new DBFItemWriter();
    } else {
      throw new RuntimeException(format + " is not supported(choose xls,csv,dbf)");
    }
  }
}
