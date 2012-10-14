/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.testbean;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;

public class ProviderManager {

  private List<UserProvider> providers = CollectUtils.newArrayList();

  public List<UserProvider> getProviders() {
    return providers;
  }

  public void setProviders(List<UserProvider> providers) {
    this.providers = providers;
  }

}
