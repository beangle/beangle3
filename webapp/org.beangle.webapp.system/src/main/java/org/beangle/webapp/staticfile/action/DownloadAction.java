/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.staticfile.action;

import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.struts2.action.BaseAction;
import org.beangle.web.io.StreamDownloader;
import org.beangle.webapp.staticfile.StaticFileLoader;

/**
 * 静态资源下载
 * 
 * @author chaostone
 */
public class DownloadAction extends BaseAction implements ServletRequestAware, ServletResponseAware {

	private List<StaticFileLoader> loaders = CollectUtils.newArrayList();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private StreamDownloader streamDownloader;
	
	public String index() throws Exception {
		String name = get("file");
		String displayName = get("display");
		URL url = null;
		if (StringUtils.isNotEmpty(name)) {
			for (StaticFileLoader loader : loaders) {
				url = loader.getFile(name);
				if (null != url) {
					break;
				}
			}
			if (null != url) {
				streamDownloader.download(request, response, url, displayName);
			}
		}
		if (null == url) {
			return "nofound";
		} else {
			return null;
		}
	}

	public void setServletResponse(HttpServletResponse resp) {
		this.response = resp;
	}

	public void setServletRequest(HttpServletRequest req) {
		this.request = req;
	}

	public List<StaticFileLoader> getLoaders() {
		return loaders;
	}

	public void setLoaders(List<StaticFileLoader> loaders) {
		this.loaders = loaders;
	}

	public void setStreamDownloader(StreamDownloader streamDownloader) {
		this.streamDownloader = streamDownloader;
	}

}
