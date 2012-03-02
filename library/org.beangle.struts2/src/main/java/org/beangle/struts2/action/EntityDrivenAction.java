/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.action;

import static org.beangle.web.util.RequestUtils.encodeAttachName;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Serializable;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.collection.CollectUtils;
import org.beangle.collection.Order;
import org.beangle.dao.Entity;
import org.beangle.dao.metadata.EntityType;
import org.beangle.dao.metadata.Model;
import org.beangle.dao.pojo.TimeEntity;
import org.beangle.dao.query.QueryBuilder;
import org.beangle.dao.query.builder.OqlBuilder;
import org.beangle.dao.util.EntityUtils;
import org.beangle.lang.StrUtils;
import org.beangle.struts2.helper.ExportHelper;
import org.beangle.struts2.helper.Params;
import org.beangle.transfer.TransferListener;
import org.beangle.transfer.TransferResult;
import org.beangle.transfer.csv.CsvItemReader;
import org.beangle.transfer.excel.ExcelItemReader;
import org.beangle.transfer.exporter.Context;
import org.beangle.transfer.exporter.DefaultPropertyExtractor;
import org.beangle.transfer.exporter.Exporter;
import org.beangle.transfer.exporter.PropertyExtractor;
import org.beangle.transfer.importer.DefaultEntityImporter;
import org.beangle.transfer.importer.EntityImporter;
import org.beangle.transfer.importer.listener.ImporterForeignerListener;
import org.beangle.transfer.io.TransferFormats;

import com.opensymphony.xwork2.util.ClassLoaderUtil;

public class EntityDrivenAction extends BaseAction {

	protected String entityName;

	/**
	 * 主页面
	 * 
	 * @return
	 */
	public String index() throws Exception {
		indexSetting();
		return forward();
	}

	/**
	 * 查找标准
	 * 
	 * @return
	 */
	public String search() {
		put(getShortName() + "s", search(getQueryBuilder()));
		return forward();
	}

	protected Collection<?> getExportDatas() {
		return search(getQueryBuilder().limit(null));
	}

