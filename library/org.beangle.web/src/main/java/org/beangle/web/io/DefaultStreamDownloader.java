/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.io;

import static org.beangle.web.util.RequestUtils.encodeAttachName;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.beangle.web.mime.MimeTypeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class DefaultStreamDownloader implements InitializingBean, StreamDownloader {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected MimeTypeProvider mimeTypeProvider;

	public void afterPropertiesSet() throws Exception {
		Validate.notNull(mimeTypeProvider, "mimeTypeProvider must be set");
	}

	public DefaultStreamDownloader() {
		super();
	}

	public DefaultStreamDownloader(MimeTypeProvider mimeTypeProvider) {
		super();
		this.mimeTypeProvider = mimeTypeProvider;
	}

	public void download(HttpServletRequest request, HttpServletResponse response, File file) {
		download(request, response, file, file.getName());
	}

	public void download(HttpServletRequest request, HttpServletResponse response, URL url, String display) {
		try {
			download(request, response, url.openStream(), url.getFile(), display);
		} catch (Exception e) {
			logger.warn("download file error=" + display, e);
		}
	}

	public void download(HttpServletRequest request, HttpServletResponse response, File file, String display) {
		Validate.notNull(file, "file shouldn't be null");
		Validate.isTrue(file.exists(), "file should exists");
		try {
			download(request, response, new FileInputStream(file), file.getAbsolutePath(), display);
		} catch (Exception e) {
			logger.warn("download file error=" + display, e);
		}
	}

	protected void addContent(HttpServletRequest request, HttpServletResponse response, String attach) {
		String contentType = response.getContentType();
		if (null == contentType) {
			contentType = mimeTypeProvider.getMimeType(StringUtils.substringAfterLast(attach, "."),
					"application/x-msdownload");
			response.setContentType(contentType);
			logger.debug("set content type {} for {}", contentType, attach);
		}
		String encodeName = encodeAttachName(request, attach);
		response.setHeader("Content-Disposition", "attachment; filename=" + encodeName);
		response.setHeader("Location", encodeName);
	}

	public void download(HttpServletRequest request, HttpServletResponse response, InputStream inStream,
			String name, String display) {
		String attach_name = getAttachName(name, display);
		try {
			response.reset();
			addContent(request, response, attach_name);
			IOUtils.copy(inStream, response.getOutputStream());
		} catch (Exception e) {
			logger.warn("download file error " + attach_name, e);
		} finally {
			IOUtils.closeQuietly(inStream);
		}
	}

	public static String getAttachName(String name, String display) {
		String attch_name = "";
		String ext = StringUtils.substringAfterLast(name, ".");
		if (StringUtils.isBlank(display)) {
			attch_name = getFileName(name);
		} else {
			attch_name = display;
			if (!attch_name.endsWith("." + ext)) {
				attch_name += "." + ext;
			}
		}
		return attch_name;
	}

	/**
	 * 根据路径得到真实的文件名.
	 * 
	 * @param file_name
	 * @return
	 */
	protected static String getFileName(String file_name) {
		if (file_name == null) return "";
		file_name = file_name.trim();
		int iPos = 0;
		iPos = file_name.lastIndexOf("\\");
		if (iPos > -1) {
			file_name = file_name.substring(iPos + 1);
		}
		iPos = file_name.lastIndexOf("/");
		if (iPos > -1) {
			file_name = file_name.substring(iPos + 1);
		}
		iPos = file_name.lastIndexOf(File.separator);
		if (iPos > -1) {
			file_name = file_name.substring(iPos + 1);
		}
		return file_name;
	}

	public void setMimeTypeProvider(MimeTypeProvider mimeTypeProvider) {
		this.mimeTypeProvider = mimeTypeProvider;
	}
}
