/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.testbean;

import org.beangle.commons.context.spring.SpringResources;

public class SpringResourcesConsumer {

  SpringResources resources;

  public SpringResources getResources() {
    return resources;
  }

  public void setResources(SpringResources resource) {
    this.resources = resource;
  }

}