	/**
	 * 修改标准
	 * 
	 * @return
	 */
	public String edit() {
		Long entityId = getEntityId(getShortName());
		Entity<?> entity = null;
		if (null == entityId) {
			entity = populateEntity();
		} else {
			entity = getModel(getEntityName(), entityId);
		}
		put(getShortName(), entity);
		editSetting(entity);
		return forward();
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String remove() throws Exception {
		Long entityId = getEntityId(getShortName());
		Collection<?> entities = null;
		if (null == entityId) {
			entities = getModels(getEntityName(), getEntityIds(getShortName()));
		} else {
			Entity<?> entity = getModel(getEntityName(), entityId);
			entities = Collections.singletonList(entity);
		}
		return removeAndForward(entities);
	}

	/**
	 * 保存修改后的标准
	 * 
	 * @return
	 */
	public String save() throws Exception {
		return saveAndForward(populateEntity());
	}

	protected Entity<?> populateEntity() {
		return populateEntity(getEntityName(), getShortName());
	}

	protected Long getEntityId(String shortName) {
		Long entityId = getLong(shortName + ".id");
		if (null == entityId) {
			entityId = getLong(shortName + "Id");
		}
		if (null == entityId) {
			entityId = getLong("id");
		}
		return entityId;
	}

	/**
	 * Get entity's id shortname.id[],shortname.ids,shortnameIds
	 * 
	 * @param <T>
	 * @param shortName
	 * @param clazz
	 * @return
	 */
	protected <T> T[] getEntityIds(String shortName, Class<T> clazz) {
		T[] datas = Params.getAll(shortName + ".id", clazz);
		if (null == datas) {
			String datastring = Params.get(shortName + ".ids");
			if (null == datastring) datastring = Params.get(shortName + "Ids");
			if (null != datastring) { return Params.converter.convert(StringUtils.split(datastring, ","),
					clazz); }
		}
		return datas;
	}

	protected Long[] getEntityIds(String shortName) {
		return getEntityIds(shortName, Long.class);
	}

	protected Long[] getEntityIds() {
		return getEntityIds(getShortName(), Long.class);
	}

	protected <T> void foo(Class<T> a) {
	}

	protected Entity<?> populateEntity(String entityName, String shortName) {
		Long entityId = getEntityId(shortName);
		Entity<?> entity = null;
		if (null == entityId) {
			entity = (Entity<?>) populate(entityName, shortName);
		} else {
			entity = getModel(entityName, entityId);
			populate(Params.sub(shortName), entity, entityName);
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	protected <T> T populateEntity(Class<T> entityClass, String shortName) {
		EntityType type = null;
		if (entityClass.isInterface()) {
			type = Model.getEntityType(entityClass.getName());
		} else {
			type = Model.getEntityType(entityClass);
		}
		return (T) populateEntity(type.getEntityName(), shortName);
	}

	protected Entity<?> getEntity() {
		return getEntity(getEntityName(), getShortName());
	}

	protected Entity<?> getEntity(String entityName, String name) {
		Long entityId = getEntityId(name);
		Entity<?> entity = null;
		try {
			EntityType type = Model.getEntityType(entityName);
			if (null == entityId) {
				entity = (Entity<?>) populate(type.newInstance(), type.getEntityName(), name);
			} else {
				entity = getModel(entityName, entityId);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	protected <T> T getEntity(Class<T> entityClass, String shortName) {
		EntityType type = null;
		if (entityClass.isInterface()) {
			type = Model.getEntityType(entityClass.getName());
		} else {
			type = Model.getEntityType(entityClass);
		}
		return (T) getEntity(type.getEntityName(), shortName);
	}

	/**
	 * 查看信息
	 * 
	 * @return
	 */
	public String info() throws Exception {
		Long entityId = getEntityId(getShortName());
		if (null == entityId) {
			logger.warn("cannot get paremeter {}Id or {}.id", getShortName(), getShortName());
		}
		Entity<?> entity = getModel(getEntityName(), entityId);
		put(getShortName(), entity);
		return forward();
	}

	protected void indexSetting() {

	}

	protected void editSetting(Entity<?> entity) {

	}

	/**
	 * 保存对象
	 * 
	 * @param entity
	 * @return
	 */
	protected String saveAndForward(Entity<?> entity) {
		try {
			if (entity instanceof TimeEntity<?>) {
				TimeEntity<?> timeEntity = (TimeEntity<?>) entity;
				if (!timeEntity.isPersisted()) {
					timeEntity.setCreatedAt(new Date());
				}
				timeEntity.setUpdatedAt(new Date());
			}
			saveOrUpdate(Collections.singletonList(entity));
			return redirect("search", "info.save.success");
		} catch (Exception e) {
			logger.info("saveAndForwad failure", e);
			return redirect("search", "info.save.failure");
		}

	}

	protected String removeAndForward(Collection<?> entities) {
		try {
			remove(entities);
		} catch (Exception e) {
			logger.info("removeAndForwad failure", e);
			return redirect("search", "info.delete.failure");
		}
		return redirect("search", "info.remove.success");
	}

	protected QueryBuilder<?> getQueryBuilder() {
		OqlBuilder<?> builder = OqlBuilder.from(getEntityName(), getShortName());
		populateConditions(builder);
		builder.orderBy(get(Order.ORDER_STR)).limit(getPageLimit());
		return builder;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	protected String getEntityName() {
		if (null == entityName) { throw new RuntimeException("entityName not set for :"
				+ getClass().getName()); }
		return entityName;
	}

	protected String getShortName() {
		String name = getEntityName();
		if (StringUtils.isNotEmpty(name)) return EntityUtils.getCommandName(name);
		else return null;
	}

	protected Entity<?> getModel(String entityName, Serializable id) {
		return (Entity<?>) entityDao.get(entityName, id);
	}

	@SuppressWarnings("rawtypes")
	protected List getModels(String entityName, Long[] ids) {
		return entityDao.get(entityName, "id", (Object[]) ids);
	}

	protected <T> List<T> getModels(Class<T> modelClass, Long[] ids) {
		return entityDao.get(modelClass, "id", (Object[]) ids);
	}

	/**
	 * 导出数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public String export() throws Exception {
		String format = get("format");
		String fileName = get("fileName");
		String template = get("template");
		if (StringUtils.isBlank(format)) {
			format = TransferFormats.XLS;
		}
		if (StringUtils.isEmpty(fileName)) {
			fileName = "exportResult";
		}

		// 配置导出上下文
		Context context = new Context();
		context.put("format", format);
		context.put("exportFile", fileName);
		context.put("template", template);
		String properties = get("properties");
		if (null != properties) {
			String[] props = StringUtils.split(properties, ",");
			List<String> keys = CollectUtils.newArrayList();
			List<String> titles = CollectUtils.newArrayList();
			for (String prop : props) {
				keys.add(StringUtils.substringBefore(prop, ":"));
				titles.add(getTextInternal(StringUtils.substringAfter(prop, ":")));
			}
			context.put(Context.KEYS, StrUtils.join(keys, ","));
			context.put(Context.TITLES, StrUtils.join(titles, ","));
		} else {
			context.put(Context.KEYS, get("keys"));
			context.put(Context.TITLES, get("titles"));
		}
		context.put(Context.EXTRACTOR, getPropertyExtractor());

		HttpServletResponse response = ServletActionContext.getResponse();
		Exporter exporter = buildExporter(context);
		exporter.getWriter().setOutputStream(response.getOutputStream());
		configExporter(exporter, context);
		if (format.equals(TransferFormats.XLS)) {
			response.setContentType("application/vnd.ms-excel;charset=GBK");
		} else {
			response.setContentType("application/x-msdownload");
		}
		response.setHeader(
				"Content-Disposition",
				"attachment;filename="
						+ encodeAttachName(ServletActionContext.getRequest(), fileName + "." + format));
		// 进行输出
		exporter.setContext(context);
		exporter.transfer(new TransferResult());
		return null;
	}

	protected PropertyExtractor getPropertyExtractor() {
		return new DefaultPropertyExtractor(getTextResource());
	}

	protected URL getResource(String name) {
		URL url = ClassLoaderUtil.getResource(name, getClass());
		if (url == null) {
			logger.error("Cannot load template {}", name);
		}
		return url;
	}

	protected Exporter buildExporter(Context context) {
		return ExportHelper.buildExporter(context);
	}

	protected void configExporter(Exporter exporter, Context context) {
		configExportContext(context);
	}

	/**
	 * @deprecated use configExporter
	 * @param context
	 */
	protected void configExportContext(Context context) {
		context.put("items", getExportDatas());
	}

	public String importForm() {
		return forward("/components/importData/form");
	}

	/**
	 * 构建实体导入者
	 * 
	 * @return
	 */
	protected EntityImporter buildEntityImporter() {
		if (null == getEntityName()) {
			return buildEntityImporter("importFile", null);
		} else {
			return buildEntityImporter("importFile", Model.getEntityType(getEntityName()).getEntityClass());
		}
	}

	/**
	 * 用于构建单个实体类的导入构造器
	 * 
	 * @param clazz
	 * @return
	 */
	protected EntityImporter buildEntityImporter(Class<?> clazz) {
		return buildEntityImporter("importFile", clazz);
	}

	/**
	 * 构建实体导入者
	 * 
	 * @param upload
	 * @param clazz
	 * @return
	 */
	protected EntityImporter buildEntityImporter(String upload, Class<?> clazz) {
		try {
			File file = get(upload, File.class);
			if (null == file) {
				logger.error("cannot get upload file {}.", upload);
				return null;
			}
			String fileName = get(upload + "FileName");
			InputStream is = new FileInputStream(file);
			if (fileName.endsWith(".xls")) {
				EntityImporter importer = (clazz == null) ? new DefaultEntityImporter()
						: new DefaultEntityImporter(clazz);
				importer.setReader(new ExcelItemReader(is, 1));
				put("importer", importer);
				return importer;
			} else {
				LineNumberReader reader = new LineNumberReader(new InputStreamReader(is));
				if (null == reader.readLine()) return null;
				reader.reset();
				EntityImporter importer = (clazz == null) ? new DefaultEntityImporter()
						: new DefaultEntityImporter(clazz);
				importer.setReader(new CsvItemReader(reader));
				return importer;
			}
		} catch (Exception e) {
			logger.error("error", e);
			return null;
		}
	}

	/**
	 * 导入信息
	 * 
	 * @return
	 */
	public String importData() {
		TransferResult tr = new TransferResult();
		EntityImporter importer = buildEntityImporter();
		if (null == importer) { return forward("/components/importData/error"); }
		configImporter(importer);
		importer.transfer(tr);
		put("importResult", tr);
		if (tr.hasErrors()) {
			return forward("/components/importData/error");
		} else {
			return forward("/components/importData/result");
		}
	}

	protected void configImporter(EntityImporter importer) {
		for (final TransferListener il : getImporterListeners()) {
			importer.addListener(il);
		}
	}

	protected List<? extends TransferListener> getImporterListeners() {
		return Collections.singletonList(new ImporterForeignerListener(entityDao));
	}
}
