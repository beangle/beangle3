/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.entity.metadata;

import org.beangle.commons.entity.pojo.LongIdObject;

public class ManagerEmployer extends LongIdObject {
  
  private static final long serialVersionUID = 1371741254315503984L;
  
  ContractInfo contractInfo;

  public ContractInfo getContractInfo() {
    return contractInfo;
  }

  public void setContractInfo(ContractInfo contractInfo) {
    this.contractInfo = contractInfo;
  }

}
