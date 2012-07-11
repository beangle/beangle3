/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.util.archiver;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.testng.annotations.Test;

public class ZipUtilsTest {

  private String testFilename = "zipsource";

  private String getSource() {
    URL url = ZipUtilsTest.class.getClassLoader().getResource(testFilename + ".txt");
    File f = null;
    try {
      f = new File(url.toURI());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return f.getAbsolutePath();
  }

  private String getZipTarget() {
    return Strings.substringBeforeLast(getSource(), "/") + "/" + testFilename + ".zip";
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
    String tmp = System.getProperty("java.io.tmpdir") + File.separator + "testzipdir";
    File file = new File(tmp);
    file.delete();
    file.mkdirs();
    ZipUtils.unzip(f, file.getAbsolutePath());
    // f.delete();
    FileUtils.deleteDirectory(file);
  }

  @Test
  public void testZip() throws Exception {
    List<String> fileNames = CollectUtils.newArrayList();
    fileNames.add(getSource());
    String filename = getZipTarget();
    ZipUtils.zip(fileNames, filename);
  }

}
