/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.mime;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.beangle.spring.config.ConfigResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MimeTypeProvider {

	private static Logger logger = LoggerFactory.getLogger(MimeTypeProvider.class);

	private final Properties contentTypes = new Properties();

	private ConfigResource resource;

	public String getMimeType(String ext, String defaultValue) {
		return contentTypes.getProperty(ext, defaultValue);
	}

	public String getMimeType(String ext) {
		return contentTypes.getProperty(ext);
	}

	public ConfigResource getResource() {
		return resource;
	}

	// META-INF/mimetypes.properties
	private void loadMimeType(URL url) {
		try {
			InputStream im = url.openStream();
			contentTypes.load(im);
			logger.info("content types loaded ");
			im.close();
		} catch (IOException e) {
			logger.error("load " + url + " error", e);
		}
	}

	public void setResource(ConfigResource resource) {
		this.resource = resource;
		if (null == resource) return;
		if (null != resource.getGlobal()) {
			loadMimeType(resource.getGlobal());
		}
		if (null != resource.getLocals()) {
			for (URL path : resource.getLocals()) {
				loadMimeType(path);
			}
		}
		if (null != resource.getUser()) {
			loadMimeType(resource.getUser());
		}

	}

}
