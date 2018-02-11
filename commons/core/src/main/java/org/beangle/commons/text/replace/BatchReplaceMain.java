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
package org.beangle.commons.text.replace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.io.Files;
import org.beangle.commons.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Batch replace file with pattern. Using<B> BatchReplaceMain dir patternfile
 * [encoding]</B>
 * <p>
 * Pattern file like this.
 * 
 * <pre>
 * ftl
 * &lt;#(.*?)&gt;=[#$1]
 * &lt;/#(.*?)&gt;=[/#$1]
 * &lt;/@&gt;=[/@]
 * &lt;@(.*?)/&gt;=[@$1/]
 * &lt;@(.*?)&gt;=[@$1]
 * &lt;/@(.*?)&gt;=[/@]
 * </pre>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class BatchReplaceMain {

  private static Logger logger = LoggerFactory.getLogger(BatchReplaceMain.class);

  /**
   * Usage:BatchReplaceMain dir patternfile encoding
   */
  public static void main(String[] args) throws Exception {
    if (args.length < 2) {
      logger.info("using BatchReplaceMain dir patternfile encoding");
      return;
    }
    String dir = args[0];
    if (!new File(dir).exists()) {
      logger.error("{} not a valid file or directory", dir);
      return;
    }
    String properties = args[1];
    if (!new File(properties).exists()) {
      logger.info("{} not valid file or directory", properties);
    }
    Charset charset = null;
    if (args.length >= 3) {
      charset = Charset.forName(args[2]);
    }
    List<String> lines = Files.readLines(new File(properties));
    Map<String, List<Replacer>> profiles = CollectUtils.newHashMap();
    List<Replacer> replacers = null;
    for (String line : lines) {
      if (Strings.isEmpty(line)) {
        continue;
      }
      if (-1 == line.indexOf('=')) {
        replacers = CollectUtils.newArrayList();
        profiles.put(line, replacers);
      } else {
        line = Strings.replace(line, "\\=", "~~~~");
        String older = Strings.replace(Strings.substringBefore(line, "="), "~~~~", "=");
        String newer = Strings.replace(Strings.substringAfter(line, "="), "~~~~", "=");
        older = Strings.replace(older, "\\n", "\n");
        older = Strings.replace(older, "\\t", "\t");
        newer = Strings.replace(newer, "\\n", "\n");
        newer = Strings.replace(newer, "\\t", "\t");
        Replacer pair = new Replacer(older, newer);
        replacers.add(pair);
      }
    }
    replaceFile(dir, profiles, charset);
  }

  /**
   * <p>
   * replaceFile.
   * </p>
   * 
   * @param fileName a {@link java.lang.String} object.
   * @param profiles a {@link java.util.Map} object.
   * @param encoding a {@link java.lang.String} object.
   * @throws java.lang.Exception if any.
   * @throws java.io.FileNotFoundException if any.
   */
  public static void replaceFile(String fileName, final Map<String, List<Replacer>> profiles, Charset charset)
      throws Exception, FileNotFoundException {
    File file = new File(fileName);
    if (file.isFile() && !file.isHidden()) {
      List<Replacer> replacers = profiles.get(Strings.substringAfterLast(fileName, "."));
      if (null == replacers) { return; }
      logger.info("processing {}", fileName);
      String filecontent = Files.readFileToString(file, charset);
      filecontent = Replacer.process(filecontent, replacers);
      writeToFile(filecontent, fileName, charset);
    } else {
      String[] subFiles = file.list(new FilenameFilter() {
        public boolean accept(File dir, String name) {
          if (dir.isDirectory()) return true;
          boolean matched = false;
          for (String key : profiles.keySet()) {
            matched = name.endsWith(key);
            if (matched) return true;
          }
          return false;
        }
      });
      if (null != subFiles) {
        for (int i = 0; i < subFiles.length; i++) {
          replaceFile(fileName + '/' + subFiles[i], profiles, charset);
        }
      }
    }
  }

  /**
   * <p>
   * writeToFile.
   * </p>
   */
  public static void writeToFile(String str, String fileName, Charset charset) throws Exception {
    OutputStreamWriter writer = null;
    if (null == charset) {
      writer = new OutputStreamWriter(new FileOutputStream(fileName));
    } else {
      writer = new OutputStreamWriter(new FileOutputStream(fileName), charset.name());
    }
    writer.write(str);
    writer.close();
  }
}
