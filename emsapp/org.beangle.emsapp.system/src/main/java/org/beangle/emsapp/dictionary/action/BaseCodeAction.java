/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.dictionary.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.ems.dictionary.model.BaseCode;
import org.beangle.ems.dictionary.model.CodeCategory;
import org.beangle.ems.dictionary.model.CodeMeta;
import org.beangle.ems.dictionary.service.BaseCodeService;
import org.beangle.ems.dictionary.service.CodeFixture;
import org.beangle.ems.dictionary.service.CodeGenerator;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.entity.Model;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.EntityQuery;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.excel.ExcelItemWriter;
import org.beangle.model.transfer.exporter.Context;
import org.beangle.model.transfer.exporter.Exporter;
import org.beangle.model.transfer.exporter.MultiEntityExporter;
import org.beangle.struts2.convention.route.Action;
import org.beangle.struts2.helper.Params;

/**
 * 基础代码管理
 * 
 * @author chaostone
 * @version $Id: BaseCodeAction.java Jun 29, 2011 5:19:26 PM chaostone $
 */
public class BaseCodeAction extends SecurityActionSupport {

	private static final long serialVersionUID = -3799353282810314506L;

	public static final String EXT = "ext/";

	private CodeGenerator codeGenerator;

	private BaseCodeService baseCodeService;

	/**
	 * 基础代码管理主界面
	 */
	public String index() {
		put("codeCategories", entityDao.getAll(CodeCategory.class));
		return forward();
	}

