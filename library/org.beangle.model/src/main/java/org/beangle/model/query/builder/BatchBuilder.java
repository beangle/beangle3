package org.beangle.model.query.builder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.beangle.commons.collection.CollectUtils;

public class BatchBuilder {

	private List<Object> excuteList = CollectUtils.newArrayList();
	
	private BuilderEnum currType;

	public static BatchBuilder createBuild() {
		return new BatchBuilder();
	}

	public BatchBuilder save(Collection<Object> entities) {
		if (CollectionUtils.isEmpty(entities)) {
			return this;
		}
		if(null==currType || currType!=BuilderEnum.SAVE){
			this.excuteList.add(BuilderEnum.SAVE);
			this.currType = BuilderEnum.SAVE;
		}
		this.excuteList.addAll(entities);
		return this;
	}

	public BatchBuilder save(Object...entities) {
		if(ArrayUtils.isEmpty(entities)){
			return this;
		}
		if(null==currType || currType!=BuilderEnum.SAVE){
			this.excuteList.add(BuilderEnum.SAVE);
			this.currType = BuilderEnum.SAVE;
		}
		this.excuteList.addAll(Arrays.asList(entities));
		return this;
	}
	
	public BatchBuilder remove(Collection<Object> entities) {
		if (CollectionUtils.isEmpty(entities)) {
			return this;
		}
		if(null==currType || currType!=BuilderEnum.REMOVE){
			this.excuteList.add(BuilderEnum.REMOVE);
			this.currType = BuilderEnum.REMOVE;
		}
		this.excuteList.addAll(entities);
		return this;
	}
	
	public BatchBuilder remove(Object...entities) {
		if(ArrayUtils.isEmpty(entities)){
			return this;
		}
		if(null==currType || currType!=BuilderEnum.REMOVE){
			this.excuteList.add(BuilderEnum.REMOVE);
			this.currType = BuilderEnum.REMOVE;
		}
		this.excuteList.addAll(Arrays.asList(entities));
		return this;
	}

	public BatchBuilder commit() {
		if(null!=currType && currType!=BuilderEnum.COMMIT){
			this.excuteList.add(BuilderEnum.COMMIT);
			this.currType = BuilderEnum.COMMIT;
		}
		return this;
	}

	public List<Object> getExcuteList() {
		return excuteList;
	}
}
