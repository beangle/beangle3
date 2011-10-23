/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.service;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.PropertyMeta;
import org.beangle.model.entity.Model;
import org.beangle.model.entity.types.EntityType;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.springframework.beans.BeanUtils;

public class IdentifierDataResolver implements UserDataResolver {

	protected EntityDao entityDao;

	public String marshal(PropertyMeta field, Collection<?> items) {
		StringBuilder sb = new StringBuilder();
		for (Object obj : items) {
			try {
				Object value = obj;
				if (null != field.getKeyName()) {
					value = PropertyUtils.getProperty(obj, field.getKeyName());
				}
				sb.append(String.valueOf(value)).append(',');
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> unmarshal(PropertyMeta field, String text) {
		if(null==field.getValueType()){
			return (List<T>)CollectUtils.newArrayList(StringUtils.split(text,","));
		}else{
			Class<?> clazz=null;
			try {
				clazz = Class.forName(field.getValueType());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			EntityType myType=Model.getEntityType(clazz);
			OqlBuilder<T> builder=OqlBuilder.from(myType.getEntityName(), "restrictField");
			
			String[] ids=StringUtils.split(text,",");
			PropertyDescriptor pd=BeanUtils.getPropertyDescriptor(clazz, field.getKeyName());
			Class<?> propertyType=pd.getReadMethod().getReturnType();
			List<Object> realIds=CollectUtils.newArrayList(ids.length);
			for(String id:ids){
				Object realId=ConvertUtils.convert(id, propertyType);
				realIds.add(realId);
			}
			builder.where("restrictField."+field.getKeyName()+" in (:ids)",realIds).cacheable();
			return entityDao.search(builder);
		}
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
