/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.dictionary.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdTimeObject;

/**
 * 系统编码规则
 * 
 * @author chaostone
 * @version $Id: CodeScript.java May 5, 2011 3:56:23 PM chaostone $
 */
@Entity
public class CodeScript extends LongIdTimeObject {

	private static final long serialVersionUID = -2771592539431465165L;

	/** 编码对象 */
	@NotNull
	@Size(max = 40)
	private String codeName;

	/** 编码属性 */
	@NotNull
	@Size(max = 20)
	private String attr;

	/** 编码对象的类名 */
	@NotNull
	@Size(max = 100)
	private String codeClassName;

	/** 编码脚本 */
	@NotNull
	@Size(max = 500)
	private String script;

	/** 编码简要描述 */
	@NotNull
	@Size(max = 100)
	private String description;

	public CodeScript() {
		super();
	}

	public CodeScript(String codeClassName, String attr, String script) {
		this.codeClassName = codeClassName;
		this.attr = attr;
		this.script = script;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getCodeClassName() {
		return codeClassName;
	}

	public void setCodeClassName(String codeClassName) {
		this.codeClassName = codeClassName;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

}
