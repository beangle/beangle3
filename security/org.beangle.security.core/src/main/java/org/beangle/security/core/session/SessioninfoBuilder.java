/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

import org.beangle.security.core.Authentication;

/**
 * 构建会话bean
 * 
 * @author chaostone
 * @version $Id: SessioninfoBuilder.java Jul 11, 2011 6:50:01 PM chaostone $
 */
public interface SessioninfoBuilder {

  Class<? extends Sessioninfo> getSessioninfoType();

  Sessioninfo build(Authentication auth, String sessionid);

  Object buildLog(Sessioninfo info);
}
