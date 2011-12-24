/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.avatar;

import java.io.InputStream;
import java.util.Date;

/**
 * 头像
 * 
 * @author chaostone
 */
public interface Avatar {

	public String getName();

	public String getType();

	public long getSize();

	public Date getUpdatedAt();

	public InputStream getInputStream() throws AvatarException;
}
