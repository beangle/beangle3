/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.exporter;

import java.net.URL;

import org.beangle.commons.transfer.io.Writer;

public interface TemplateWriter extends Writer {

  public URL getTemplate();

  public void setTemplate(URL template);

  public void write();

}