	protected QueryBuilder<?> getQueryBuilder() {
		try {
			String codeName = get("codeName");
			OqlBuilder<?> builder = OqlBuilder.from(Class.forName(codeName), "baseCode");
			populateConditions(builder, "baseCode.codeName");
			builder.limit(getPageLimit());
			builder.orderBy(get("orderBy"));
			return builder;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Class getCodeClass(String codeName) {
		try {
			EntityQuery query = new EntityQuery(CodeMeta.class, "CodeMeta");
			query.add(new Condition("CodeMeta.engName=:name", codeName));
			List rs = (List) entityDao.search(query);
			if (1 != rs.size()) {
				throw new RuntimeException("ClassNotFoundException:" + codeName + " is not defined!");
			} else {
				return Class.forName(codeName);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("ClassNotFoundException:" + codeName + " is not defined!");
		}
	}

	/**
	 * 保存基础代码信息，
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String save() {
		String codeName = get("codeName");
		Class codeClass = null;
		BaseCode<?> baseCode = null;
		Long codeId = getLong("baseCode.id");
		try {
			codeClass = Class.forName(codeName);
			if (null == codeId) {
				baseCode = (BaseCode<?>) codeClass.newInstance();
			} else {
				baseCode = baseCodeService.getCode(codeClass, codeId);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		String code = get("baseCode.code");
		Model.populate(Params.sub("baseCode"), baseCode);
		if (!codeGenerator.isValidCode(code)) {
			code = codeGenerator.gen(new CodeFixture(baseCode));
			if (codeGenerator.isValidCode(code)) {
				baseCode.setCode(code);
			} else {
				return forward(new Action("edit"), "system.codeGen.failure");
			}
		}

		if (entityDao.duplicate(codeClass, codeId, "code", baseCode.getCode())) { return forward(new Action(
				"", "edit"), "error.code.existed"); }
		baseCodeService.saveOrUpdate(baseCode);
		return redirect("search", "info.save.success");
	}

	/**
	 * 查找信息action.
	 * 
	 @
	 */
	public String search() {
		Collection<?> datas = entityDao.search(this.getQueryBuilder());
		put("baseCodes", datas);
		put("shortName", getShortName());
		CodeMeta CodeMeta = (CodeMeta) entityDao.get(CodeMeta.class, "className", getEntityName()).iterator()
				.next();
		put("CodeMeta", CodeMeta);
		return forward();
	}

	/**
	 * 批量修改状态
	 */
	public String batchUpdateState() throws ClassNotFoundException {
		String codeName = get("codeName");
		Long[] ids = StrUtils.transformToLong(StrUtils.split(get("ids")));
		Boolean status = getBoolean("status");
		Class clazz = null;
		if (StringUtils.isEmpty(codeName)) {
			clazz = BaseCode.class;
		} else {
			clazz = Class.forName(codeName);
		}

		List codes = (List) entityDao.get(clazz, "id", ids);
		for (Iterator it = codes.iterator(); it.hasNext();) {
			BaseCode code = (BaseCode) it.next();
			// code.setEnabled(status.booleanValue());
		}
		entityDao.saveOrUpdate(codes);

		return redirect("search", "info.action.success");
	}

	public String getExtEditForward() {
		return forward(EXT + getShortName() + "Form");
	}

	@Override
	protected String getEntityName() {
		return get("codeName");
	}

	@Override
	protected String getShortName() {
		return "baseCode";
	}

	public void setCodeGenerator(CodeGenerator codeGenerator) {
		this.codeGenerator = codeGenerator;
	}

	@Override
	protected Exporter buildExporter(Context context) {
		String codeNameStr = get("codeNames");
		if (null != codeNameStr) {
			MultiEntityExporter exporter = new MultiEntityExporter();
			exporter.setWriter(new ExcelItemWriter());
			return exporter;
		} else {
			return super.buildExporter(context);
		}
	}

	protected void configExporter1(Exporter exporter, Context context, List list) {
		context.put("items", list);
	}

	protected void configExporter2(Exporter exporter, Context context) {
		String keys = get("keys");
		String titles = get("titles");
		if (keys == null) {
			keys = "code,name,engName,updatedAt,effectiveAt,invalidAt";
		}
		if (titles == null) {
			titles = "代码,名称,英文名,修改时间,生效时间,失效时间";
		}
		String codeNameStr = get("codeNames");
		if (null != codeNameStr) {
			String[] codeNames = StringUtils.split(codeNameStr, ',');
			List<List<?>> datas = CollectUtils.newArrayList();
			List<MultiEntityExporter.Metadata> metadatas = CollectUtils.newArrayList();
			context.put("metadatas", metadatas);
			String[] keyArray = StringUtils.split(keys, ',');
			String[] titleArray = StringUtils.split(titles, ',');
			for (String codeName : codeNames) {
				CodeMeta CodeMeta = entityDao.get(CodeMeta.class, "className", codeName).get(0);
				OqlBuilder b = OqlBuilder.hql("from " + codeName
						+ " as basecode order by basecode.updatedAt desc,basecode.code");
				datas.add(entityDao.search(b));
				metadatas.add(new MultiEntityExporter.Metadata(CodeMeta.getName(), keyArray, titleArray));
			}
			context.put("items", datas);
		} else {
			Collection list = getExportDatas();
			context.put("items", list);
			context.put("keys", keys);
			context.put("titles", titles);
		}
	}

	@Override
	protected void configExporter(Exporter exporter, Context context) {
		// markup 用来区分是标准维护里的导出 还是学校或者国家的标准里导出多个的
		String markup = get("markup");
		List<CodeMeta> list = new ArrayList<CodeMeta>();
		if (markup != null) {
			if (markup.equals("true")) {
				Long[] CodeMetaIds = StrUtils.transformToLong(StrUtils.split(get("CodeMetaId")));
				if (CodeMetaIds.length > 0) {
					for (int i = 0; i < CodeMetaIds.length; i++) {
						CodeMeta CodeMeta = entityDao.get(CodeMeta.class, CodeMetaIds[i]);
						list.add(CodeMeta);
					}
				} else {
					list = entityDao.getAll(CodeMeta.class);
				}
				configExporter1(exporter, context, list);
			}
		} else {
			configExporter2(exporter, context);
		}
	}

	public void getCodes() {
		String simpleName = get("className");
		StringBuilder builder = new StringBuilder();
		PrintWriter out = null;
		String format = get("format");
		if (StringUtils.isNotBlank(simpleName)) {
			Iterator<CodeMeta> it = entityDao.get(CodeMeta.class, "name", simpleName).iterator();
			if (it.hasNext()) {
				try {
					HttpServletResponse response =getResponse();
					response.setContentType("text/xml");
					response.setCharacterEncoding("UTF-8");
					out = response.getWriter();
					Class baseCodeClass = Class.forName(it.next().getClassName());
					if (BaseCode.class.isAssignableFrom(baseCodeClass)) {
						List<? extends BaseCode<?>> baseCodes = baseCodeService.getCodes(baseCodeClass);
						if (StringUtils.isNotEmpty(format)) {
							for (BaseCode<?> baseCode : baseCodes) {
								builder.append("<option value='" + baseCode.getId() + "'>" + baseCode.getName().trim()+"["+PropertyUtils.getProperty(baseCode, format)+"]"+"</option>");
							}
							
						}else{
							for (BaseCode<?> baseCode : baseCodes) {
								builder.append("<option value='" + baseCode.getId() + "'>" + baseCode.getName()
										+ "</option>");
							}
						}
					}
					out.write(builder.toString());
				} catch (ClassNotFoundException e) {
					out.write("<option value=''>没有该基础代码</option>");
				} catch (IOException e1) {
					out.write("<option value=''>加载失败</option>");
				} catch (Exception e2){
					out.write("<option value=''>"+format+"不符合规范</option>");
				}
			}
		}
	}

	public void setBaseCodeService(BaseCodeService baseCodeService) {
		this.baseCodeService = baseCodeService;
	}

}
