/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.archiver;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;

public class ZipUtilsTest {

	public void isZip() throws Exception {
		URL url = getClass().getClassLoader().getResource("notZip.zip");
		File f = new File(url.getPath());
		assertFalse(ZipUtils.isZipFile(f));

		url = getClass().getClassLoader().getResource("test.zip");
		f = new File(url.getPath());
		assertTrue(ZipUtils.isZipFile(f));

		url = getClass().getClassLoader().getResource("test");
		f = new File(url.getPath());
		assertTrue(ZipUtils.isZipFile(f));

		url = getClass().getClassLoader().getResource("test.rar");
		f = new File(url.getPath());
		assertFalse(ZipUtils.isZipFile(f));
	}

	public void testUnZipAndDelete() throws Exception {
		URL url = getClass().getClassLoader().getResource("test.zip");
		File f = new File(url.getPath());
		FileInputStream in = new FileInputStream(f);
		String tmp = System.getProperty("java.io.tmpdir") + File.separator + "test.zip.tmp";
		try {
			FileOutputStream out = new FileOutputStream(tmp);
			byte[] bytes = null;
			bytes = new byte[(int) f.length()];
			in.read(bytes);
			in.close();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		File file = new File(tmp);
		ZipUtils.unzip(file, file.getParentFile().getAbsolutePath());
		assertTrue(file.delete());
	}

	public void zip() throws Exception {
		URL url = ZipUtilsTest.class.getClassLoader().getResource("测试压缩文档.txt");
		File f = new File(url.toURI());
		String path = f.getAbsolutePath();
		List<String> fileNames = CollectUtils.newArrayList();
		fileNames.add(path);

		ZipUtils.zip(fileNames, StringUtils.substringBeforeLast(path, "/") + "/测试压缩文档.zip", "GBK");
		// List list = ZipUtils.unzip(new File("d:/test/她.zip"), "d:/test");
		// for (Iterator iter = list.iterator(); iter.hasNext();) {
		// String element = (String) iter.next();
		// System.out.println(element);
		// }
		// File zipFile = new File("d:/test/她.zip");
		// assertTrue(ZipUtils.isZipFile(zipFile));
	}

}
