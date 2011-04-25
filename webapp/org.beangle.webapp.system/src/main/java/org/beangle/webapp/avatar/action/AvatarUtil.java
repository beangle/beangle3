/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.avatar.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.beangle.webapp.service.avatar.Avatar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AvatarUtil {
	private static final Logger logger = LoggerFactory.getLogger(AvatarUtil.class);

	private AvatarUtil() {
		super();
	}

	public static void copyTo(Avatar avatar, HttpServletResponse response) throws IOException {
		if (null == avatar) { return; }
		byte[] oBuff = new byte[1024];
		int iSize = 0;
		response.setContentType("image/" + avatar.getType());
		InputStream inStream = null;
		OutputStream output = null;
		try {
			inStream = avatar.getInputStream();
			output = response.getOutputStream();
			while ((iSize = inStream.read(oBuff)) > 0) {
				output.write(oBuff, 0, iSize);
			}
		} catch (Exception e) {
			logger.error("copy input to output error for XXXXXXXXXX" + avatar.getName(), e);
		} finally {
			if (null != inStream) {
				inStream.close();
			}
			if (null != output) {
				output.close();
			}
		}
	}
}
