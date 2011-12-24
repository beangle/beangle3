/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.dictionary.service.impl;

import org.apache.commons.beanutils.PropertyUtils;
import org.beangle.ems.dictionary.service.CodeFixture;
import org.beangle.ems.dictionary.service.CodeGenerator;
import org.beangle.model.Entity;
import org.beangle.model.transfer.AbstractTransferListener;
import org.beangle.model.transfer.TransferResult;

/**
 * @author chaostone
 * @version $Id: ImporterCodeGenListener.java May 5, 2011 3:38:05 PM chaostone $
 */
public class ImporterCodeGenListener extends AbstractTransferListener {

	protected CodeGenerator codeGenerator;

	private String codeAttrName = "code";

	public ImporterCodeGenListener(CodeGenerator codeGenerator) {
		this.codeGenerator = codeGenerator;
	}

	public void onItemFinish(TransferResult tr) {
		try {
			String code = (String) PropertyUtils.getProperty((Entity<?>) transfer.getCurrent(), codeAttrName);
			if (!codeGenerator.isValidCode(code)) {
				code = codeGenerator.gen(new CodeFixture((Entity<?>) transfer.getCurrent()));
				PropertyUtils.setProperty(transfer.getCurrent(), codeAttrName, code);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getCodeAttrName() {
		return codeAttrName;
	}

	public void setCodeAttrName(String codeAttrName) {
		this.codeAttrName = codeAttrName;
	}

}
