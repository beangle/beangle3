/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.archiver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;

public final class ZipUtils {
	/**
	 * Don't let anyone instantiate this class.
	 */
	private ZipUtils() {
	}

	public static File zip(List<String> fileNames, String zipPath) {
		return zip(fileNames, zipPath, null);
	}

	// 文件压缩
	public static File zip(List<String> fileNames, String zipPath, String encoding) {
		try {
			FileOutputStream f = new FileOutputStream(zipPath);
			ZipArchiveOutputStream zos = (ZipArchiveOutputStream) new ArchiveStreamFactory()
					.createArchiveOutputStream(ArchiveStreamFactory.ZIP, f);
			if (null != encoding) {
				zos.setEncoding(encoding);
			}
			for (int i = 0; i < fileNames.size(); i++) {
				String fileName = fileNames.get(i);
				String entryName = StringUtils.substringAfterLast(fileName, File.separator);
				ZipArchiveEntry entry = new ZipArchiveEntry(entryName);
				zos.putArchiveEntry(entry);
				FileInputStream fis = new FileInputStream(fileName);
				IOUtils.copy(fis, zos);
				fis.close();
				zos.closeArchiveEntry();
			}
			zos.close();
			return new File(zipPath);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static List<String> unzip(final File zipFile, final String destination) {
		return unzip(zipFile, destination, null);
	}

	public static List<String> unzip(final File zipFile, final String destination, String encoding) {
		List<String> fileNames = CollectUtils.newArrayList();
		String dest = destination;
		if (!destination.endsWith(File.separator)) {
			dest = destination + File.separator;
		}
		ZipFile file;
		try {
			file = null;
			if (null == encoding) file = new ZipFile(zipFile);
			else file = new ZipFile(zipFile, encoding);
			@SuppressWarnings("unchecked")
			Enumeration<ZipArchiveEntry> en = file.getEntries();
			ZipArchiveEntry ze = null;
			while (en.hasMoreElements()) {
				ze = en.nextElement();
				File f = new File(dest, ze.getName());
				if (ze.isDirectory()) {
					f.mkdirs();
					continue;
				} else {
					f.getParentFile().mkdirs();
					InputStream is = file.getInputStream(ze);
					OutputStream os = new FileOutputStream(f);
					IOUtils.copy(is, os);
					is.close();
					os.close();
					fileNames.add(f.getAbsolutePath());
				}
			}
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileNames;
	}

	public static boolean isZipFile(File zipFile) {
		try {
			ZipFile zf = new ZipFile(zipFile);
			boolean isZip = zf.getEntries().hasMoreElements();
			zf.close();
			return isZip;
		} catch (IOException e) {
			return false;
		}
	}
}
