/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.avatar.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.beangle.ems.avatar.AvatarException;

public class FileAvatar extends AbstractAvatar {

	private File file;

	public FileAvatar(File file) {
		super();
		this.file = file;
		this.setUpdatedAt(new Date(file.lastModified()));
		setName(file.getName());
	}

	public FileAvatar() {
		super();
	}

	public long getSize() {
		return file.length();
	}

	public String getType() {
		if (null == super.getType()) {
			setType(StringUtils.substringAfterLast(file.getAbsolutePath(), "."));
		}
		return super.getType();
	}

	public InputStream getInputStream() throws AvatarException {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new AvatarException(e);
		}
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
