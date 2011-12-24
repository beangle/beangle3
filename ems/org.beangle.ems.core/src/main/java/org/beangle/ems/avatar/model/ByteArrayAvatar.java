/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.avatar.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.beangle.ems.avatar.AvatarException;

public class ByteArrayAvatar extends AbstractAvatar {

	byte[] buf;

	public ByteArrayAvatar() {
		super();
	}

	public ByteArrayAvatar(String name, String type, byte[] buf) {
		super();
		setName(name);
		setType(type);
		this.buf = buf;
	}

	public InputStream getInputStream() throws AvatarException {
		return new ByteArrayInputStream(buf);
	}

	public long getSize() {
		return buf.length;
	}

}
