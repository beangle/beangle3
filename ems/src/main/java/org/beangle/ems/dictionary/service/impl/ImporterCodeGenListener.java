/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.ems.dictionary.service.impl;

import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.transfer.AbstractTransferListener;
import org.beangle.commons.transfer.TransferResult;
import org.beangle.ems.dictionary.service.CodeFixture;
import org.beangle.ems.dictionary.service.CodeGenerator;

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
