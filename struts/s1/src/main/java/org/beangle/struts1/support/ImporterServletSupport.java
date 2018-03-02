/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.struts1.support;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.beangle.commons.transfer.TransferResult;
import org.beangle.commons.transfer.csv.CsvItemReader;
import org.beangle.commons.transfer.excel.ExcelItemReader;
import org.beangle.commons.transfer.importer.DefaultEntityImporter;
import org.beangle.commons.transfer.importer.EntityImporter;

public class ImporterServletSupport {

  public ImporterServletSupport() {
  }

  public static EntityImporter buildEntityImporter(HttpServletRequest request, Class clazz,
      TransferResult tr) {
    try {
      Part filePart = null;
      String fileName = null;
      for (Part part : request.getParts()) {
        if (null != part.getSubmittedFileName() && part.getSize() > 0) {
          filePart = part;
          fileName = part.getSubmittedFileName();
          break;
        }
      }
      java.io.InputStream is;
      HSSFWorkbook wb;
      is = filePart.getInputStream();
      EntityImporter importer;
      if (fileName.endsWith(".xls")) {
        wb = new HSSFWorkbook(is);
        if (wb.getNumberOfSheets() < 1 || wb.getSheetAt(0).getLastRowNum() == 0) return null;
        importer = new DefaultEntityImporter(clazz);
        importer.setReader(new ExcelItemReader(wb, 1));
        request.setAttribute("importer", importer);
        request.setAttribute("importResult", tr);
        return importer;
      } else if (fileName.endsWith(".csv")) {
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(is));
        if (null == reader.readLine()) return null;
        importer = new DefaultEntityImporter(clazz);
        importer.setReader(new CsvItemReader(reader));
        return importer;
      } else {
        throw new RuntimeException("Cannot accept file " + fileName);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}
