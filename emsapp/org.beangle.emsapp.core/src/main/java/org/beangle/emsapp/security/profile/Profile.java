/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.profile;

import java.util.List;

import org.beangle.model.pojo.LongIdEntity;

/**
 * 属性配置
 * 
 * @author chaostone
 * @version $Id: Profile.java Oct 21, 2011 8:43:35 AM chaostone $
 */
public interface Profile extends LongIdEntity{

	public List<? extends Property> getProperties();

	public Property getProperty(PropertyMeta meta);

}
