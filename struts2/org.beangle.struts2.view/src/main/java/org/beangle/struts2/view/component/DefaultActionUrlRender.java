package org.beangle.struts2.view.component;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsConstants;
import org.beangle.web.url.UrlRender;

import com.opensymphony.xwork2.inject.Inject;

public class DefaultActionUrlRender implements ActionUrlRender {

	private UrlRender render;

	public String render(String referer, String uri) {
		return render.render(referer, uri);
	}

	@Inject(StrutsConstants.STRUTS_ACTION_EXTENSION)
	public void setSuffix(String suffix) {
		String firstSuffix = null;
		if (StringUtils.isNotEmpty(suffix)) {
			int commaIndex = suffix.indexOf(",");
			if (-1 == commaIndex) firstSuffix = suffix;
			else firstSuffix = suffix.substring(0, commaIndex);
		}
		render = new UrlRender(firstSuffix);
	}

}
