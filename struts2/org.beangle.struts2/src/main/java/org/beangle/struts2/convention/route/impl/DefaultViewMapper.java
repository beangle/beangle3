/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.convention.route.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.struts2.convention.Constants;
import org.beangle.struts2.convention.route.Profile;
import org.beangle.struts2.convention.route.ProfileService;
import org.beangle.struts2.convention.route.ViewMapper;

import com.opensymphony.xwork2.inject.Inject;

public class DefaultViewMapper implements ViewMapper {

	private Map<String, String> methodViews = new HashMap<String, String>();

	private ProfileService profileServie;

	public DefaultViewMapper() {
		super();
		methodViews.put("search", "list");
		methodViews.put("query", "list");
		methodViews.put("edit", "form");
		methodViews.put("home", "index");
		methodViews.put("execute", "index");
		methodViews.put("add", "new");
	}

	/**
	 * 查询control对应的view的名字(没有后缀)
	 * 
	 * @param request
	 * @param controllerClass
	 * @param relativePath
	 * @return
	 */
	public String getViewPath(String className, String methodName, String viewName) {
		if (StringUtils.isNotEmpty(viewName)) {
			if (viewName.charAt(0) == Constants.separator) { return viewName; }
		}
		Profile profile = profileServie.getProfile(className);
		if (null == profile) { throw new RuntimeException("no convention profile for " + className); }
		StringBuilder buf = new StringBuilder();
		if (profile.getViewPathStyle().equals(Constants.FULL_VIEWPATH)) {
			buf.append(Constants.separator);
			buf.append(profile.getSimpleName(className));
		} else if (profile.getViewPathStyle().equals(Constants.SIMPLE_VIEWPATH)) {
			buf.append(profile.getViewPath());
			// 添加中缀路径
			buf.append(profile.getInfix(className));
		} else if (profile.getViewPathStyle().equals(Constants.SEO_VIEWPATH)) {
			buf.append(profile.getViewPath());
			buf.append(StrUtils.unCamel(profile.getInfix(className)));
		} else {
			throw new RuntimeException(profile.getViewPathStyle() + " was not supported");
		}
		// add method mapping path
		buf.append(Constants.separator);
		if (StringUtils.isEmpty(viewName) || viewName.equals("success")) {
			viewName = methodName;
		}

		if (null == methodViews.get(viewName)) {
			buf.append(viewName);
		} else {
			buf.append(methodViews.get(viewName));
		}
		return buf.toString();
	}

	@Inject
	public void setProfileServie(ProfileService profileServie) {
		this.profileServie = profileServie;
	}
}
