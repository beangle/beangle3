/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.archiver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipException;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.archiver.zip.ZipEntry;
import org.beangle.commons.archiver.zip.ZipFile;
import org.beangle.commons.archiver.zip.ZipOutputStream;
import org.beangle.commons.collection.CollectUtils;

public final class ZipUtils {
	/**
	 * Don't let anyone instantiate this class.
	 */
	private ZipUtils() {
	}

	public static List<String> unzip(final File zipFile, final String destination) {
		List<String> fileNames = CollectUtils.newArrayList();
		String dest = destination;
		if (!destination.endsWith(File.separator)) {
			dest = destination + File.separator;
		}

		ZipFile zf = null;
		try {
			zf = new ZipFile(zipFile);
			@SuppressWarnings("unchecked")
			Enumeration<ZipEntry> enu = zf.getEntries();
			while (enu.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) enu.nextElement();
				String name = entry.getName();
				String path = dest + name;
				File file = new File(path);
				if (entry.isDirectory()) {
					file.mkdirs();
				} else {
					InputStream is = zf.getInputStream(entry);
					byte[] buf1 = new byte[1024];
					int len;
					if (!file.exists()) {
						file.getParentFile().mkdirs();
						file.createNewFile();
					}
					OutputStream out = new FileOutputStream(file);
					while ((len = is.read(buf1)) > 0) {
						out.write(buf1, 0, len);
					}
					out.flush();
					out.close();
					is.close();
					fileNames.add(file.getAbsolutePath());
				}
			}
			zf.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (null != zf) {
				try {
					zf.close();
				} catch (IOException e) {
				}
			}
		}
		return fileNames;
	}

	// 文件压缩
	public static File zip(List<String> fileNames, String zipPath, String encoding) {
		try {
			FileOutputStream f = new FileOutputStream(zipPath);
			ZipOutputStream out = new ZipOutputStream(new DataOutputStream(f));
			// FIXME
			out.setEncoding(encoding);
			for (int i = 0; i < fileNames.size(); i++) {
				String fileName = fileNames.get(i);
				DataInputStream in = new DataInputStream(new FileInputStream(fileName));
				String entryName = StringUtils.substringAfterLast(fileName, File.separator);
				out.putNextEntry(new ZipEntry(entryName));
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
			}
			out.close();
			return new File(zipPath);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isZipFile(File zipFile) {
		try {
			ZipFile zf = new ZipFile(zipFile);
			boolean isZip = zf.getEntries().hasMoreElements();
			zf.close();
			return isZip;
		} catch (ZipException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}
