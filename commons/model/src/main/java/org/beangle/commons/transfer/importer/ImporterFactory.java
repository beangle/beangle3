/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.commons.transfer.importer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Map;

import org.beangle.commons.transfer.csv.CsvItemReader;
import org.beangle.commons.transfer.excel.ExcelItemReader;
import org.beangle.commons.transfer.io.TransferFormat;

/**
 * Importer Factory
 * 
 * @author chaostone
 * @since 3.1
 */
public final class ImporterFactory {

  public static final EntityImporter getEntityImporter(TransferFormat format, InputStream is, Class<?> clazz,
      Map<String, Object> params) {
    EntityImporter importer = new DefaultEntityImporter(clazz);
    if (format.equals(TransferFormat.Xls)) {
      importer.setReader(new ExcelItemReader(is, 1));
    } else {
      LineNumberReader reader = new LineNumberReader(new InputStreamReader(is));
      importer.setReader(new CsvItemReader(reader));
    }
    return importer;
  }
}
