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
package org.beangle.ems.util;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.io.Files;
import org.beangle.commons.lang.ClassLoaders;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.SystemInfo;
import org.testng.annotations.Test;

public class ZipUtilsTest {

  private String testFilename = "zipsource";

  private String getSource() {
    URL url = ClassLoaders.getResource(testFilename + ".txt", getClass());
    File f = null;
    try {
      f = new File(url.toURI());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return f.getAbsolutePath();
  }

  private String getZipTarget() {
    return Strings.substringBeforeLast(getSource(), File.separator) + File.separator + testFilename + ".zip";
  }

  @Test(dependsOnMethods = "testZip")
  public void isZip() throws Exception {
    File f = new File(getZipTarget());
    assertTrue(ZipUtils.isZipFile(f));
    f = new File(getSource());
    assertFalse(ZipUtils.isZipFile(f));
  }

  @Test(dependsOnMethods = { "testZip", "isZip" })
  public void testUnZipAndDelete() throws Exception {
    File f = new File(getZipTarget());
    String tmp = SystemInfo.getTmpDir() + File.separator + "testzipdir";
    File file = new File(tmp);
    file.delete();
    file.mkdirs();
    ZipUtils.unzip(f, file.getAbsolutePath());
    f.delete();
    Files.deleteDirectory(file);
  }

  @Test
  public void testZip() throws Exception {
    List<String> fileNames = CollectUtils.newArrayList();
    fileNames.add(getSource());
    String filename = getZipTarget();
    ZipUtils.zip(fileNames, filename);
  }

}
