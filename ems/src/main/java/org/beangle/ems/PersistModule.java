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
package org.beangle.ems;

import org.beangle.commons.entity.orm.AbstractPersistModule;
import org.beangle.ems.config.model.PropertyConfigItemBean;
import org.beangle.ems.dictionary.model.CodeCategory;
import org.beangle.ems.dictionary.model.CodeMeta;
import org.beangle.ems.dictionary.model.CodeScript;
import org.beangle.ems.log.model.BusinessLogBean;
import org.beangle.ems.log.model.BusinessLogDetailBean;
import org.beangle.ems.meta.model.EntityMetaBean;
import org.beangle.ems.meta.model.PropertyMetaBean;
import org.beangle.ems.rule.model.RuleBean;
import org.beangle.ems.rule.model.RuleConfig;
import org.beangle.ems.rule.model.RuleConfigParam;
import org.beangle.ems.rule.model.RuleParameterBean;

public class PersistModule extends AbstractPersistModule {

  @SuppressWarnings("unchecked")
  @Override
  protected void doConfig() {
    defaultCache("beangle", "read-write");

    add(RuleBean.class, RuleParameterBean.class, RuleConfig.class, RuleConfigParam.class).cacheable();

    add(CodeCategory.class, CodeMeta.class).cacheable();

    add(CodeScript.class, EntityMetaBean.class, PropertyMetaBean.class, BusinessLogBean.class,
        BusinessLogDetailBean.class, PropertyConfigItemBean.class);

    cache().add(collection(RuleParameterBean.class, "children"), collection(RuleBean.class, "params"),
        collection(RuleConfig.class, "params"));
  }

}
