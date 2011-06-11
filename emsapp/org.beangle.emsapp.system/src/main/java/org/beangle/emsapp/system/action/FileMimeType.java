/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.system.action;

import java.io.File;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.web.mime.MimeTypeProvider;

public class FileMimeType {

	private MimeTypeProvider mimeTypeProvider;

	private Set<String> texts = CollectUtils.newHashSet();

	public FileMimeType(MimeTypeProvider mimeTypeProvider) {
		super();
		texts.add("xml");
		this.mimeTypeProvider = mimeTypeProvider;
	}

	public String getMimeType(File file) {
		String ext = StringUtils.substringAfterLast(file.getName(), ".");
		String mimeType = mimeTypeProvider.getMimeType(ext, "application/x-msdownload");
		return StringUtils.replace(mimeType, "/", "-");
	}

	public boolean isTextType(File file) {
		String ext = StringUtils.substringAfterLast(file.getName(), ".");
		String mimeType = mimeTypeProvider.getMimeType(ext, "application/x-msdownload");
		boolean text = mimeType.contains("text");
		if (!text) {
			return texts.contains(ext);
		} else {
			return text;
		}
	}

}
