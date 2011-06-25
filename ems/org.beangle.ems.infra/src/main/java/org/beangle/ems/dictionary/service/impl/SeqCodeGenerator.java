/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.dictionary.service.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.Entity;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.util.EntityUtils;
import org.beangle.ems.dictionary.model.CodeScript;
import org.beangle.ems.dictionary.service.CodeFixture;

/**
 * @author chaostone
 * @version $Id: SeqCodeGenerator.java May 5, 2011 3:57:11 PM chaostone $
 */

public class SeqCodeGenerator extends ScriptCodeGenerator {

	public static final String SEQ = "seq";

	private EntityDao entityDao;

	public void setUp() throws Exception {
		// 事先注册seq变量
		interpreter.set("seq", SEQ);
		interpreter.set("dao", entityDao);
	}

	protected CodeScript getCodeScript(String entityName) {
		OqlBuilder<CodeScript> builder = OqlBuilder.from(CodeScript.class, "codeScript");
		builder.where("codeScript.codeClassName=:codeClassName", entityName);
		builder.cacheable();
		List<CodeScript> scripts = entityDao.search(builder);
		if (scripts.size() != 1) {
			return null;
		} else {
			return scripts.get(0);
		}
	}

	/**
	 * 根据请求的实体，生成代码
	 */
	@SuppressWarnings("unchecked")
	public String gen(CodeFixture fixture) {
		// 在必要时查找相应的生成脚本
		String script = fixture.getScript();
		CodeScript codeScript = null;
		if (null == script) {
			codeScript = getCodeScript(EntityUtils.getEntityClassName(fixture.getEntity().getClass()));
			if (null == codeScript) { return null; }
			script = codeScript.getScript();
			try {
				String code = (String) PropertyUtils.getProperty(fixture.getEntity(), codeScript.getAttr());
				if (isValidCode(code)) { return code; }
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		int seqLength = -1;
		// 替换自动代码生成中的seq[x]
		if (StringUtils.contains(script, SEQ)) {
			seqLength = NumberUtils.toInt(StringUtils.substringBetween(script, SEQ + "[", "]"));

			script = StringUtils.replace(script,
					SEQ + "[" + StringUtils.substringBetween(script, SEQ + "[", "]") + "]", SEQ);
		}
		fixture.setScript(script);
		String code = super.gen(fixture);
		List<String> seqs = CollectUtils.newArrayList();
		if (-1 != seqLength) {
			try {
				OqlBuilder<?> builder = OqlBuilder.from(Class.forName(codeScript.getCodeClassName()),
						"entity");
				builder.select("select substr(entity." + codeScript.getAttr() + "," + (code.indexOf(SEQ) + 1)
						+ "," + seqLength + ")");
				builder.where(" entity." + codeScript.getAttr() + " like :codeExample",
						StringUtils.replace(code, SEQ, "%"));
				builder.where("length(entity." + codeScript.getAttr() + ")="
						+ (code.length() - SEQ.length() + seqLength));
				seqs = (List<String>) entityDao.search(builder);
				Collections.sort(seqs);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			synchronized (this) {
				int newSeqNo = 0;
				for (Iterator<String> iter = seqs.iterator(); iter.hasNext();) {
					String seqNo = iter.next();
					if (NumberUtils.toInt(seqNo) - newSeqNo >= 2) {
						break;
					} else {
						newSeqNo = NumberUtils.toInt(seqNo);
					}
				}
				newSeqNo++;
				String seqNo = String.valueOf(newSeqNo);
				if (0 != seqLength) {
					seqNo = StringUtils.repeat("0", seqLength - seqNo.length()) + newSeqNo;
				}
				code = StringUtils.replace(code, SEQ, seqNo);
			}
		}
		return code;
	}

	public String test(CodeFixture fixture, CodeScript codeScript) {
		try {
			Class<?> codeClass = Class.forName(codeScript.getCodeClassName());
			Entity<?> entity = (Entity<?>) codeClass.newInstance();
			PropertyUtils.getProperty(entity, codeScript.getAttr());
			if (null != fixture) {
				for (Iterator<?> iter = fixture.getParams().keySet().iterator(); iter.hasNext();) {
					String param = (String) iter.next();
					interpreter.set(param, fixture.getParams().get(param));
				}
				if (StringUtils.isNotEmpty(fixture.getScript())) {
					interpreter.eval(fixture.getScript());
				}
			}
			return gen(new CodeFixture(entity, codeScript.getScript()));
		} catch (Exception e) {
			return ExceptionUtils.getFullStackTrace(e);
		}
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
}
