/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.dictionary.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.ems.dictionary.model.CodeCategory;
import org.beangle.ems.dictionary.model.CodeMeta;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.Entity;

/**
 * 代码原信息管理类
 * 
 * @author chaostone
 * @version $Id: CodeMetaAction.java Jun 29, 2011 5:20:35 PM chaostone $
 */
public class CodeMetaAction extends SecurityActionSupport {

	@Override
	public String index() throws Exception {
		put(getShortName() + "s", search(getQueryBuilder()));
		return forward();
	}

	public String categories() {
		put("categories", entityDao.getAll(CodeCategory.class));
		return forward();
	}

	@Override
	protected void editSetting(Entity<?> entity) {
		put("categories", entityDao.getAll(CodeCategory.class));
	}

	public String saveCategory() {
		List<CodeCategory> categories = entityDao.getAll(CodeCategory.class);
		for (CodeCategory category : categories) {
			CodeCategory newCategory = populateEntity(CodeCategory.class, category.getId() + "_codeCategory");
			entityDao.saveOrUpdate(newCategory);
		}
		CodeCategory newCategory = populateEntity(CodeCategory.class, "newCodeCategory");
		if (StringUtils.isNotBlank(newCategory.getName())) {
			entityDao.saveOrUpdate(newCategory);
		}
		return redirect("categories", "info.save.success");
	}

	public String removeCategory() {
		CodeCategory newCategory = getEntity(CodeCategory.class, "codeCategory");
		try {
			if (null != newCategory.getId()) entityDao.remove(newCategory);
		} catch (Exception e) {
			logger.error("code category remove error :" + newCategory.getName(), e);
			return redirect("categories", "info.remove.failure");
		}
		return redirect("categories", "info.remove.success");
	}

	@Override
	protected String getEntityName() {
		return CodeMeta.class.getName();
	}

	public String save() {
		CodeMeta meta = populateEntity(CodeMeta.class, "codeMeta");
		List<CodeMeta> list = entityDao.get(CodeMeta.class, "name", new Object[] { meta.getName() });
		List<CodeMeta> enNamelist = entityDao.get(CodeMeta.class, "title", new Object[] { meta.getTitle() });
		List<CodeMeta> classlist = entityDao.get(CodeMeta.class, "className",
				new Object[] { meta.getClassName() });
		if (list.size() > 1) { return redirect("coderlist", "对不起，基础数据名称不能重复"); }
		if (classlist.size() > 1) { return redirect("coderlist", "对不起，基础数据实体类不能重复"); }
		if (enNamelist.size() > 1) {
			return redirect("coderlist", "对不起，基础数据标题名称不能重复");
		} else {
			entityDao.saveOrUpdate(meta);
			return redirect("search", "info.save.success");
		}
	}

}
