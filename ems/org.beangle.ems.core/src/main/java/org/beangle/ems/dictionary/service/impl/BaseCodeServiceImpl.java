/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.dictionary.service.impl;

import java.sql.Date;
import java.util.List;

import org.beangle.dao.impl.BaseServiceImpl;
import org.beangle.dao.query.builder.OqlBuilder;
import org.beangle.ems.dictionary.model.BaseCode;
import org.beangle.ems.dictionary.model.CodeMeta;
import org.beangle.ems.dictionary.service.BaseCodeService;

/**
 *
 * @author chaostone
 * @version $Id: BaseCodeServiceImpl.java May 5, 2011 3:33:07 PM chaostone $
 */
public class BaseCodeServiceImpl extends BaseServiceImpl implements BaseCodeService  {

	public <T extends BaseCode<?>> T getCode(Class<T> codeClass, String code) {
		OqlBuilder<T> builder = OqlBuilder.from(codeClass, "basecode").where("basecode.code=:code", code);
		List<T> rs = entityDao.search(builder);
		if (!rs.isEmpty()) return rs.get(0);
		else return null;
	}

	public <T extends BaseCode<?>> List<T> getCodes(Class<T> codeClass) {
		OqlBuilder<T> builder = OqlBuilder.from(codeClass, "basecode").where(
				"basecode.effectAt <= :now and (basecode.invalidAt is null or basecode.invalidAt >= :now)",
				new java.util.Date());
		builder.orderBy("basecode.code");
		return entityDao.search(builder);
	}

	public <T extends BaseCode<?>> T getCode(Class<T> codeClass, Long codeId) {
		return entityDao.get(codeClass, codeId);
	}

	public <T extends BaseCode<?>> List<T> getCodes(Class<T> type, Long... ids) {
		OqlBuilder<T> builder = OqlBuilder.from(type, "basecode").where("basecode.id in(:ids)", ids);
		return entityDao.search(builder);
	}

	@SuppressWarnings("unchecked")
	public Class<? extends BaseCode<?>> getCodeType(String name) {
		OqlBuilder<CodeMeta> builder = OqlBuilder.from(CodeMeta.class, "coder");
		builder.where("coder.name=:name or coder.engName=:name", name);
		List<CodeMeta> coders = entityDao.search(builder);
		try {
			if (1 != coders.size()) return null;
			else return (Class<? extends BaseCode<?>>) Class.forName(coders.get(0).getClassName());
		} catch (ClassNotFoundException e) {
			logger.error("Basecode " + name + "type not found", e);
			throw new RuntimeException(e);
		}
	}

	public void removeCodes(Class<? extends BaseCode<?>> codeClass, Long... codeIds) {
		entityDao.remove(entityDao.get(codeClass, codeIds));
	}

	public void saveOrUpdate(BaseCode<?> code) {
		if (code.isTransient()) {
			code.setCreatedAt(new Date(System.currentTimeMillis()));
		}
		code.setUpdatedAt(new Date(System.currentTimeMillis()));
		entityDao.saveOrUpdate(code);
	}
}
