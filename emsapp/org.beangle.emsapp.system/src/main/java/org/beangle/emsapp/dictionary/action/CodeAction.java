/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.dictionary.action;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.ems.dictionary.model.BaseCode;
import org.beangle.ems.dictionary.model.CodeMeta;
import org.beangle.ems.dictionary.service.BaseCodeService;
import org.beangle.struts2.action.BaseAction;

/**
 * 基础代码管理
 * 
 * @author chaostone
 * @version $Id: BaseCodeAction.java Jun 29, 2011 5:19:26 PM chaostone $
 */
public class CodeAction extends BaseAction {

	private static final long serialVersionUID = -3799353282810314506L;

	private BaseCodeService baseCodeService;

	public void index() {
		String simpleName = get("type");
		StringBuilder builder = new StringBuilder();
		PrintWriter out = null;
		String format = get("format");
		if (StringUtils.isNotBlank(simpleName)) {
			Iterator<CodeMeta> it = entityDao.get(CodeMeta.class, "name", simpleName).iterator();
			if (it.hasNext()) {
				try {
					HttpServletResponse response = getResponse();
					response.setContentType("text/xml");
					response.setCharacterEncoding("UTF-8");
					out = response.getWriter();
					@SuppressWarnings("rawtypes")
					Class baseCodeClass = Class.forName(it.next().getClassName());
					if (BaseCode.class.isAssignableFrom(baseCodeClass)) {
						@SuppressWarnings("unchecked")
						List<? extends BaseCode<?>> baseCodes = baseCodeService.getCodes(baseCodeClass);
						if (StringUtils.isNotEmpty(format)) {
							for (BaseCode<?> baseCode : baseCodes) {
								builder.append("<option value='" + baseCode.getId() + "'>"
										+ baseCode.getName().trim() + "["
										+ PropertyUtils.getProperty(baseCode, format) + "]" + "</option>");
							}

						} else {
							for (BaseCode<?> baseCode : baseCodes) {
								builder.append("<option value='" + baseCode.getId() + "'>"
										+ baseCode.getName() + "</option>");
							}
						}
					}
					out.write(builder.toString());
				} catch (ClassNotFoundException e) {
					out.write("<option value=''>没有该基础代码</option>");
				} catch (Exception e2) {
					out.write("<option value=''>" + format + "不符合规范</option>");
				}
			}
		}
	}

	public void setBaseCodeService(BaseCodeService baseCodeService) {
		this.baseCodeService = baseCodeService;
	}

}
