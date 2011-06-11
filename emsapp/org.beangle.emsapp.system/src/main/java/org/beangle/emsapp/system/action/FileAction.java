/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.system.action;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.struts2.action.BaseAction;
import org.beangle.web.io.StreamDownloader;
import org.beangle.web.mime.MimeTypeProvider;

public class FileAction extends BaseAction {

	private MimeTypeProvider mimeTypeProvider;

	private StreamDownloader streamDownloader;

	public String index() {
		put("path", getPath());
		return forward();
	}

	public String list() {
		String path = getPath();
		File curFile = new File(path);
		if (curFile.exists()) {
			File[] files = list(curFile);
			put("files", files);
			put("path", path);
			if (null != curFile.getParent()) {
				put("parent", curFile.getParent());
			}
		}
		put("dateformat", new DateFormatUtils());
		put("mimeType", new FileMimeType(mimeTypeProvider));
		return forward();
	}

	public String download() throws IOException {
		String path = get("path");
		FileMimeType fileMimeType = new FileMimeType(mimeTypeProvider);
		if (StringUtils.isNotBlank(path)) {
			File file = new File(path);
			if (!file.isFile()) { return null; }
			boolean download = getBool("download");
			if (!download && fileMimeType.isTextType(file)) {
				List<String> lines = CollectUtils.newArrayList();
				LineNumberReader reader = new LineNumberReader(new FileReader(file));
				String line = reader.readLine();
				while (null != line) {
					lines.add(line);
					line = reader.readLine();
				}
				put("lines", lines);
				put("file", file);
				reader.close();
				return forward("content");
			} else {
				streamDownloader.download(getRequest(), getResponse(), file);
			}
		}
		return null;
	}

	private File[] list(File curFile) {
		try {
			String path = curFile.getAbsolutePath();
			String[] sFiles = curFile.list();
			File[] files = new File[sFiles.length];
			for (int n = 0; n < sFiles.length; n++) {
				files[n] = new File(path + "/" + sFiles[n]);
			}
			Arrays.sort(files);
			return files;
		} catch (Exception e) {
			return null;
		}
	}

	private String getPath() {
		String path = get("path");
		if (StringUtils.isEmpty(path)) {
			path = getRequest().getSession().getServletContext().getRealPath("");
		}
		String sRet = path;// .replace('\\', '/');
		File file = new File(path);
		try {
			sRet = file.getCanonicalPath();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if (file.isDirectory()) {
			if (!sRet.endsWith(File.separator)) sRet += File.separator;
		}
		return sRet;
	}

	public String newFolder() throws IOException {
		String path = getPath();
		String newFolder = get("newFolder");
		File file = new File(path + newFolder);
		file.createNewFile();
		return redirect("list", "info.action.success", "&path=" + path);
	}

	public void setMimeTypeProvider(MimeTypeProvider mimeTypeProvider) {
		this.mimeTypeProvider = mimeTypeProvider;
	}

	public void setStreamDownloader(StreamDownloader streamDownloader) {
		this.streamDownloader = streamDownloader;
	}

}
